/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0.
 */
package aws.sdk.kotlin.runtime.protocol.xml

import aws.sdk.kotlin.runtime.InternalSdkApi
import aws.sdk.kotlin.runtime.http.ExceptionRegistry
import aws.sdk.kotlin.runtime.http.middleware.errors.AbstractErrorHandling
import aws.sdk.kotlin.runtime.http.middleware.errors.ErrorDetails
import aws.smithy.kotlin.runtime.http.FeatureKey
import aws.smithy.kotlin.runtime.http.Headers

/**
 * Http feature that inspects responses and throws the appropriate modeled service error that matches
 *
 * @property registry Modeled exceptions registered with the feature. All responses will be inspected to
 * see if one of the registered errors matches
 */
@InternalSdkApi
public class RestXmlError(registry: ExceptionRegistry) : AbstractErrorHandling(registry) {
    public companion object Feature : AbstractFeature<RestXmlError>() {
        override val key: FeatureKey<RestXmlError> = FeatureKey("RestXmlError")
        override fun create(config: Config): RestXmlError = RestXmlError(config.registry)
    }

    protected override val protocolName: String = "restXml"
    override suspend fun parseErrorResponse(headers: Headers, payload: ByteArray?): ErrorDetails =
        parseRestXmlErrorResponse(payload ?: emptyByteArray)
}