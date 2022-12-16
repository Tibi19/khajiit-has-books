package com.tam.tesbooks.util

private const val CONFLICTING_CHAR = '&'
private const val MASKING_CHAR = '|'

fun String.toRouteArg(): String =
    replace(CONFLICTING_CHAR, MASKING_CHAR)

fun String.toNormalArg(): String =
    replace(MASKING_CHAR, CONFLICTING_CHAR)