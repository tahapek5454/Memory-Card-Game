package com.melihsurkmez.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import com.melihsurkmez.memorygame.databinding.ActivitySplashScreenBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    var cards2 = ArrayList<Card>()
    lateinit var binding:ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation1 = AnimationUtils.loadAnimation(this,R.anim.animation1)
        val animation2 = AnimationUtils.loadAnimation(this,R.anim.animation2)
        val animation3 = AnimationUtils.loadAnimation(this,R.anim.animation3)

        val imageView = binding.imageView2
        val text = binding.textView

        imageView.animation = animation2
        text.animation = animation3

        object : CountDownTimer(8000, 8000) {
            override fun onTick(millisUntilFinished: Long) {

                get_data()

            }
            override fun onFinish() {

                intent= Intent(applicationContext,GameForSix2::class.java)
                intent.putExtra("cards",cards2)
                startActivity(intent)

                }

        }.start()



    }

    fun get_data() {

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.1.104:5001")
            .build()
            .create(ApiInterface::class.java)


        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<CardsItem>?> {
            override fun onResponse(
                call: Call<List<CardsItem>?>,
                response: retrofit2.Response<List<CardsItem>?>
            ) {
                val responseBody = response.body()!!
                val myStringBuilder = StringBuilder()
                for(Cards in responseBody){
                    val card_id: Int = Cards.cardId
                    val card_name: String = Cards.cardName
                    val card_home: String = Cards.homeName
                    val card_score: Int = Cards.cardScore
                    val home_score : Int = Cards.homeScore
                    var addedCard = Card(name=card_name, score=card_score, homeName = card_home, home = home_score)
                    // println(card_id.toString()+" "+ card_name+" "+card_home+" "+card_score.toString()+" "+home_score.toString())
                    cards2.add(addedCard)



                }

            }

            override fun onFailure(call: Call<List<CardsItem>?>, t: Throwable) {
                Log.e("Error",t.toString())
            }
        })




    }
}