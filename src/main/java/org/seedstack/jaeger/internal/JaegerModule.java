/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.jaeger.internal;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import org.seedstack.jaeger.JaegerConfig;

class JaegerModule extends AbstractModule {
    private final JaegerConfig jaegerConfig;

    JaegerModule(JaegerConfig jaegerConfig) {
        this.jaegerConfig = jaegerConfig;
    }

    @Override
    protected void configure() {
        bindListener(Matchers.any(), new TracerTypeListener(jaegerConfig));
    }
}
