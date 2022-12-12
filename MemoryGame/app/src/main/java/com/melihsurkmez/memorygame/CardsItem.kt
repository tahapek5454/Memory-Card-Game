package com.melihsurkmez.memorygame

import com.google.gson.annotations.SerializedName

data class CardsItem(
    val cardId: Int,
    val cardName: String,
    val cardScore: Int,
    val homeName: String,
    val homeScore: Int,
    val image: String
)