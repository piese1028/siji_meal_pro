package io.github.apwlq.schoolmealinfo

import com.leeseojune.neisapi.NeisApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.Instant
import java.time.ZoneOffset

fun getLunch(): String {
    val neis = NeisApi.Builder().build()
    val schoolName = "부산동고"
//    val schoolName = System.getenv("SCHOOL_NAME")
    val sch = neis.getSchoolByName(schoolName).first()
    val meal = neis.getMealsByAbsoluteDay(getNowDate(), sch.scCode, sch.schoolCode)
    return meal.lunch.joinToString("\n")
}
fun getDinner(): String {
    val neis = NeisApi.Builder().build()
    val schoolName = "부산동고"
//    val schoolName = System.getenv("SCHOOL_NAME")
    val sch = neis.getSchoolByName(schoolName).first()
    val meal = neis.getMealsByAbsoluteDay(getNowDate(), sch.scCode, sch.schoolCode)
    return meal.dinner.joinToString("\n")
}


fun getNowDate(): String {
    val seoulZoneId = ZoneId.of("Asia/Seoul")
    val formatter = DateTimeFormatter.ofPattern("YYYYMMdd")

    // Google의 NTP 서버 주소
    val ntpServerAddress = "time.google.com"

    // NTP 패킷을 보내어 서버 시간을 가져옴
    val ntpPacket = NtpPacket.getPacket(ntpServerAddress)

    // 서버 시간을 LocalDateTime으로 변환
    val serverInstant = Instant.ofEpochSecond(ntpPacket.transmitTimestamp.seconds, ntpPacket.transmitTimestamp.nanos)
    val serverTime = LocalDateTime.ofInstant(serverInstant, ZoneOffset.UTC)

    // 서버 시간을 Asia/Seoul 시간대로 변환
    val seoulTime = serverTime.atZone(ZoneOffset.UTC).withZoneSameInstant(seoulZoneId)

    // 변환된 시간을 지정된 포맷에 맞게 문자열로 반환
    return (seoulTime.format(formatter).toInt()-700000).toString()
}