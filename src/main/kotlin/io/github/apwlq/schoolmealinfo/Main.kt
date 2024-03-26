package io.github.apwlq.schoolmealinfo

/*
 * @author Bruce0203, apwlq
 * @license MIT License
 */

import com.github.instagram4j.instagram4j.actions.timeline.TimelineAction
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if(args[0] == "timeline") {
        timeline()
    }
    else if (args[0] == "story") {
        story()
    }
    else {
        story()
        timeline()
    }
    exitProcess(0)
}

fun timeline() {
    val client = login()
    val albumFiles: MutableList<TimelineAction.SidecarInfo> = mutableListOf()
//    albumFiles.add(
//        TimelineAction.SidecarPhoto.from(
//            File("assets/image/timeline_intro.jpg")
//        )
//    )
    if(!getBreakfast().isNullOrEmpty())
        albumFiles.add(
            TimelineAction.SidecarPhoto.from(
                genTimelineImage("오늘의 아침", getBreakfast(), getBreakfastKcal())
            )
        )
    if(!getLunch().isNullOrEmpty())
        albumFiles.add(
            TimelineAction.SidecarPhoto.from(
                genTimelineImage("오늘의 점심", getLunch(), getLunchKcal())
            )
        )
    if(!getDinner().isNullOrEmpty())
        albumFiles.add(
            TimelineAction.SidecarPhoto.from(
                genTimelineImage("오늘의 저녁", getDinner(), getDinnerKcal())
            )
        )

    client.actions().timeline().uploadAlbum(albumFiles, "${getNowDate("yyyy년 MM월 dd일")} 오늘의 급식")
        .thenAccept {
            println("타임라인에 성공적으로 급식을 업로드했습니다!".trimIndent())
        }
        .join()
}

fun story() {
    val client = login()
    client.actions().story().uploadPhoto(genStoryImage("오늘의 급식", getLunch(), getLunchKcal(), getDinner(), getDinnerKcal()))
        .thenAccept {
            println("스토리에 성공적으로 아침 급식을 업로드했습니다!".trimIndent())
        }
        .join()
}