package com.tam.tesbooks.util

import com.tam.tesbooks.domain.model.listing_modifier.Sort

fun getSortText(sort: Sort): String =
    when(sort) {
        Sort.DATE_ADDED -> TEXT_DATE_ADDED
        Sort.TITLE -> TEXT_TITLE
        Sort.SIZE -> TEXT_SIZE
    }