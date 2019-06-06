package example

import kotlin.test.Test
import kotlin.test.assertTrue

class CommonPlatformTests {
    @Test
    fun testPlatform() {
        assertTrue(platformName.isNotBlank())
    }
}