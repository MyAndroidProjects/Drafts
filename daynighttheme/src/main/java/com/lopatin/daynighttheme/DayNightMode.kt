package com.lopatin.daynighttheme

enum class DayNightMode {
    DAY,
    NIGHT,
    UNDEFINED;

    fun getDayNightModeByOrder(ordinal: Int): DayNightMode {
        return when (ordinal) {
            DAY.ordinal -> DAY
            NIGHT.ordinal -> NIGHT
            UNDEFINED.ordinal -> UNDEFINED
            else -> UNDEFINED
        }
    }
}