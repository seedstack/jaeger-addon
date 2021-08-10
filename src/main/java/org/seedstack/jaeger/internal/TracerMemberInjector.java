/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.jaeger.internal;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.inject.MembersInjector;
import io.jaegertracing.Configuration;
import io.jaegertracing.internal.reporters.CompositeReporter;
import io.jaegertracing.internal.reporters.InMemoryReporter;
import io.jaegertracing.internal.reporters.LoggingReporter;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;
import org.seedstack.jaeger.JaegerConfig;
import org.seedstack.jaeger.Tracing;
import org.seedstack.seed.SeedException;
import org.seedstack.shed.reflect.Classes;
import org.seedstack.shed.reflect.ReflectUtils;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class TracerMemberInjector<T> implements MembersInjector<T> {
    private static final ConcurrentMap<String, Tracer> tracers = new ConcurrentHashMap<>();
    private final JaegerConfig jaegerConfig;
    private final Field field;
    private final Tracing annotation;

    TracerMemberInjector(Field field, JaegerConfig jaegerConfig) {
        this.field = field;
        this.jaegerConfig = jaegerConfig;
        this.annotation = Preconditions.checkNotNull(field.getAnnotation(Tracing.class), "Missing @Tracing annotation on field " + field);
        if (Strings.isNullOrEmpty(annotation.value())) {
            throw SeedException.createNew(JaegerErrorCode.EMPTY_SERVICE_NAME)
                    .put("field", field);
        }
    }

    @Override
    public void injectMembers(T instance) {
        Tracer tracer = tracers.computeIfAbsent(annotation.value(), this::getTracer);
        ReflectUtils.setValue(ReflectUtils.makeAccessible(field), instance, tracer);
    }

    private Tracer getTracer(String serviceName) {
        Configuration config = new Configuration(serviceName);

        // Common
        if (jaegerConfig.getTracerConfig() != null) {
            JaegerConfig.TracerConfig tracerConfig = jaegerConfig.getTracerConfig();
            config.withTraceId128Bit(tracerConfig.isTraceId128Bit());
            Optional.ofNullable(tracerConfig.getTracerTags()).ifPresent(config::withTracerTags);
            Optional.ofNullable(tracerConfig.getMetricsFactory()).map(Classes::instantiateDefault).ifPresent(config::withMetricsFactory);
        }


        if (jaegerConfig.isDevMode()) {
            return config.getTracerBuilder()
                    .withReporter(new CompositeReporter(new InMemoryReporter(), new LoggingReporter()))
                    .withSampler(new ConstSampler(true))
                    .build();
        } else {
            if (jaegerConfig.getSamplerConfig() != null) {
                Configuration.SamplerConfiguration samplerConfig = new Configuration.SamplerConfiguration().withType(jaegerConfig.getSamplerConfig().getSamplerType())
                        .withParam(jaegerConfig.getSamplerConfig().getSamplerParam())
                        .withManagerHostPort(jaegerConfig.getSamplerConfig().getSamplerManagerHostPort());
                config.withSampler(samplerConfig);
            }

            if (jaegerConfig.getReporterConfig() != null) {
                Configuration.ReporterConfiguration reporterConfig = new Configuration.ReporterConfiguration().withLogSpans(jaegerConfig.getReporterConfig().isReporterLogSpans())
                        .withFlushInterval(jaegerConfig.getReporterConfig().getReporterFlushInterval())
                        .withMaxQueueSize(jaegerConfig.getReporterConfig().getReporterMaxQueueSize());

                if (jaegerConfig.getSenderConfig() != null) {
                    Configuration.SenderConfiguration senderConfig = new Configuration.SenderConfiguration().withAgentHost(jaegerConfig.getSenderConfig().getAgentHost())
                            .withAgentPort(jaegerConfig.getSenderConfig().getAgentPort()).withEndpoint(jaegerConfig.getSenderConfig().getEndPoint())
                            .withAuthUsername(jaegerConfig.getSenderConfig().getUserName()).withAuthPassword(jaegerConfig.getSenderConfig().getPassword())
                            .withAuthToken(jaegerConfig.getSenderConfig().getAuthToken());
                    reporterConfig.withSender(senderConfig);
                }

                config.withReporter(reporterConfig);
            }

            if (jaegerConfig.getCodecConfig() != null) {
                Configuration.CodecConfiguration codecConfig = Configuration.CodecConfiguration.fromString(jaegerConfig.getCodecConfig().getPropagation());
                config.withCodec(codecConfig);
            }


            return config.getTracer();
        }
    }
}
