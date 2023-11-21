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
    println("오늘 밥 뭐지...")
    val client = login()
    client.actions()
        .story()
        .uploadPhoto(genImage("${prop["lunch"].toString()}\n${getLunch()}","${prop["dinner"].toString()}\n${getDinner()}"))
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

