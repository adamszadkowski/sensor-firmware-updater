package info.szadkowski.sensor.firmware.updater

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
        .args(*args)
        .environments("local")
        .packages("info.szadkowski.sensor.firmware.updater")
        .start()
}

