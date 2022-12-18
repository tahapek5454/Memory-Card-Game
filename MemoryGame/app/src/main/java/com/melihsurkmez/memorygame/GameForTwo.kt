package com.melihsurkmez.memorygame

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.melihsurkmez.memorygame.databinding.ActivityGameBinding
import com.melihsurkmez.memorygame.databinding.ActivityGameForTwoBinding
import kotlinx.android.synthetic.main.activity_game_for_six.*


import kotlinx.android.synthetic.main.activity_game_for_two.*
import kotlinx.android.synthetic.main.activity_game_for_two.puan
import kotlinx.android.synthetic.main.activity_game_for_two.sayac

import java.io.IOException


class GameForTwo : AppCompatActivity() {

    var buttons = ArrayList<ImageButton>()
    lateinit var timer : CountDownTimer
    var cards = ArrayList<Card>()
    var cards2 = ArrayList<Card>()
    var indexOfSingleSelectionCard : Int? =null
    lateinit var binding: ActivityGameForTwoBinding
    var mMediaPlayer: MediaPlayer? = null
    var mCMediaPlayer: MediaPlayer? = null
    var mSMediaPlayer: MediaPlayer? = null
    var mVMediaPlayer: MediaPlayer? = null


    var homeName1Counter =0
    var homeName2Counter =0
    var homeName3Counter =0
    var homeName4Counter=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityGameForTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val arrayList = intent.getSerializableExtra("cards") as ArrayList<Card>
        cards2=arrayList
        println(arrayList.size)
        buttons.add(deneme1)
        buttons.add(deneme2)
        buttons.add(deneme3)
        buttons.add(deneme4)

        binding.sesAc.setOnClickListener {
            playSound()
        }

        binding.sesKapa.setOnClickListener {
            pauseSound()
        }

        var images = mutableListOf(R.drawable.bir,
            R.drawable.iki, R.drawable.uc,
            R.drawable.dort,R.drawable.bes,R.drawable.alti,R.drawable.yedi,R.drawable.sekiz
            ,R.drawable.dokuz,R.drawable.on,R.drawable.onbir,R.drawable.oniki,R.drawable.onuc
            ,R.drawable.ondort,R.drawable.onbes,R.drawable.onalti,R.drawable.onyedi,R.drawable.onsekiz
            ,R.drawable.ondokuz,R.drawable.yirmi,R.drawable.yirmibir,R.drawable.yirmiiki,R.drawable.yirmiuc
            ,R.drawable.yirmidort,R.drawable.yirmibes,R.drawable.yirmi6,R.drawable.yirmiyedi,R.drawable.yirmisekiz
            ,R.drawable.yirmidokuz,R.drawable.otuz,R.drawable.otuzbir,R.drawable.otuziki,R.drawable.otuzuc
            ,R.drawable.otuzdort,R.drawable.otuzbes,R.drawable.otuzalti,R.drawable.otuzyedi,R.drawable.otuzsekiz
            ,R.drawable.otuzdokuz,R.drawable.kirk,R.drawable.kirkbir,R.drawable.kirktiki,R.drawable.kirkuc
            ,R.drawable.kirkdort)


        // biz eklenen cards lara sırasıyla image atıcaz

        // image isinlerini boyle sırasıyla atsak


        cards2.forEachIndexed{index, card2 ->
            // println("Eklenen index "+index)
            card2.image = images[index]
        }
        // artik cards icersinde tum cardlar var

        // simdi sira karistirmakta
        cards2.shuffle()

        var i = 0
        while(cards.size < buttons.size){

            var temp = Card(name = cards2[i].name, score=cards2[i].score, home = cards2[i].home, image = cards2[i].image, homeName = cards2[i].homeName)
            cards.add(temp)
            var temp2 = Card(name = cards2[i].name, score=cards2[i].score, home = cards2[i].home, image = cards2[i].image, homeName = cards2[i].homeName)

            cards.add(temp2)



            i+=1

        }

        // for(i in 0..(buttons.size/2)-1){
        // var temp = Card(name = cards2[i].name, score=cards2[i].score, home = cards2[i].home, image = cards2[i].image, homeName = cards2[i].homeName)
        // cards.add(temp)
        // }

        //for(i in 0..(buttons.size/2)-1){
        // var temp = Card(name = cards2[i].name, score=cards2[i].score, home = cards2[i].home, image = cards2[i].image, homeName = cards2[i].homeName)
        // cards.add(temp)
        // }


        cards.shuffle()



        // karistirdik da artik kartlar buttonlarla index ile iliskili davrnacak


        // sonra kartlari sufflela


        myTimer()
        playSound()

        // eksi kod
        //images.addAll(images)
        //images.addAll(images)
        //images.shuffle()


        //cards = images.indices.map { index ->
        //    Card(images[index])
        //}

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {

                updateModel(index)

                updateViews()


            }
        }
    }

    fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.prologue)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }
    fun pauseSound() {
        if (mMediaPlayer?.isPlaying == true) mMediaPlayer?.pause()
    }
    fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }
    // 4. Destroys the MediaPlayer instance when the app is closed
    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }
    fun CongratsplaySound() {
        if (mCMediaPlayer == null) {
            mCMediaPlayer = MediaPlayer.create(this, R.raw.congrats)
            mCMediaPlayer!!.isLooping = true
            mCMediaPlayer!!.start()
        } else mCMediaPlayer!!.start()
    }
    fun CongratspauseSound() {
        if (mCMediaPlayer?.isPlaying == true) mCMediaPlayer?.pause()
    }
    fun CongratstopSound() {
        if (mCMediaPlayer != null) {
            mCMediaPlayer!!.stop()
            mCMediaPlayer!!.release()
            mCMediaPlayer = null
        }
    }
    // 4. Destroys the MediaPlayer instance when the app is closed

    fun shockedplaySound() {
        if (mSMediaPlayer == null) {
            mSMediaPlayer = MediaPlayer.create(this, R.raw.shocked)
            mSMediaPlayer!!.isLooping = true
            mSMediaPlayer!!.start()
        } else mSMediaPlayer!!.start()
    }
    fun shockedpauseSound() {
        if (mSMediaPlayer?.isPlaying == true) mSMediaPlayer?.pause()
    }
    fun shockedstopSound() {
        if (mSMediaPlayer != null) {
            mSMediaPlayer!!.stop()
            mSMediaPlayer!!.release()
            mSMediaPlayer = null
        }
    }
    // 4. Destroys the MediaPlayer instance when the app is closed

    fun victoryplaySound() {
        if (mVMediaPlayer == null) {
            mVMediaPlayer = MediaPlayer.create(this, R.raw.victory)
            mVMediaPlayer!!.isLooping = true
            mVMediaPlayer!!.start()
        } else mVMediaPlayer!!.start()
    }
    fun victorypauseSound() {
        if (mVMediaPlayer?.isPlaying == true) mVMediaPlayer?.pause()
    }
    fun victorystopSound() {
        if (mVMediaPlayer != null) {
            mVMediaPlayer!!.stop()
            mVMediaPlayer!!.release()
            mVMediaPlayer = null
        }
    }
    private fun oyunBittiBasarili(){
        var builder: AlertDialog.Builder

        builder = AlertDialog.Builder(this)


        builder.setTitle("Bravo! Kazandın!")
            .setMessage("Puanın:"+puan.text)
            .setCancelable(true) // dialog box in cancellable
            // set positive button
            //take two parameters dialogInterface and an int
            .setPositiveButton("Ana Menüye Dön") { dialogInterface, it ->
                intent = Intent(applicationContext,Preferences::class.java)
                shockedstopSound()
                victorystopSound()
                CongratstopSound()
                stopSound()
                startActivity(intent)
                finish() // close the app when yes clicked
            }
            .setNegativeButton("Tekrar Oyna") { dialogInterface, it ->
                intent = Intent(applicationContext,SplashScreen::class.java)
                intent.putExtra("zorlukderece", "Kolay");
                intent.putExtra("kisisayisi", "Tek");
                shockedstopSound()
                victorystopSound()
                CongratstopSound()
                stopSound()
                startActivity(intent)
                dialogInterface.cancel()
            }
            // show the builder
            .show()

    }

    private fun oyunBittiBasarisiz() {
        var builder: AlertDialog.Builder

        builder = AlertDialog.Builder(this)


        builder.setTitle("Yetişemedin! Kaybettin :(")
            .setMessage("Puanın:"+puan.text)
            .setCancelable(true) // dialog box in cancellable
            // set positive button
            //take two parameters dialogInterface and an int
            .setPositiveButton("Ana Menüye Dön") { dialogInterface, it ->
                intent = Intent(applicationContext,Preferences::class.java)
                shockedstopSound()
                victorystopSound()
                CongratstopSound()
                stopSound()
                startActivity(intent)
                finish() // close the app when yes clicked
            }
            .setNegativeButton("Tekrar Oyna") { dialogInterface, it ->
                intent = Intent(applicationContext,SplashScreen::class.java)
                intent.putExtra("zorlukderece", "Kolay");
                intent.putExtra("kisisayisi", "Tek");
                shockedstopSound()
                victorystopSound()
                CongratstopSound()
                stopSound()
                startActivity(intent)
                dialogInterface.cancel()
            }
            // show the builder
            .show()
    }



    private fun calculateTrueResult(index :Int){

        var card = cards[index]
        var totalScore = puan.text.toString().toInt()
        var timeScore = sayac.text.toString().toInt()

        var calculate = (card.score*2*card.home)*(timeScore/10)
        calculate = calculate + totalScore


        puan.text = calculate.toString()

        // val text: String = binding.succesLogs.text.toString()

        val new_text: String ="Karakter"+":"+card.name+" Kazandırdığı Puan:"+calculate+" Evi:"+card.home



    }

    private fun calculateFalseResult(index1 :Int, index2 :Int){
        var card1 = cards[index1]
        var card2 = cards[index2]
        var totalScore = puan.text.toString().toInt()
        var timeScore = sayac.text.toString().toInt()

        var calculate = 0

        if(card1.home == card2.home){

            calculate = ((card1.score+card2.score)/card1.home)*(timeScore/10)
            calculate = totalScore-calculate

        }else{
            calculate = ((((card1.score+card2.score)/2)*card1.home*card2.home))*(timeScore/10)
            calculate = totalScore-calculate

        }

        if(calculate<0){
            calculate = 0
        }

        puan.text = calculate.toString()

    }

    private fun updateViews() {

        cards.forEachIndexed{index, card->
            var button = buttons[index]

            if(card.isFaceUp){



                button.setImageResource(card.image)
            }else{
                button.setImageResource(R.drawable.arka)
            }
        }
    }

    private fun updateModel(index: Int) {
        CongratstopSound()
        var card = cards[index]
        val new_text:String = card.name+"(Puan:"+card.score+""+",Ev:"+card.home+")"
        val textView = TextView(this)
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        textView.text = new_text
        binding.scroll.addView(textView)
        //println("Tiklanan Kart'in adi "+card.name+ "Tiklanan kartin indexi "+index)
        //println("Cards[index] lengt "+cards.size)


        //println("Tiklanan Kart'in adi "+card.name+ "Tiklanan kartin indexi "+index)


        if(card.isFaceUp){
            Toast.makeText(this, "Bu kart zaten secili", Toast.LENGTH_SHORT).show()
            return
        }

        if(indexOfSingleSelectionCard == null){
            restoreCards()
            indexOfSingleSelectionCard = index
            card.isFaceUp = !card.isFaceUp
            //println("FaceUpladim")
            // println(card)
            //cards.forEachIndexed{index, card ->
            // if(card.isFaceUp){
            //   println("FaceUp olan kartin indexi-> "+index+ " Adi ->"+card.name)
            //  println(card)
            // }
            // }

        }else{
            checkForMatch(indexOfSingleSelectionCard!!, index)
            indexOfSingleSelectionCard = null
        }


    }

    private fun restoreCards(){

        for (card in cards){
            if(!card.isMatched){
                card.isFaceUp = false

            }
        }
    }

    private fun checkForMatch(indexOfSingleSelectionCard: Int, index: Int) {

        if(cards[indexOfSingleSelectionCard].image == cards[index].image){

            Toast.makeText(this, "Eslesme Basarili", Toast.LENGTH_SHORT).show()


            cards[indexOfSingleSelectionCard].isMatched = true
            cards[index].isMatched = true
            cards[index].isFaceUp = !cards[index].isFaceUp
            updateViews()
            calculateTrueResult(index)
            CongratsplaySound()
            var bittimi = 1
            for(card in cards){

                if(!card.isMatched){

                    bittimi = 0
                    break

                }
            }

            if(bittimi==1){
                stopSound()
                victoryplaySound()
                CongratstopSound()
                timer.cancel()
                oyunBittiBasarili()

            }

        }else{

            object : CountDownTimer(1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    for (button in buttons) {    // Başta Kartlar Ters Gözüksün Diye
                        button.isEnabled = false
                    }
                    calculateFalseResult(indexOfSingleSelectionCard, index)
                    buttons[index].setImageResource(cards[index].image)
                    buttons[indexOfSingleSelectionCard].setImageResource(cards[indexOfSingleSelectionCard].image)

                }

                override fun onFinish() {
                    for (button in buttons) {    // Başta Kartlar Ters Gözüksün Diye
                        button.isEnabled = true
                    }
                    restoreCards()
                    updateViews()

                }
            }.start()



        }
    }

    //Timer
    private fun myTimer(){
        timer = object : CountDownTimer(45000,1000){
            override fun onTick(p0: Long) {
                sayac.text = "${p0/1000}"

            }

            override fun onFinish() {
                binding.sayac.text = "0"
                victorystopSound()
                CongratstopSound()
                stopSound()
                shockedplaySound()
                oyunBittiBasarisiz()
            }

        }

        timer.start()

    }





}
