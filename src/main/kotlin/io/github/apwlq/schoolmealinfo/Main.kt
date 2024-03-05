package io.github.apwlq.schoolmealinfo

/*
 * @author Bruce0203, apwlq
 * @license MIT License
 */

import io.github.apwlq.schoolmealinfo.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    publish()
}

fun publish() {
    println("publishing...")
    val client = login()
    client.actions()
        .timeline()
        .uploadPhoto(genImage("[점심]\n${getLunch()}","[저녁]\n${getDinner()}"),"(${getNowDate()}) 의 급식")
        .thenAccept {
            println(
                """
                    --------------------------
                   "Successfully uploaded photo!" 
                    --------------------------
                """.trimIndent()
            )
        }
        .join() // block current thread until complete
    exitProcess(0)
}

