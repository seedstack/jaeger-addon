package org.seedstack.jaeger.internal;

import org.seedstack.jaeger.JaegerConfig;
import org.seedstack.jaeger.JaegerConfig.TracerConfig;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.CodecConfiguration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.Configuration.SenderConfiguration;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.reporters.InMemoryReporter;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.jaegertracing.spi.Reporter;
import io.jaegertracing.spi.Sampler;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.Context;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.opentracing.Tracer;

public class JaegerPlugin extends AbstractSeedPlugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(JaegerPlugin.class);
    private JaegerConfig jaegerConfig;

    @Override
    public String name() {
        return "jaeger";
    }

    @Override
    public String pluginPackageRoot() {
        return "org.seedstack.addons.jaeger";
    }

    @Override
    public InitState initialize(InitContext initContext) {
        jaegerConfig = getConfiguration(JaegerConfig.class);

        return InitState.INITIALIZED;
    }

    public Tracer getTracer(String serviceName) {

        io.opentracing.Tracer tracer;

        if (!jaegerConfig.isDevMode()) {

            SamplerConfiguration samplerConfig = new SamplerConfiguration().withType(jaegerConfig.getSamplerConfig().getSamplerType())
                    .withParam(jaegerConfig.getSamplerConfig().getSamplerParam());

            SenderConfiguration senderConfig = new SenderConfiguration().withAgentHost(jaegerConfig.getSenderConfig().getAgentHost())
                    .withAgentPort(jaegerConfig.getSenderConfig().getAgentPort()).withEndpoint(jaegerConfig.getSenderConfig().getEndPoint());

            ReporterConfiguration reporterConfig = new ReporterConfiguration().withLogSpans(jaegerConfig.getReporterConfig().getReporterLogSpans())
                    .withSender(senderConfig);

            CodecConfiguration codecConfig = new CodecConfiguration().fromString(jaegerConfig.getCodecConfig().getPropagation());

            TracerConfig tracerConfig = jaegerConfig.getTracerConfig();

            Configuration config = new Configuration(serviceName).withSampler(samplerConfig).withReporter(reporterConfig).withCodec(codecConfig)
                    .withTracerTags(tracerConfig.getTracerTags());
            tracer = config.getTracer();

        }

        else {

            Reporter reporter = new InMemoryReporter();
            Sampler sampler = new ConstSampler(true);
            tracer = new JaegerTracer.Builder(serviceName).withReporter(reporter).withSampler(sampler).build();
        }

        return tracer;
    }

    @Override
    public Object nativeUnitModule() {
        return new JaegerModule();
    }

    @Override
    public void start(Context context) {
        // TODO: place add-on startup code here if any
    }

    @Override
    public void stop() {
        // TODO: place add-on shutdown code here if any
    }

}
