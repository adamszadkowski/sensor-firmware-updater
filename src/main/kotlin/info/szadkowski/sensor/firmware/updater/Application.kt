package info.szadkowski.sensor.firmware.updater

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("info.szadkowski.sensor.firmware.updater")
        .start()
}

