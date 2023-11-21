package io.github.apwlq.schoolmealinfo

/*
 * @author Bruce0203
 * @license MIT License
 */

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun genImage(lunch: String, dinner: String): File {
    val png = File("output/dist.png")
    AddTextToImg.execute(File("assets/image/image.png"), lunch, dinner, png)
    val jpg = File("output/dist.jpg")
    pngToJpg(png, jpg)
    return jpg
}

fun pngToJpg(png: File, jpg: File) {
    val beforeImg = ImageIO.read(png)
    val afterImg = BufferedImage(beforeImg.width, beforeImg.height, BufferedImage.TYPE_INT_RGB)
    afterImg.createGraphics().drawImage(beforeImg, 0, 0, Color.white, null)
    ImageIO.write(afterImg, "jpg", jpg)
}
