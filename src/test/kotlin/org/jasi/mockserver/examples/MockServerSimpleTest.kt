package org.jasi.mockserver.examples

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.integration.ClientAndServer
import org.mockserver.integration.ClientAndServer.startClientAndServer
import org.mockserver.model.HttpRequest.request
import org.mockserver.verify.VerificationTimes.once


class MockServerSimpleTest {

    private var mockServer: ClientAndServer? = null

    @BeforeEach
    fun startMockServer() {
        mockServer = startClientAndServer(1090)
    }

    @AfterEach
    fun stopMockServer() {
        mockServer?.stop()
    }

    @Test
    fun testSomething() {
        val mockServer = mockServer ?: throw IllegalStateException("Null value")
        mockRequest(mockServer)

        val result = makeRequest(1090)
        assertEquals(BODY, result)

        mockServer.verify(request().withPath(PATH), once())
    }
}