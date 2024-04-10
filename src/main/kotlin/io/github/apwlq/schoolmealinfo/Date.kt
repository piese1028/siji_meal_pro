package io.github.apwlq.schoolmealinfo

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketTimeoutException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Date private constructor(private val data: ByteArray) {
    companion object {
        private const val NTP_PACKET_SIZE = 48
        fun getPacket(ntpServer: String): Date {
            return try {
                DatagramSocket().use { socket ->
                    socket.soTimeout = 10000
                    val address = InetAddress.getByName(ntpServer)
                    val msg = ByteArray(NTP_PACKET_SIZE)
                    msg[0] = 0x1B
                    val ntpData = DatagramPacket(msg, msg.size, address, 123)

                    socket.send(ntpData)
                    socket.receive(ntpData)

                    Date(msg)
                }
            } catch (e: SocketTimeoutException) {
                handleException(e)
            }
        }

        private fun handleException(e: Exception): Date {
            println(e)
            return Date(byteArrayOf())
        }
    }

    val transmitTimestamp: Timestamp
        get() = Timestamp(getLong(40), getLong(44))

    private fun getLong(offset: Int): Long {
        var result: Long = 0
        for (i in offset until offset + 4) {
            result = result shl 8 or (data[i].toLong() and 0xFF)
        }
        return result
    }
}

data class Timestamp(val seconds: Long, val nanos: Long)

fun getNowDate(setFormatter: String = "yyyyMMdd"): String {
    val localSeoulTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(setFormatter))
    try {
        val seoulZoneId = ZoneId.of("Asia/Seoul")
        val ntpServerAddress = "time.cloudflare.com"
        val ntpPacket = Date.getPacket(ntpServerAddress)
        val serverInstant = Instant.ofEpochSecond(ntpPacket.transmitTimestamp.seconds, ntpPacket.transmitTimestamp.nanos)
        val serverTime = LocalDateTime.ofInstant(serverInstant, ZoneOffset.UTC)
        val seoulTime = serverTime.atZone(ZoneOffset.UTC).withZoneSameInstant(seoulZoneId)
        val time = (seoulTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"))).toInt() - 700000

        return convertDateFormat(time.toString(), setFormatter)
    } catch (e: Exception) {
        System.err.println(e.message)
        return localSeoulTime
    }
}

fun convertDateFormat(dateStr: String, pattern: String): String {
    val originalFormat = SimpleDateFormat("yyyyMMdd")
    val targetFormat = SimpleDateFormat(pattern)
    val date = originalFormat.parse(dateStr)
    return targetFormat.format(date)
}
