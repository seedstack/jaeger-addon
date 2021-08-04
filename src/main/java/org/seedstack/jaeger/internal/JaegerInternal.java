/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.jaeger.internal;

import java.util.Map;

import org.seedstack.jaeger.JaegerConfig;
import org.seedstack.jaeger.JaegerConfig.TracerConfig;
import org.seedstack.seed.SeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.CodecConfiguration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.Configuration.SenderConfiguration;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.JaegerTracer.Builder;
import io.jaegertracing.internal.reporters.CompositeReporter;
import io.jaegertracing.internal.reporters.InMemoryReporter;
import io.jaegertracing.internal.reporters.LoggingReporter;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.jaegertracing.spi.MetricsFactory;
import io.jaegertracing.spi.Reporter;
import io.jaegertracing.spi.Sampler;
import io.opentracing.Tracer;

/**
 * JaegerInternal class for Jaeger Tracer initialisation
 */

public class JaegerInternal {
    private static final Logger LOGGER = LoggerFactory.getLogger(JaegerInternal.class);

    private JaegerInternal() {
    }

    /**
     * Gets the tracer.
     *
     * @param serviceName the service name
     * @param jaegerConfig the jaeger config
     * @return the tracer
     */
    public static Tracer getTracer(String serviceName, JaegerConfig jaegerConfig) {

        Configuration config = new Configuration(serviceName);
        MetricsFactory metricsFactory = null;
        boolean traceId128Bit = false;
        Map<String, String> tracerTags = null;
        Tracer tracer;

        if (jaegerConfig.getSamplerConfig() != null) {

            SamplerConfiguration samplerConfig = new SamplerConfiguration().withType(jaegerConfig.getSamplerConfig().getSamplerType())
                    .withParam(jaegerConfig.getSamplerConfig().getSamplerParam())
                    .withManagerHostPort(jaegerConfig.getSamplerConfig().getSamplerManagerHostPort());
            config.withSampler(samplerConfig);
        }

        if (jaegerConfig.getReporterConfig() != null) {

            ReporterConfiguration reporterConfig = new ReporterConfiguration().withLogSpans(jaegerConfig.getReporterConfig().isReporterLogSpans())
                    .withFlushInterval(jaegerConfig.getReporterConfig().getReporterFlushInterval())
                    .withMaxQueueSize(jaegerConfig.getReporterConfig().getReporterMaxQueueSize());

            if (jaegerConfig.getSenderConfig() != null) {

                SenderConfiguration senderConfig = new SenderConfiguration().withAgentHost(jaegerConfig.getSenderConfig().getAgentHost())
                        .withAgentPort(jaegerConfig.getSenderConfig().getAgentPort()).withEndpoint(jaegerConfig.getSenderConfig().getEndPoint())
                        .withAuthUsername(jaegerConfig.getSenderConfig().getUserName()).withAuthPassword(jaegerConfig.getSenderConfig().getPassword())
                        .withAuthToken(jaegerConfig.getSenderConfig().getAuthToken());
                reporterConfig.withSender(senderConfig);
            }

            config.withReporter(reporterConfig);
        }

        if (jaegerConfig.getCodecConfig() != null) {
            CodecConfiguration codecConfig = CodecConfiguration.fromString(jaegerConfig.getCodecConfig().getPropagation());
            config.withCodec(codecConfig);
        }

        if (jaegerConfig.getTracerConfig() != null) {
            TracerConfig tracerConfig = jaegerConfig.getTracerConfig();
            traceId128Bit = tracerConfig.isTraceId128Bit();
            tracerTags = tracerConfig.getTracerTags();
            config.withTraceId128Bit(traceId128Bit);
            config.withTracerTags(tracerTags);

            try {

                if (tracerConfig.getMetricsFactory() != null) {

                    metricsFactory = tracerConfig.getMetricsFactory().newInstance();

                    config.withMetricsFactory(metricsFactory);
                }
            } catch (InstantiationException | IllegalAccessException e) {

                throw SeedException.wrap(e, JaegerErrorCode.ERROR_INSTANTIATING_METRICS_FACTORY).put("class", tracerConfig.getMetricsFactory());
            }
        }

        if (!jaegerConfig.isDevMode()) {

            tracer = config.getTracer();
            LOGGER.info("JaegerTracer Initialized as per yaml Configuration");

        }

        else {

            Reporter inMemoryReporter = new InMemoryReporter();
            Reporter loggingReporter = new LoggingReporter();
            Reporter compositeReporter = new CompositeReporter(inMemoryReporter, loggingReporter);
            Sampler sampler = new ConstSampler(true);

            Builder builder = new JaegerTracer.Builder(serviceName).withReporter(compositeReporter).withSampler(sampler);
            builder.withTags(tracerTags);
            if (metricsFactory != null) {
                builder.withMetricsFactory(metricsFactory);
            }

            tracer = builder.build();
            LOGGER.info("InMemoryTracer Initialized");

        }

        return tracer;
    }

}