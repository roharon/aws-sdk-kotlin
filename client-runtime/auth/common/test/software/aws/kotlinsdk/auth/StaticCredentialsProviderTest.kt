/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0.
 */

package software.aws.kotlinsdk.auth

import software.aws.kotlinsdk.testing.runSuspendTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StaticCredentialsProviderTest {
    @Test
    fun testStaticProvider() = runSuspendTest {
        val expected = Credentials("access_key_id", "secret_access_key", "session_token")
        val provider = StaticCredentialsProvider.fromCredentials(expected)
        assertEquals(expected, provider.getCredentials())

        val provider2 = StaticCredentialsProvider.build {
            accessKeyId = expected.accessKeyId
            secretAccessKey = expected.secretAccessKey
            sessionToken = expected.sessionToken
        }
        assertEquals(expected, provider2.getCredentials())
    }
}
