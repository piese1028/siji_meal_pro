package io.github.apwlq.schoolmealinfo

import java.awt.image.BufferedImage
import java.io.*
import java.util.*
import javax.imageio.ImageIO
import kotlin.system.exitProcess


fun main()  {

    genStoryImage("[점심]\n${getLunch()}\n\n[저녁]\n${getDinner()}")
    genTimelineImage("[점심]\n${getLunch()}","[저녁]\n${getDinner()}")
}