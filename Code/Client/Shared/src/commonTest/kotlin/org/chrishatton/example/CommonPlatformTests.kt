package org.chrishatton.example

import org.chrishatton.example.platformName
import kotlin.test.Test
import kotlin.test.assertTrue

class CommonPlatformTests {
    @Test
    fun testPlatform() {
        assertTrue(platformName.isNotBlank())
    }
}