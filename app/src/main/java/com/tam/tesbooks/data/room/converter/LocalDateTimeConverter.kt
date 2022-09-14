package com.tam.tesbooks.data.room.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDateTimeConverter {

    @TypeConverter
    fun secondsToDate(value: Long): LocalDateTime =
        Instant.ofEpochSecond(value)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

    @TypeConverter
    fun dateToSeconds(date: LocalDateTime): Long =
        date.atZone(ZoneId.systemDefault())
            .toEpochSecond()

}