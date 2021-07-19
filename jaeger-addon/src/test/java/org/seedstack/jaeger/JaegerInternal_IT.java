/*
 * Creation : 19 Jul 2021
 */
package org.seedstack.jaeger;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.testing.junit4.SeedITRunner;

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
import io.jaegertracing.spi.Reporter;
import io.jaegertracing.spi.Sampler;
import io.opentracing.Tracer;

@RunWith(SeedITRunner.class)
public class JaegerInternal_IT {

    @Configuration
    private JaegerConfig jaegerConfig;
    Map<String, String> tags = new HashMap<>();
    String serviceName = "SomeService";

    @Test
    public void test_JaegerConfig() {

        Assertions.assertThat(jaegerConfig).isNotNull();

    }

    @Test
    public void test_getTracer_WithDefalutValues() {

        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration(serviceName);
        Tracer tracer = config.getTracer();
        Assertions.assertThat(tracer).isInstanceOf(JaegerTracer.class);

    }

    @Test
    public void test_getTracer_With_ConfigurationValues() {

        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration(serviceName);

        SamplerConfiguration samplerConfig = new SamplerConfiguration().withType("const").withParam(1).withManagerHostPort("127.0.0.1");
        config.withSampler(samplerConfig);

        ReporterConfiguration reporterConfig = new ReporterConfiguration().withLogSpans(true).withFlushInterval(10000).withMaxQueueSize(1000);
        SenderConfiguration senderConfig = new SenderConfiguration().withAgentHost("127.0.0.1").withAgentPort(6831)
                .withEndpoint("http://localhost:14268/api/traces").withAuthUsername("someUser").withAuthPassword("somePasssword")
                .withAuthToken("myAuthToken");
        reporterConfig.withSender(senderConfig);

        config.withReporter(reporterConfig);

        CodecConfiguration codecConfig = CodecConfiguration.fromString("jaeger,b3");
        config.withCodec(codecConfig);
        config.withTraceId128Bit(false);

        tags.put("tag1", "value1");
        tags.put("tag2", "value2");

        config.withTracerTags(tags);

        Tracer tracer = config.getTracer();
        Assertions.assertThat(tracer).isInstanceOf(JaegerTracer.class);

    }

    @Test
    public void test_getInmemoryTracer() {

        Reporter inMemoryReporter = new InMemoryReporter();
        Reporter loggingReporter = new LoggingReporter();
        Reporter compositeReporter = new CompositeReporter(inMemoryReporter, loggingReporter);
        Sampler sampler = new ConstSampler(true);

        Builder builder = new JaegerTracer.Builder(serviceName).withReporter(compositeReporter).withSampler(sampler);
        builder.withTags(tags);
        Tracer tracer = builder.build();
        Assertions.assertThat(tracer).isInstanceOf(JaegerTracer.class);

    }
}
