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

@Config("jaeger")
public class JaegerConfig {

    private boolean devMode;
    private SamplerConfig samplerConfig;
    private SenderConfig senderConfig;
    private ReporterConfig reporterConfig;
    private CodecConfig codecConfig;
    private TracerConfig tracerConfig;

    public boolean isDevMode() {
        return devMode;
    }

    public JaegerConfig setDevMode(boolean devMode) {
        this.devMode = devMode;
        return this;
    }

    public SamplerConfig getSamplerConfig() {
        return samplerConfig;
    }

    public JaegerConfig setSamplerConfig(SamplerConfig samplerConfig) {
        this.samplerConfig = samplerConfig;
        return this;
    }

    public SenderConfig getSenderConfig() {
        return senderConfig;
    }

    public JaegerConfig setSenderConfig(SenderConfig senderConfig) {
        this.senderConfig = senderConfig;
        return this;
    }

    public ReporterConfig getReporterConfig() {
        return reporterConfig;
    }

    public JaegerConfig setReporterConfig(ReporterConfig reporterConfig) {
        this.reporterConfig = reporterConfig;
        return this;
    }

    public CodecConfig getCodecConfig() {
        return codecConfig;
    }

    public JaegerConfig setCodecConfig(CodecConfig codecConfig) {
        this.codecConfig = codecConfig;
        return this;
    }

    public TracerConfig getTracerConfig() {
        return tracerConfig;
    }

    public JaegerConfig setTracerConfig(TracerConfig tracerConfig) {
        this.tracerConfig = tracerConfig;
        return this;
    }

    public static class SamplerConfig {

        private String samplerType;
        private Integer samplerParam;
        private String samplerManagerHostPort;

        public String getSamplerType() {
            return samplerType;
        }

        public SamplerConfig setSamplerType(String samplerType) {
            this.samplerType = samplerType;
            return this;
        }

        public Integer getSamplerParam() {
            return samplerParam;
        }

        public SamplerConfig setSamplerParam(Integer samplerParam) {
            this.samplerParam = samplerParam;
            return this;
        }

        public String getSamplerManagerHostPort() {
            return samplerManagerHostPort;
        }

        public SamplerConfig setSamplerManagerHostPort(String samplerManagerHostPort) {
            this.samplerManagerHostPort = samplerManagerHostPort;
            return this;
        }

    }

    public static class SenderConfig {

        private String agentHost;
        private Integer agentPort;
        private String endPoint;
        private String authToken;
        private String userName;
        private String password;

        public String getAgentHost() {
            return agentHost;
        }

        public SenderConfig setAgentHost(String agentHost) {
            this.agentHost = agentHost;
            return this;
        }

        public Integer getAgentPort() {
            return agentPort;
        }

        public SenderConfig setAgentPort(Integer agentPort) {
            this.agentPort = agentPort;
            return this;
        }

        public String getEndPoint() {
            return endPoint;
        }

        public SenderConfig setEndPoint(String endPoint) {
            this.endPoint = endPoint;
            return this;
        }

        public String getAuthToken() {
            return authToken;
        }

        public SenderConfig setAuthToken(String authToken) {
            this.authToken = authToken;
            return this;
        }

        public String getUserName() {
            return userName;
        }

        public SenderConfig setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public SenderConfig setPassword(String password) {
            this.password = password;
            return this;
        }

    }

    public static class ReporterConfig {

        private boolean reporterLogSpans;
        private Integer reporterMaxQueueSize;
        private Integer reporterFlushInterval;

        public boolean isReporterLogSpans() {
            return reporterLogSpans;
        }

        public ReporterConfig setReporterLogSpans(boolean reporterLogSpans) {
            this.reporterLogSpans = reporterLogSpans;
            return this;
        }

        public Integer getReporterMaxQueueSize() {
            return reporterMaxQueueSize;
        }

        public ReporterConfig setReporterMaxQueueSize(Integer reporterMaxQueueSize) {
            this.reporterMaxQueueSize = reporterMaxQueueSize;
            return this;
        }

        public Integer getReporterFlushInterval() {
            return reporterFlushInterval;
        }

        public ReporterConfig setReporterFlushInterval(Integer reporterFlushInterval) {
            this.reporterFlushInterval = reporterFlushInterval;
            return this;
        }

    }

    public static class CodecConfig {

        private String propagation;

        public String getPropagation() {
            return propagation;
        }

        public CodecConfig setPropagation(String propagation) {
            this.propagation = propagation;
            return this;
        }

    }

    public static class TracerConfig {

        private boolean traceId128Bit;
        private Class<? extends MetricsFactory> metricsFactory;
        private Map<String, String> tracerTags = new HashMap();

        public boolean isTraceId128Bit() {
            return traceId128Bit;
        }

        public TracerConfig setTraceId128Bit(boolean traceId128Bit) {
            this.traceId128Bit = traceId128Bit;
            return this;
        }

        public Class<? extends MetricsFactory> getMetricsFactory() {
            return metricsFactory;
        }

        public TracerConfig setMetricsFactory(Class<? extends MetricsFactory> metricsFactory) {
            this.metricsFactory = metricsFactory;
            return this;
        }

        public Map<String, String> getTracerTags() {
            return tracerTags == null ? null : Collections.unmodifiableMap(tracerTags);
        }

        public TracerConfig setTracerTags(Map<String, String> tracerTags) {
            this.tracerTags = tracerTags;
            return this;
        }

    }

}