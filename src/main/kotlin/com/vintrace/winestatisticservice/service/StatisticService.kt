package com.vintrace.winestatisticservice.service

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.vintrace.winestatisticservice.controller.BreakDown
import com.vintrace.winestatisticservice.controller.Statistics
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import java.net.URL

@Service
open class StatisticService()
{
    @Throws(Exception::class)
    fun byYear(lotCode: String) : Mono<Statistics> {
        val resource: URL = this::class.java.getResource("/json/$lotCode.json")
            ?: return Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid lotCode"))
        return Mono.just(jacksonObjectMapper().readValue(resource, WineData::class.java))
                .onErrorMap { e -> throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went Wrong", e) }
                .flatMap { wineData ->
                    Mono.just(Statistics("year", wineData.components.groupBy { it.year }
                            .mapValues { (_, percentage) -> percentage.sumOf { it.percentage } }
                            .map { BreakDown(it.value, it.key.toString()) }
                            .sortedByDescending { it.percentage }
                    ))
                }

    }

    fun byVariety(lotCode: String) :Mono<Statistics> {
        val resource: URL = this::class.java.getResource("/json/$lotCode.json")
            ?: return Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid lotCode"))
        return Mono.just(jacksonObjectMapper().readValue(resource, WineData::class.java))
                .onErrorMap { e -> throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went Wrong", e) }
                .flatMap { wineData ->
                    Mono.just(Statistics("variety", wineData.components.groupBy { it.variety }
                            .mapValues { (_, percentage) -> percentage.sumOf { it.percentage } }
                            .map { BreakDown(it.value, it.key) }
                            .sortedByDescending { it.percentage }
                    ))
                }

    }

    fun byRegion(lotCode: String) :Mono<Statistics> {
        val resource: URL = this::class.java.getResource("/json/$lotCode.json")
            ?: return Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid lotCode"))
        return Mono.just(jacksonObjectMapper().readValue(resource, WineData::class.java))
                .onErrorMap { e -> throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went Wrong", e) }
                .flatMap { wineData ->
                    Mono.just(Statistics("region", wineData.components.groupBy { it.region }
                            .mapValues { (_, percentage) -> percentage.sumOf { it.percentage } }
                            .map { BreakDown(it.value, it.key) }
                            .sortedByDescending { it.percentage }
                    ))
                }
    }

    fun byYearVariety(lotCode: String) :Mono<Statistics> {
        val resource: URL = this::class.java.getResource("/json/$lotCode.json")
            ?: return Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid lotCode"))
        return Mono.just(jacksonObjectMapper().readValue(resource, WineData::class.java))
                .onErrorMap { e -> throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went Wrong", e) }
                .flatMap { wineData ->
                    Mono.just(Statistics("year-variety", wineData.components.groupBy( {listOf(it.year, it.variety)})
                            .mapValues { (_, percentage) -> percentage.sumOf { it.percentage } }
                            .map { BreakDown(it.value, it.key.toString()) }
                            .sortedByDescending { it.percentage }
                    ))
                }
    }
}

@JsonSerialize
data class WineData(val lotCode:String,
                    val volume:Double,
                    val description: String?,
                    val tankCode:String,
                    val productState: String?,
                    val ownerName: String,
                    val components: ArrayList<Component>)

data class Component (val percentage:Double,
                      val year: Int,
                      val variety:String,
                      val region: String)