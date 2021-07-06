package org.seedstack.jaeger;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.testing.junit4.SeedITRunner;

import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Tracer;

@RunWith(SeedITRunner.class)
public class JaegerIT {

    @Configuration("jaeger")
    private JaegerConfig jaegerConfig;

    @ServiceName("SomeService")
    Tracer tracer;

    @Test
    public void tracer_is_injectable() {

        Assertions.assertThat(tracer).isNotNull();
        Assertions.assertThat(tracer).isInstanceOf(JaegerTracer.class);
    }

    @Test
    public void when_no_configuration_inYaml() {

        if (jaegerConfig.getSamplerConfig() == null && jaegerConfig.getReporterConfig() == null && jaegerConfig.getSenderConfig() == null
                && jaegerConfig.getCodecConfig() == null && jaegerConfig.getTracerConfig() == null) {
            Assertions.assertThat(tracer).isNotNull();
            Assertions.assertThat(tracer).isInstanceOf(JaegerTracer.class);
        }

    }

    @Test
    public void when_dev_mode_false() {

        if (!jaegerConfig.isDevMode()) {
            Assertions.assertThat(tracer).isNotNull();
            Assertions.assertThat(tracer).isInstanceOf(JaegerTracer.class);
        }

    }

    @Test
    public void when_dev_mode_true() {

        if (jaegerConfig.isDevMode()) {
            Assertions.assertThat(tracer).isNotNull();
            Assertions.assertThat(tracer).isInstanceOf(JaegerTracer.class);
        }
    }

}
