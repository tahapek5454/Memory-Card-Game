package com.melihsurkmez.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        myTimer()
    }


    //Timer
    private fun myTimer(){
        object : CountDownTimer(90000,1000){
            override fun onTick(p0: Long) {
                sayac.text = "${p0/1000}"
            }

            override fun onFinish() {
                sayac.text = "0"
            }

        }.start()
    }
}