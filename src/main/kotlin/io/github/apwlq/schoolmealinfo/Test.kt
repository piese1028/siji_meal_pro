package io.github.apwlq.schoolmealinfo

import java.awt.image.BufferedImage
import java.io.*
import java.util.*
import javax.imageio.ImageIO
import kotlin.system.exitProcess


fun main()  {
    print("[점심]\n${getLunch()}\n[저녁]\n${getDinner()}")
    val prop = Properties()
    val file = File("assets/config/drawing.properties")
    FileInputStream(file).use { fileInputStream ->
        InputStreamReader(fileInputStream, "UTF-8").use { inputStreamReader ->
            prop.load(inputStreamReader)
        }
    }
    genImage("[점심]\n${getLunch()}","[저녁]\n${getDinner()}")
}