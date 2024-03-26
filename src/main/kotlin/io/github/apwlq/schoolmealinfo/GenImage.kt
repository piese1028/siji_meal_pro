package io.github.apwlq.schoolmealinfo

/*
 * @author Bruce0203
 * @license MIT License
 */

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun genTimelineImage(title: String, meal: String?, kcal: String?): File? {
    if (meal.isNullOrEmpty()) return null
    val png = File("output/timeline_dist.png")
    AddTextToImgByTimeline.execute(File("assets/image/timeline.png"), title, meal, kcal, png, getNowDate("yyyy.MM.dd"))
    val jpg = File("output/timeline_dist.jpg")
    pngToJpg(png, jpg)
    return jpg
}

fun genStoryImage(title: String, meal: String? = "", kcal: String? = "", meal2: String? = "", kcal2: String? = ""): File? {
    if (meal.isNullOrEmpty() || kcal.isNullOrEmpty()) return null
    var meal2 = meal2
    var kcal2 = kcal2
    if (meal2.isNullOrEmpty() || kcal2.isNullOrEmpty()) {
        meal2 = "급식이 없습니다"
        kcal2 = "???"
    }

    val png = File("output/story_dist.png")
    AddTextToImgByStory.execute(File("assets/image/story.png"), title, meal, kcal, png, getNowDate("yyyy.MM.dd"), meal2, kcal2)
    val jpg = File("output/story_dist.jpg")
    pngToJpg(png, jpg)
    return jpg
}

fun pngToJpg(png: File, jpg: File) {
    val beforeImg = ImageIO.read(png)
    val afterImg = BufferedImage(beforeImg.width, beforeImg.height, BufferedImage.TYPE_INT_RGB)
    afterImg.createGraphics().drawImage(beforeImg, 0, 0, Color.white, null)
    ImageIO.write(afterImg, "jpg", jpg)
}
