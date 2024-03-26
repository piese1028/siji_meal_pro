package io.github.apwlq.schoolmealinfo

/*
 * @author Bruce0203
 * @license MIT License
 */

import java.awt.*
import java.io.File
import java.util.*
import javax.imageio.ImageIO



fun getFont(name: String): Font {
    val font: Font
    when (name) {
        "J" -> {
            font = Font.createFont(Font.TRUETYPE_FONT, File("assets/font/JalnanGothicTTF.ttf"))
        }
        "EB" -> {
            font = Font.createFont(Font.TRUETYPE_FONT, File("assets/font/NanumSquareEB.ttf"))
        }
        "B" -> {
            font = Font.createFont(Font.TRUETYPE_FONT, File("assets/font/NanumSquareB.ttf"))
        }
        "L" -> {
            font = Font.createFont(Font.TRUETYPE_FONT, File("assets/font/NanumSquareL.ttf"))
        }
        else -> {
            font = Font.createFont(Font.TRUETYPE_FONT, File("assets/font/NanumSquareR.ttf"))
        }
    }
    val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
    ge.registerFont(font)
    return font
}

fun hex2Rgb(colorStr: String): Color {
    var s = colorStr
    if (!s.startsWith("#")) s = "#$colorStr"
    return Color(
        Integer.parseInt(s.substring(1, 3), 16),
        Integer.parseInt(s.substring(3, 5), 16),
        Integer.parseInt(s.substring(5, 7), 16)
    )
}

object AddTextToImgByStory {
    @JvmStatic
    fun execute(file: File, title: String, meal: String, kcal: String, out: File, date: String, meal2: String, kcal2: String) {
        val image = ImageIO.read(file)
        val g2d = image.graphics
        val g = g2d as Graphics2D
        val prop = Properties()
        prop.load(File("assets/config/story.properties").bufferedReader())

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        g.color = hex2Rgb(prop["title_font_color"].toString())
        g.font = getFont("J").deriveFont(prop["title_font_size"].toString().toFloat())
        title.split("\n").forEachIndexed { ind, str ->
            val metrics = g.fontMetrics
            val textWidth = metrics.stringWidth(str)

            val x = when (prop["title_font_align"].toString()) {
                "center" -> (image.width - textWidth) / 2
                "right" -> image.width - textWidth - prop["title_font_x"].toString().toInt()
                else -> prop["title_font_x"].toString().toInt()
            }
            val y = prop["title_font_y"].toString().toInt()

            g.drawString(str, x, y)
        }

        g.color = hex2Rgb(prop["meal_font_color"].toString())
        g.font = getFont("B").deriveFont(prop["meal_font_size"].toString().toFloat())
        meal.split("\n").forEachIndexed { ind, str ->
            val metrics = g.fontMetrics
            val textWidth = metrics.stringWidth(str)

            val x = when (prop["meal_font_align"].toString()) {
                "center" -> (image.width - textWidth) / 2
                "right" -> image.width - textWidth - prop["meal_font_x"].toString().toInt()
                else -> prop["meal_font_x"].toString().toInt()
            }
            val y = prop["meal_font_y"].toString().toInt() + ind * prop["meal_font_leading"].toString().toInt()

            g.drawString(str, x, y)
        }

        g.color = hex2Rgb(prop["meal_font_color"].toString())
        g.font = getFont("B").deriveFont(prop["meal_font_size"].toString().toFloat())
        meal2.split("\n").forEachIndexed { ind, str ->
            val metrics = g.fontMetrics
            val textWidth = metrics.stringWidth(str)

            val x = when (prop["meal_font_align"].toString()) {
                "center" -> (image.width - textWidth) / 2
                "right" -> image.width - textWidth - prop["meal_font_x2"].toString().toInt()
                else -> prop["meal_font_x2"].toString().toInt()
            }
            val y = prop["meal_font_y2"].toString().toInt() + ind * prop["meal_font_leading"].toString().toInt()

            g.drawString(str, x, y)
        }

        g.color = hex2Rgb(prop["school_font_color"].toString())
        g.font = getFont("B").deriveFont(prop["school_font_size"].toString().toFloat())
            System.getenv("SCHOOL_NAME").split("\n").forEachIndexed { ind, str ->
            val metrics = g.fontMetrics
            val textWidth = metrics.stringWidth(str)

            val x = when (prop["school_font_align"].toString()) {
                "center" -> (image.width - textWidth) / 2
                "right" -> image.width - textWidth - prop["school_font_x"].toString().toInt()
                else -> prop["school_font_x"].toString().toInt()
            }
            val y = prop["school_font_y"].toString().toInt()

            g.drawString(str, x, y)
        }

        g.color = hex2Rgb(prop["kcal_font_color"].toString())
        g.font = getFont("EB").deriveFont(prop["kcal_font_size"].toString().toFloat())
        g.drawString(kcal.substring(0,3),
            prop["kcal_font_x"].toString().toInt(),
            prop["kcal_font_y"].toString().toInt())

        g.drawString(kcal.substring(0,3),
            prop["kcal_font_x2"].toString().toInt(),
            prop["kcal_font_y2"].toString().toInt())

        g.color = hex2Rgb(prop["date_font_color"].toString())
        g.font = getFont("B").deriveFont(prop["date_font_size"].toString().toFloat())
        g.drawString(date,
            prop["date_font_x"].toString().toInt(),
            prop["date_font_y"].toString().toInt())

        g.dispose()
        out.mkdirs()
        ImageIO.write(image, "png", out)
    }
    
}

object AddTextToImgByTimeline {
    fun execute(file: File, title: String, meal: String, kcal: String?, out: File, date: String) {
        val image = ImageIO.read(file)
        val g2d = image.graphics
        val g = g2d as Graphics2D
        val prop = Properties()
        prop.load(File("assets/config/timeline.properties").bufferedReader())

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        g.color = hex2Rgb(prop["title_font_color"].toString())
        g.font = getFont("J").deriveFont(prop["title_font_size"].toString().toFloat())
        title.split("\n").forEachIndexed { ind, str ->
            val metrics = g.fontMetrics
            val textWidth = metrics.stringWidth(str)

            val x = when (prop["title_font_align"].toString()) {
                "center" -> (image.width - textWidth) / 2
                "right" -> image.width - textWidth - prop["title_font_x"].toString().toInt()
                else -> prop["title_font_x"].toString().toInt()
            }
            val y = prop["title_font_y"].toString().toInt()

            g.drawString(str, x, y)
        }

        g.color = hex2Rgb(prop["meal_font_color"].toString())
        g.font = getFont("R").deriveFont(prop["meal_font_size"].toString().toFloat())
        meal.split("\n").forEachIndexed { ind, str ->
            val metrics = g.fontMetrics
            val textWidth = metrics.stringWidth(str)

            val x = when (prop["meal_font_align"].toString()) {
                "center" -> (image.width - textWidth) / 2
                "right" -> image.width - textWidth - prop["meal_font_x"].toString().toInt()
                else -> prop["meal_font_x"].toString().toInt()
            }
            val y = prop["meal_font_y"].toString().toInt() + ind * prop["meal_font_leading"].toString().toInt()

            g.drawString(str, x, y)
        }

        g.color = hex2Rgb(prop["school_font_color"].toString())
        g.font = getFont("R").deriveFont(prop["school_font_size"].toString().toFloat())
        System.getenv("SCHOOL_NAME").split("\n").forEachIndexed { ind, str ->
            val metrics = g.fontMetrics
            val textWidth = metrics.stringWidth(str)

            val x = when (prop["school_font_align"].toString()) {
                "center" -> (image.width - textWidth) / 2
                "right" -> image.width - textWidth - prop["school_font_x"].toString().toInt()
                else -> prop["school_font_x"].toString().toInt()
            }
            val y = prop["school_font_y"].toString().toInt()

            g.drawString(str, x, y)
        }

       if(!kcal.isNullOrEmpty()) {
           g.color = hex2Rgb(prop["kcal_font_color"].toString())
           g.font = getFont("B").deriveFont(prop["kcal_font_size"].toString().toFloat())
           g.drawString(kcal?.substring(0,3),
               prop["kcal_font_x"].toString().toInt(),
               prop["kcal_font_y"].toString().toInt())
       }

        g.color = hex2Rgb(prop["date_font_color"].toString())
        g.font = getFont("R").deriveFont(prop["date_font_size"].toString().toFloat())
        g.drawString(date,
            prop["date_font_x"].toString().toInt(),
            prop["date_font_y"].toString().toInt())

        g.dispose()
        out.mkdirs()
        ImageIO.write(image, "png", out)
    }

}