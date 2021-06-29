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

/*
 * TypeListener for Jaeger Tracer 
 */
class TracerTypeListener implements TypeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(TracerTypeListener.class);
    private JaegerConfig jaegerConfig;

    TracerTypeListener(JaegerConfig jaegerConfig) {
        this.jaegerConfig = jaegerConfig;
    }

    @Override
    public <T> void hear(TypeLiteral<T> type, TypeEncounter<T> encounter) {
        LOGGER.info("Resgister with the TracerMemberInjector");
        for (Field field : type.getRawType().getDeclaredFields()) {
            if (field.getType() == Tracer.class && field.isAnnotationPresent(ServiceName.class)) {

                encounter.register(new TracerMemberInjector<>(field, jaegerConfig));

            }
        }
    }
}