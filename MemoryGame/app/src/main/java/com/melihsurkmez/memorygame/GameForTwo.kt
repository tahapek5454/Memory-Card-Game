package com.melihsurkmez.memorygame

import android.content.DialogInterface
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


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
    var mediaPlayer : MediaPlayer?=null
    var mpForNope : MediaPlayer?=null
    var mpForEndFlag = true
    var mpForEnd : MediaPlayer?=null

    var homeName1Counter =0
    var homeName2Counter =0
    var homeName3Counter =0
    var homeName4Counter=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_for_two)



        val arrayList = intent.getSerializableExtra("cards") as ArrayList<Card>
        cards2=arrayList
        println(arrayList.size)
        buttons.add(deneme1)
        buttons.add(deneme2)
        buttons.add(deneme3)
        buttons.add(deneme4)



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
        playAudio()






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

    private fun oyunBittiBasarili(){
        val uyari = AlertDialog.Builder(this)
        uyari.setTitle("Tebrikler Oyunu Kazandiriniz")
        uyari.setMessage("Puaninizi : "+puan.text)
        uyari.setMessage("Butona Tiklayarak Ana Menuye Gidebilirsiniz")
        uyari.setPositiveButton("Git",DialogInterface.OnClickListener { dialogInterface, i ->
            Toast.makeText(this, "Ana Menuye yonledirme eklenecek", Toast.LENGTH_LONG).show()
        })
        uyari.show()

    }

    private fun oyunBittiBasarisiz(){
        val uyari = AlertDialog.Builder(this)
        uyari.setTitle("Maalesef Oyunu Kazanamadiniz")
        uyari.setMessage("Puaninizi : "+puan.text)
        uyari.setMessage("Butona Tiklayarak Ana Menuye Gidebilirsiniz")
        uyari.setNegativeButton("Git",DialogInterface.OnClickListener { dialogInterface, i ->
            Toast.makeText(this, "Ana Menuye yonledirme eklenecek", Toast.LENGTH_LONG).show()
        })

        uyari.show()

    }

    private fun playAudioForNope(){

        mpForNope = MediaPlayer()
        mpForNope!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            mpForNope!!.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.nope))
            mpForNope!!.prepare()
            mpForNope!!.start()

        }catch (e: IOException){
            println("Hata")
        }

    }

    private fun stopAudioForNope(){
        if(mpForNope!!.isPlaying){
            mpForNope!!.stop()
            mpForNope!!.reset()
            mpForNope!!.release()
        }
    }

    private fun playAudioForEnd(){

        mpForEnd = MediaPlayer()
        mpForEnd!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            mpForEnd!!.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.yeter))
            mpForEnd!!.prepare()
            mpForEnd!!.start()

        }catch (e: IOException){
            println("Hata")
        }
    }

    private fun stopAudioForEnd(){
        if(mpForEnd!!.isPlaying){
            mpForEnd!!.stop()
            mpForEnd!!.reset()
            mpForEnd!!.release()
        }
    }


    private fun playAudio(){

        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            mediaPlayer!!.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.vodka))
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()

        }catch (e: IOException){
            println("Hata")
        }
    }

    private fun stopAudio(){
        if(mediaPlayer!!.isPlaying){
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
            mediaPlayer!!.release()
        }
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
        var card = cards[index]
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

            var bittimi = 1
            for(card in cards){

                if(!card.isMatched){

                    bittimi = 0
                    break

                }
            }

            if(bittimi==1){
                println("Buraya Girdim")
                stopAudio()
                if(!mpForEndFlag){
                    stopAudioForEnd()
                }
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
                    playAudioForNope()
                }

                override fun onFinish() {
                    for (button in buttons) {    // Başta Kartlar Ters Gözüksün Diye
                        button.isEnabled = true
                    }
                    restoreCards()
                    updateViews()
                    stopAudioForNope()
                }
            }.start()



        }
    }

    //Timer
    private fun myTimer(){
        timer = object : CountDownTimer(45000,1000){
            override fun onTick(p0: Long) {
                sayac.text = "${p0/1000}"

                if((p0/1000)<10 && mpForEndFlag){
                    playAudioForEnd()
                    mpForEndFlag = false
                }

            }

            override fun onFinish() {
                sayac.text = "0"
                stopAudioForEnd()
                stopAudio()
                oyunBittiBasarisiz()
            }

        }

        timer.start()

    }





}
