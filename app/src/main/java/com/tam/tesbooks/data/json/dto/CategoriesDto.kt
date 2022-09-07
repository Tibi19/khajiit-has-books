package com.tam.tesbooks.data.json.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CategoriesDto: ArrayList<String>()