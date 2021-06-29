package org.seedstack.jaeger;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.testing.junit4.SeedITRunner;

import io.opentracing.Tracer;

@RunWith(SeedITRunner.class)
public class JaegerIT {

    @Configuration("jaeger")
    private JaegerConfig jaegerConfig;

    @ServiceName("SomeService")
    Tracer tracer;

    @Test
    public void tracer_injection_is_working() {

        Assertions.assertThat(tracer).isNotNull();
    }

    @Test
    public void Jaeger_tracer_in_falsedevMode() {

    }

    @Test
    public void metric_factory_NullCheck() {

    }

}
