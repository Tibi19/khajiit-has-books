package com.tam.tesbooks.data.room.converter

import androidx.room.TypeConverter

const val SEPARATOR_JOIN_TAGS = "#"

class TagsConverter {

    @TypeConverter
    fun tagsToString(tags: List<String>): String =
        tags.joinToString(SEPARATOR_JOIN_TAGS)

    @TypeConverter
    fun stringToTags(tagsString: String): List<String> =
        tagsString.split(SEPARATOR_JOIN_TAGS)

}