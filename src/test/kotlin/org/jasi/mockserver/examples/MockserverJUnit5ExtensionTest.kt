package org.jasi.mockserver.examples

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.integration.ClientAndServer
import org.mockserver.junit.jupiter.MockServerExtension
import org.mockserver.junit.jupiter.MockServerSettings
import org.mockserver.model.HttpRequest.request
import org.mockserver.verify.VerificationTimes.once


@ExtendWith(MockServerExtension::class)
@MockServerSettings(ports = [8787, 8888])
class MockserverJUnit5ExtensionTest(val client: ClientAndServer) {

    @Test
    fun testSomething() {
        val mockServer = client
        mockRequest(mockServer)

        val result = makeRequest(8787)
        assertEquals(BODY, result)

        client.verify(request().withPath(PATH), once())
    }
}