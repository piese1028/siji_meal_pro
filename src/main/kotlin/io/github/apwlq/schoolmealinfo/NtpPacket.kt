package io.github.apwlq.schoolmealinfo

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

// NTP 패킷 클래스
class NtpPacket private constructor(private val data: ByteArray) {
    companion object {
        // NTP 패킷을 생성하는 함수
        fun getPacket(ntpServer: String): NtpPacket {
            val address = InetAddress.getByName(ntpServer)
            val msg = ByteArray(48)
            msg[0] = 0x1B
            val ntpData = DatagramPacket(msg, msg.size, address, 123)
            val socket = DatagramSocket()
            socket.soTimeout = 10000
            socket.send(ntpData)
            socket.receive(ntpData)
            return NtpPacket(msg)
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

// NTP 타임스탬프 클래스
data class Timestamp(val seconds: Long, val nanos: Long)