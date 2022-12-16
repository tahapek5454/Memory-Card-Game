package com.melihsurkmez.memorygame

data class Card
    (var name: String="", var isFaceUp: Boolean=false, var isMatched: Boolean=false, var score:Int=10, var home:Int=1, var image:Int=0, var homeName:String = "") : java.io.Serializable

