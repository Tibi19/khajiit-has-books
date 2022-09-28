package com.tam.tesbooks.util

import com.tam.tesbooks.domain.model.listing_modifier.Order

fun getOrderText(order: Order): String =
    when(order) {
        Order.CATEGORY -> TEXT_CATEGORY
        Order.TITLE -> TEXT_TITLE
        Order.SIZE -> TEXT_SIZE
        Order.AUTHOR_NAME -> TEXT_AUTHOR
    }