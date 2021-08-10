/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.jaeger.internal;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import io.opentracing.Tracer;
import org.seedstack.jaeger.JaegerConfig;
import org.seedstack.jaeger.Tracing;

import java.lang.reflect.Field;

class TracerTypeListener implements TypeListener {
    private final JaegerConfig jaegerConfig;

    TracerTypeListener(JaegerConfig jaegerConfig) {
        this.jaegerConfig = jaegerConfig;
    }

    @Override
    public <T> void hear(TypeLiteral<T> type, TypeEncounter<T> encounter) {

        for (Field field : type.getRawType().getDeclaredFields()) {
            if (field.getType() == Tracer.class && field.isAnnotationPresent(Tracing.class)) {
                encounter.register(new TracerMemberInjector<>(field, jaegerConfig));
            }
        }
    }
}