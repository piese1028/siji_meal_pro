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
        .uploadPhoto(genTimelineImage("[점심]\n${getLunch()}","[저녁]\n${getDinner()}"),"${getNowDate()} 급식")
        .thenAccept {
            println(
                """
                    --------------------------
                   "Successfully uploaded timeline!" 
                    --------------------------
                """.trimIndent()
            )
        }
        .join() // block current thread until complete
    client.actions()
        .story()
        .uploadPhoto(genStoryImage("[점심]\n${getLunch()}\n\n[저녁]\n${getDinner()}"))
        .thenAccept {
            println(
                """
                    --------------------------
                   "Successfully uploaded story!" 
                    --------------------------
                """.trimIndent()
            )
        }
        .join() // block current thread until complete
    exitProcess(0)
}

