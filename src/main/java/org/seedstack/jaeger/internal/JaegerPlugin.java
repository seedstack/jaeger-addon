/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.jaeger.internal;

import org.seedstack.jaeger.JaegerConfig;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;

/**
 * Plugin for Jaeger Tracer
 */
public class JaegerPlugin extends AbstractSeedPlugin {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JaegerPlugin.class);

    /** The jaeger config. */
    private JaegerConfig jaegerConfig;

    /**
     * {@inheritDoc}
     * 
     * @see io.nuun.kernel.core.AbstractPlugin#name()
     */
    @Override
    public String name() {
        return "jaeger";
    }

    @Override
    public InitState initialize(InitContext initContext) {
        jaegerConfig = getConfiguration(JaegerConfig.class);
        LOGGER.info("JaegerPlugin Initialized");
        return InitState.INITIALIZED;
    }

    @Override
    public Object nativeUnitModule() {

        return new JaegerModule(jaegerConfig);
    }

}
