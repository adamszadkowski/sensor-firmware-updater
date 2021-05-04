package info.szadkowski.sensor.firmware.updater.configuration

import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.config.MeterFilter
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Value
import javax.inject.Singleton


@Factory
class MeterFilterFactory {

    @Bean
    @Singleton
    fun addCommonTagFilter(@Value("\${micronaut.application.name}") name: String): MeterFilter =
        MeterFilter.commonTags(listOf(Tag.of("application", name)))
}
