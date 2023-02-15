package com.tam.tesbooks.util

import java.time.LocalDateTime
import java.time.temporal.TemporalAmount

fun nowPlusOneSecond(): LocalDateTime {
    return LocalDateTime.now().plusSeconds(1)
}