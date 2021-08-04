/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.jaeger.internal;

import org.seedstack.shed.exception.ErrorCode;

/**
 * ErrorCode for Jaeger
 */
public enum JaegerErrorCode implements ErrorCode {

    ERROR_LOADING, ERROR_INSTANTIATING_METRICS_FACTORY
}