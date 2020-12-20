package org.jasi.mockserver.examples

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest.request
import org.mockserver.springtest.MockServerTest
import org.mockserver.verify.VerificationTimes.once
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate
import java.net.URI
import javax.annotation.Nullable

@ExtendWith(SpringExtension::class)
@MockServerTest("server.url=http://localhost:\${mockServerPort}/resource")
@ContextConfiguration(classes = [MockServerSpringClientTest.Config::class])
class MockServerSpringClientTest {

    internal class Client {
        @Value("\${server.url}")
        private val serverUrl: URI? = null
        private val restTemplate = RestTemplate()

        @Nullable
        fun <T> getResult(path: String, responseType: Class<T>): T {
            return restTemplate.getForObject("$serverUrl$path", responseType)
                    ?: throw IllegalStateException("Null result")
        }
    }

    internal class Config {
        @Bean
        fun client(): Client {
            return Client()
        }
    }

    @Autowired
    private lateinit var client: Client
    private lateinit var mockServerClient: MockServerClient

    @Test
    fun testSomething() {
        val mockedPath = "/resource$PATH"
        mockRequest(mockServerClient, mockedPath)

        val result = client.getResult(PATH, String::class.java)
        assertEquals(BODY, result)

        mockServerClient.verify(request().withPath(mockedPath), once())
    }
}