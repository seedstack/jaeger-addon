/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.jaeger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.seedstack.coffig.Config;

import io.jaegertracing.spi.MetricsFactory;

/**
 * The Class JaegerConfig to populate Jaeger Configuration from yaml.
 */
@Config("jaeger")
public class JaegerConfig {

    /** The dev mode. */
    private boolean devMode;

    /** The sampler config. */
    private SamplerConfig samplerConfig;

    /** The sender config. */
    private SenderConfig senderConfig;

    /** The reporter config. */
    private ReporterConfig reporterConfig;

    /** The codec config. */
    private CodecConfig codecConfig;

    /** The tracer config. */
    private TracerConfig tracerConfig;

    /**
     * Checks if is dev mode.
     *
     * @return true, if is dev mode
     */
    public boolean isDevMode() {
        return devMode;
    }

    /**
     * Sets the dev mode.
     *
     * @param devMode the dev mode
     * @return the jaeger config
     */
    public JaegerConfig setDevMode(boolean devMode) {
        this.devMode = devMode;
        return this;
    }

    /**
     * Gets the sampler config.
     *
     * @return the sampler config
     */
    public SamplerConfig getSamplerConfig() {
        return samplerConfig;
    }

    /**
     * Sets the sampler config.
     *
     * @param samplerConfig the sampler config
     * @return the jaeger config
     */
    public JaegerConfig setSamplerConfig(SamplerConfig samplerConfig) {
        this.samplerConfig = samplerConfig;
        return this;
    }

    /**
     * Gets the sender config.
     *
     * @return the sender config
     */
    public SenderConfig getSenderConfig() {
        return senderConfig;
    }

    /**
     * Sets the sender config.
     *
     * @param senderConfig the sender config
     * @return the jaeger config
     */
    public JaegerConfig setSenderConfig(SenderConfig senderConfig) {
        this.senderConfig = senderConfig;
        return this;
    }

    /**
     * Gets the reporter config.
     *
     * @return the reporter config
     */
    public ReporterConfig getReporterConfig() {
        return reporterConfig;
    }

    /**
     * Sets the reporter config.
     *
     * @param reporterConfig the reporter config
     * @return the jaeger config
     */
    public JaegerConfig setReporterConfig(ReporterConfig reporterConfig) {
        this.reporterConfig = reporterConfig;
        return this;
    }

    /**
     * Gets the codec config.
     *
     * @return the codec config
     */
    public CodecConfig getCodecConfig() {
        return codecConfig;
    }

    /**
     * Sets the codec config.
     *
     * @param codecConfig the codec config
     * @return the jaeger config
     */
    public JaegerConfig setCodecConfig(CodecConfig codecConfig) {
        this.codecConfig = codecConfig;
        return this;
    }

    /**
     * Gets the tracer config.
     *
     * @return the tracer config
     */
    public TracerConfig getTracerConfig() {
        return tracerConfig;
    }

    /**
     * Sets the tracer config.
     *
     * @param tracerConfig the tracer config
     * @return the jaeger config
     */
    public JaegerConfig setTracerConfig(TracerConfig tracerConfig) {
        this.tracerConfig = tracerConfig;
        return this;
    }

    /**
     * The Class SamplerConfig.
     */
    public static class SamplerConfig {

        /** The sampler type. */
        private String samplerType;

        /** The sampler param. */
        private Integer samplerParam;

        /** The sampler manager host port. */
        private String samplerManagerHostPort;

        /**
         * Gets the sampler type.
         *
         * @return the sampler type
         */
        public String getSamplerType() {
            return samplerType;
        }

        /**
         * Sets the sampler type.
         *
         * @param samplerType the sampler type
         * @return the sampler config
         */
        public SamplerConfig setSamplerType(String samplerType) {
            this.samplerType = samplerType;
            return this;
        }

        /**
         * Gets the sampler param.
         *
         * @return the sampler param
         */
        public Integer getSamplerParam() {
            return samplerParam;
        }

        /**
         * Sets the sampler param.
         *
         * @param samplerParam the sampler param
         * @return the sampler config
         */
        public SamplerConfig setSamplerParam(Integer samplerParam) {
            this.samplerParam = samplerParam;
            return this;
        }

        /**
         * Gets the sampler manager host port.
         *
         * @return the sampler manager host port
         */
        public String getSamplerManagerHostPort() {
            return samplerManagerHostPort;
        }

        /**
         * Sets the sampler manager host port.
         *
         * @param samplerManagerHostPort the sampler manager host port
         * @return the sampler config
         */
        public SamplerConfig setSamplerManagerHostPort(String samplerManagerHostPort) {
            this.samplerManagerHostPort = samplerManagerHostPort;
            return this;
        }

    }

    /**
     * The Class SenderConfig.
     */
    public static class SenderConfig {

        /** The agent host. */
        private String agentHost;

        /** The agent port. */
        private Integer agentPort;

        /** The end point. */
        private String endPoint;

        /** The auth token. */
        private String authToken;

        /** The user name. */
        private String userName;

        /** The password. */
        private String password;

        /**
         * Gets the agent host.
         *
         * @return the agent host
         */
        public String getAgentHost() {
            return agentHost;
        }

        /**
         * Sets the agent host.
         *
         * @param agentHost the agent host
         * @return the sender config
         */
        public SenderConfig setAgentHost(String agentHost) {
            this.agentHost = agentHost;
            return this;
        }

        /**
         * Gets the agent port.
         *
         * @return the agent port
         */
        public Integer getAgentPort() {
            return agentPort;
        }

        /**
         * Sets the agent port.
         *
         * @param agentPort the agent port
         * @return the sender config
         */
        public SenderConfig setAgentPort(Integer agentPort) {
            this.agentPort = agentPort;
            return this;
        }

        /**
         * Gets the end point.
         *
         * @return the end point
         */
        public String getEndPoint() {
            return endPoint;
        }

        /**
         * Sets the end point.
         *
         * @param endPoint the end point
         * @return the sender config
         */
        public SenderConfig setEndPoint(String endPoint) {
            this.endPoint = endPoint;
            return this;
        }

        /**
         * Gets the auth token.
         *
         * @return the auth token
         */
        public String getAuthToken() {
            return authToken;
        }

        /**
         * Sets the auth token.
         *
         * @param authToken the auth token
         * @return the sender config
         */
        public SenderConfig setAuthToken(String authToken) {
            this.authToken = authToken;
            return this;
        }

        /**
         * Gets the user name.
         *
         * @return the user name
         */
        public String getUserName() {
            return userName;
        }

        /**
         * Sets the user name.
         *
         * @param userName the user name
         * @return the sender config
         */
        public SenderConfig setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        /**
         * Gets the password.
         *
         * @return the password
         */
        public String getPassword() {
            return password;
        }

        /**
         * Sets the password.
         *
         * @param password the password
         * @return the sender config
         */
        public SenderConfig setPassword(String password) {
            this.password = password;
            return this;
        }

    }

    /**
     * The Class ReporterConfig.
     */
    public static class ReporterConfig {

        /** The reporter log spans. */
        private boolean reporterLogSpans;

        /** The reporter max queue size. */
        private Integer reporterMaxQueueSize;

        /** The reporter flush interval. */
        private Integer reporterFlushInterval;

        /**
         * Checks if is reporter log spans.
         *
         * @return true, if is reporter log spans
         */
        public boolean isReporterLogSpans() {
            return reporterLogSpans;
        }

        /**
         * Sets the reporter log spans.
         *
         * @param reporterLogSpans the reporter log spans
         * @return the reporter config
         */
        public ReporterConfig setReporterLogSpans(boolean reporterLogSpans) {
            this.reporterLogSpans = reporterLogSpans;
            return this;
        }

        /**
         * Gets the reporter max queue size.
         *
         * @return the reporter max queue size
         */
        public Integer getReporterMaxQueueSize() {
            return reporterMaxQueueSize;
        }

        /**
         * Sets the reporter max queue size.
         *
         * @param reporterMaxQueueSize the reporter max queue size
         * @return the reporter config
         */
        public ReporterConfig setReporterMaxQueueSize(Integer reporterMaxQueueSize) {
            this.reporterMaxQueueSize = reporterMaxQueueSize;
            return this;
        }

        /**
         * Gets the reporter flush interval.
         *
         * @return the reporter flush interval
         */
        public Integer getReporterFlushInterval() {
            return reporterFlushInterval;
        }

        /**
         * Sets the reporter flush interval.
         *
         * @param reporterFlushInterval the reporter flush interval
         * @return the reporter config
         */
        public ReporterConfig setReporterFlushInterval(Integer reporterFlushInterval) {
            this.reporterFlushInterval = reporterFlushInterval;
            return this;
        }

    }

    /**
     * The Class CodecConfig.
     */
    public static class CodecConfig {

        /** The propagation. */
        private String propagation;

        /**
         * Gets the propagation.
         *
         * @return the propagation
         */
        public String getPropagation() {
            return propagation;
        }

        /**
         * Sets the propagation.
         *
         * @param propagation the propagation
         * @return the codec config
         */
        public CodecConfig setPropagation(String propagation) {
            this.propagation = propagation;
            return this;
        }

    }

    /**
     * The Class TracerConfig.
     */
    public static class TracerConfig {

        /** The trace id 128 bit. */
        private boolean traceId128Bit;

        /** The metrics factory. */
        private Class<? extends MetricsFactory> metricsFactory;

        /** The tracer tags. */
        private Map<String, String> tracerTags = new HashMap<>();

        /**
         * Checks if is trace id 128 bit.
         *
         * @return true, if is trace id 128 bit
         */
        public boolean isTraceId128Bit() {
            return traceId128Bit;
        }

        /**
         * Sets the trace id 128 bit.
         *
         * @param traceId128Bit the trace id 128 bit
         * @return the tracer config
         */
        public TracerConfig setTraceId128Bit(boolean traceId128Bit) {
            this.traceId128Bit = traceId128Bit;
            return this;
        }

        /**
         * Gets the metrics factory.
         *
         * @return the metrics factory
         */
        public Class<? extends MetricsFactory> getMetricsFactory() {
            return metricsFactory;
        }

        /**
         * Sets the metrics factory.
         *
         * @param metricsFactory the metrics factory
         * @return the tracer config
         */
        public TracerConfig setMetricsFactory(Class<? extends MetricsFactory> metricsFactory) {
            this.metricsFactory = metricsFactory;
            return this;
        }

        /**
         * Gets the tracer tags.
         *
         * @return the tracer tags
         */
        public Map<String, String> getTracerTags() {
            return tracerTags == null ? null : Collections.unmodifiableMap(tracerTags);
        }

        /**
         * Sets the tracer tags.
         *
         * @param tracerTags the tracer tags
         * @return the tracer config
         */
        public TracerConfig setTracerTags(Map<String, String> tracerTags) {
            this.tracerTags = tracerTags;
            return this;
        }

    }

}