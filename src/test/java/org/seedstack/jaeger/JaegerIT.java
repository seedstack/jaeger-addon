/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.jaeger;

import io.jaegertracing.internal.JaegerSpan;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.metrics.InMemoryMetricsFactory;
import io.jaegertracing.internal.metrics.Metrics;
import io.jaegertracing.internal.reporters.InMemoryReporter;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.testing.junit4.SeedITRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SeedITRunner.class)
public class JaegerIT {
    InMemoryMetricsFactory metricsFactory;
    InMemoryReporter spanReporter;
    JaegerTracer jaegerTracer;

    @Tracing("SomeService")
    Tracer tracer;
    @Tracing("SomeService")
    Tracer tracer1;

    @Before
    public void setUp() {
        metricsFactory = new InMemoryMetricsFactory();
        spanReporter = new InMemoryReporter();
        jaegerTracer = new JaegerTracer.Builder("TracerTestService").withReporter(spanReporter).withSampler(new ConstSampler(true))
                .withMetrics(new Metrics(metricsFactory)).withTag("hostname", "localhost").withTag("ip", "127.0.0.1").build();
    }

    @Test
    public void test_tracer_is_injectable() {
        Assertions.assertThat(tracer).isNotNull();
        Assertions.assertThat(tracer).isInstanceOf(JaegerTracer.class);
    }

    @Test
    public void test_another_tracer_assignment() {
        Assertions.assertThat(tracer1).isNotNull();
        Assertions.assertThat(tracer1).isInstanceOf(JaegerTracer.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testServiceNameNotEmpty() {
        new JaegerTracer.Builder("  ");
    }

    @Test
    public void testDefaultTracer() {
        io.jaegertracing.Configuration configuration = new io.jaegertracing.Configuration("name");
        assertNotNull(configuration.getTracer());
        assertNotNull(configuration.getTracer());
        configuration.closeTracer();
    }

    @Test
    public void testMetrics() {
        InMemoryMetricsFactory inMemoryMetricsFactory = new InMemoryMetricsFactory();
        io.jaegertracing.Configuration configuration = new io.jaegertracing.Configuration("foo").withMetricsFactory(inMemoryMetricsFactory);
        assertEquals(inMemoryMetricsFactory, configuration.getMetricsFactory());
    }

    @Test
    public void testActiveSpanPropagation() {
        Span span = jaegerTracer.buildSpan("parent").start();
        try (Scope parent = jaegerTracer.activateSpan(span)) {
            assertEquals(span, jaegerTracer.scopeManager().activeSpan());
        }
    }

    @Test
    public void testActiveSpan() {
        Span span = jaegerTracer.buildSpan("parent").start();
        try (Scope ignored = jaegerTracer.activateSpan(span)) {
            jaegerTracer.buildSpan("child").start().finish();
        } finally {
            span.finish();
        }
        assertEquals(2, spanReporter.getSpans().size());

        JaegerSpan childSpan = spanReporter.getSpans().get(0);
        assertEquals("child", childSpan.getOperationName());

        JaegerSpan parentSpan = spanReporter.getSpans().get(1);
        assertEquals("parent", parentSpan.getOperationName());

    }

    @Test
    public void testTracerTags() throws Exception {
        assertEquals("localhost", jaegerTracer.tags().get("hostname"));
        assertEquals("127.0.0.1", jaegerTracer.tags().get("ip"));

    }

    @Test
    public void testTraceId128Bit() {
        assertFalse(jaegerTracer.isUseTraceId128Bit());
    }

    @Test
    public void testBuildSpan() {
        String expectedOperation = "someOperation";
        JaegerSpan jaegerSpan = jaegerTracer.buildSpan(expectedOperation).withTag("spantag", "testing").start();
        assertEquals(expectedOperation, jaegerSpan.getOperationName());
        assertEquals("testing", jaegerSpan.getTags().get("spantag"));
    }
}
