package com.melihsurkmez.memorygame

data class Card
    (var identifier: Int,var name: String="", var isFaceUp: Boolean=false, var isMatched: Boolean=false, var score:Int=10, var home:Int=1,var home_name: String="", var image:Int=0)
