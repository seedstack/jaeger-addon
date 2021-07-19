/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.jaeger.internal;

import java.lang.reflect.Field;

import org.seedstack.jaeger.JaegerConfig;
import org.seedstack.jaeger.ServiceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import io.opentracing.Tracer;

/**
 * TypeListener for Jaeger Tracer.
 */
class TracerTypeListener implements TypeListener {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TracerTypeListener.class);

    /** The jaeger config. */
    private JaegerConfig jaegerConfig;

    /**
     * @param jaegerConfig
     */
    TracerTypeListener(JaegerConfig jaegerConfig) {
        this.jaegerConfig = jaegerConfig;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.google.inject.spi.TypeListener#hear(com.google.inject.TypeLiteral, com.google.inject.spi.TypeEncounter)
     */
    @Override
    public <T> void hear(TypeLiteral<T> type, TypeEncounter<T> encounter) {

        for (Field field : type.getRawType().getDeclaredFields()) {
            if (field.getType() == Tracer.class && field.isAnnotationPresent(ServiceName.class)) {
                LOGGER.info("Resgister with the TracerMemberInjector");
                encounter.register(new TracerMemberInjector<>(field, jaegerConfig));

            }
        }
    }
}