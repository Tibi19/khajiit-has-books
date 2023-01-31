package com.tam.tesbooks.data.room.converter

import androidx.room.TypeConverter
import java.util.*

class UuidConverter {

    @TypeConverter
    fun uuidToString(uuid: UUID): String = uuid.toString()

    @TypeConverter
    fun stringToUuid(uuidString: String): UUID = UUID.fromString(uuidString)

}