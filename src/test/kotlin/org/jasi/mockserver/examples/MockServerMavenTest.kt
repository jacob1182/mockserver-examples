package org.jasi.mockserver.examples

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest.request
import org.mockserver.verify.VerificationTimes.once

class MockServerMavenTest {

    val mockServer = MockServerClient("localhost", 1080)

    @Test
    fun testSomething() {
        mockRequest(mockServer)

        val result = makeRequest(1080)
        assertEquals(BODY, result)

        mockServer.verify(request().withPath(PATH), once())
    }
}