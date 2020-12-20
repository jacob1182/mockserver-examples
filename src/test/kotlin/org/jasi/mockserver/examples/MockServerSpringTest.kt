package org.jasi.mockserver.examples

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest.request
import org.mockserver.springtest.MockServerPort
import org.mockserver.springtest.MockServerTest
import org.mockserver.verify.VerificationTimes.once
import org.springframework.test.context.junit.jupiter.SpringExtension


@MockServerTest
@ExtendWith(SpringExtension::class)
class MockServerSpringTest {

    @MockServerPort
    private var port: Int = 0

    private lateinit var client: MockServerClient

    @Test
    fun testSomething() {
        val mockServer = client
        mockRequest(mockServer)

        val result = makeRequest(port)
        assertEquals(BODY, result)

        client.verify(request().withPath(PATH), once())
    }
}