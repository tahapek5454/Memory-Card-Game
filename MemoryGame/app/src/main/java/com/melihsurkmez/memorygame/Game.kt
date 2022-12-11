package com.melihsurkmez.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {
    //Butonlarimizin listesi burada olucak
    private lateinit var buttons: List<ImageButton>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)



        buttons = listOf(imageButton, imageButton2, imageButton3, imageButton4, imageButton5,
            imageButton6, imageButton7, imageButton8, imageButton9, imageButton10, imageButton11, imageButton12,
                    imageButton13, imageButton14, imageButton15, imageButton16)

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {

                button.setImageResource(R.drawable.ic_baseline_email_24)
            }
        }

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