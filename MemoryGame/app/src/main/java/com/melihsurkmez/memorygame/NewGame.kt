package com.melihsurkmez.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_new_game.*
import kotlinx.android.synthetic.main.activity_new_game.imageButton
import kotlinx.android.synthetic.main.activity_new_game.imageButton10
import kotlinx.android.synthetic.main.activity_new_game.imageButton11
import kotlinx.android.synthetic.main.activity_new_game.imageButton12
import kotlinx.android.synthetic.main.activity_new_game.imageButton13
import kotlinx.android.synthetic.main.activity_new_game.imageButton14
import kotlinx.android.synthetic.main.activity_new_game.imageButton15
import kotlinx.android.synthetic.main.activity_new_game.imageButton16
import kotlinx.android.synthetic.main.activity_new_game.imageButton2
import kotlinx.android.synthetic.main.activity_new_game.imageButton3
import kotlinx.android.synthetic.main.activity_new_game.imageButton4
import kotlinx.android.synthetic.main.activity_new_game.imageButton5
import kotlinx.android.synthetic.main.activity_new_game.imageButton6
import kotlinx.android.synthetic.main.activity_new_game.imageButton7
import kotlinx.android.synthetic.main.activity_new_game.imageButton8
import kotlinx.android.synthetic.main.activity_new_game.imageButton9
import kotlinx.android.synthetic.main.activity_new_game.sayac

class NewGame : AppCompatActivity() {



    val buttons= ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)



        buttons.add("Selam")

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