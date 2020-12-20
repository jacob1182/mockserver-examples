package org.jasi.mockserver.examples

import org.mockserver.client.MockServerClient
import org.mockserver.matchers.TimeToLive
import org.mockserver.matchers.Times
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.springframework.web.client.RestTemplate
import java.util.concurrent.TimeUnit

const val PATH = "/some/path"
const val BODY = "some_response_body"

fun makeRequest(port: Int): String {
    val restTemplate = RestTemplate()
    return restTemplate.getForObject("http://localhost:$port$PATH", String::class.java)
            ?: throw IllegalStateException("Null result")
}


fun mockRequest(mockServerClient: MockServerClient, path: String = PATH) {
    mockServerClient.`when`(
            HttpRequest.request().withPath(path).withMethod("get"),
            Times.once(),
            TimeToLive.exactly(TimeUnit.SECONDS, 60L),
            10
    )
            .respond(HttpResponse.response().withBody(BODY))
}
