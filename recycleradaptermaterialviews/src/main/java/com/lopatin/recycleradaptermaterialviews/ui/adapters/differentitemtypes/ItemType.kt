package com.lopatin.recycleradaptermaterialviews.ui.adapters.differentitemtypes

enum class ItemType {
    SHORT,
    INFO,
    BUTTON,
    IMAGE,
    LIST;

    companion object {
        fun getItemTypeByOrdinal(ordinal: Int): ItemType {
            return when (ordinal) {
                INFO.ordinal -> INFO
                BUTTON.ordinal -> BUTTON
                IMAGE.ordinal -> IMAGE
                LIST.ordinal -> LIST
                else -> SHORT
            }
        }
    }
}