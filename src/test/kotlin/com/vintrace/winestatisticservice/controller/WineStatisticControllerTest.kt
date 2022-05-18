package com.vintrace.winestatisticservice.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*

@WebFluxTest
class WineStatisticControllerTest(@Autowired val webTestClient: WebTestClient) {

    @Test
    fun year() {
        webTestClient.get().uri("/api/breakdown/year/11YVCHAR001")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.breakDownType").isEqualTo("year")
            .jsonPath("$.breakDown[0].percentage").isEqualTo(85.0)
            .jsonPath("$.breakDown[0].key").isEqualTo(2011)
    }

    @Test
    fun yearFailed() {
        webTestClient.get().uri("/api/breakdown/year/11YVCHAR005")
            .exchange()
            .expectStatus().is4xxClientError
            .expectStatus().isBadRequest
    }

    @Test
    fun variety() {
        webTestClient.get().uri("/api/breakdown/variety/11YVCHAR001")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.breakDownType").isEqualTo("variety")
            .jsonPath("$.breakDown[0].percentage").isEqualTo(121.0)
            .jsonPath("$.breakDown[0].key").isEqualTo("Chardonnay")
    }

    @Test
    fun region() {
        webTestClient.get().uri("/api/breakdown/region/11YVCHAR001")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.breakDownType").isEqualTo("region")
            .jsonPath("$.breakDown[0].percentage").isEqualTo(100.0)
            .jsonPath("$.breakDown[0].key").isEqualTo("Yarra Valley")
    }

    @Test
    fun yearVariety() {
        webTestClient.get().uri("/api/breakdown/year-variety/11YVCHAR001")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.breakDownType").isEqualTo("year-variety")
            .jsonPath("$.breakDown[0].percentage").isEqualTo(80.0)
            .jsonPath("$.breakDown[0].key").isEqualTo(Arrays.asList(2011, "Chardonnay").toString())
    }
}