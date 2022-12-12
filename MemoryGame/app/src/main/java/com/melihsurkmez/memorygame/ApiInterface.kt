package com.melihsurkmez.memorygame

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("getCards")
    fun getData(): Call<List<CardsItem>>

}