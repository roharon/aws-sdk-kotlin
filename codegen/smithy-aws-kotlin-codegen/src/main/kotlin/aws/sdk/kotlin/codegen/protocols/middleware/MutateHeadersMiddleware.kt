/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0.
 */

package aws.sdk.kotlin.codegen.protocols.middleware

import software.amazon.smithy.kotlin.codegen.core.KotlinWriter
import software.amazon.smithy.kotlin.codegen.core.RuntimeTypes
import software.amazon.smithy.kotlin.codegen.rendering.protocol.ProtocolMiddleware

/**
 * General purpose middleware that allows mutation of headers
 */
class MutateHeadersMiddleware(
    val extraHeaders: Map<String, String> = emptyMap(),
    val overrideHeaders: Map<String, String> = emptyMap(),
    val addMissingHeaders: Map<String, String> = emptyMap(),
) : ProtocolMiddleware {
    override val name: String = "MutateHeaders"
    override fun renderConfigure(writer: KotlinWriter) {
        writer.addImport(RuntimeTypes.Http.MutateHeadersMiddleware)
        overrideHeaders.forEach {
            writer.write("set(#S, #S)", it.key, it.value)
        }
        extraHeaders.forEach {
            writer.write("append(#S, #S)", it.key, it.value)
        }
        addMissingHeaders.forEach {
            writer.write("setIfMissing(#S, #S)", it.key, it.value)
        }
    }
}