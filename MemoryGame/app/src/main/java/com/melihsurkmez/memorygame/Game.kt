package com.melihsurkmez.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.Toast
import com.melihsurkmez.memorygame.databinding.ActivityGameBinding
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding
    //Butonlarimizin listesi burada olucak
    var buttons = ArrayList<ImageButton>()
    lateinit var cards : List<Card>
    var indexOfSingleSelectionCard : Int? =null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttons.add(imageButton)
        buttons.add(imageButton2)
        buttons.add(imageButton3)
        buttons.add(imageButton4)
        buttons.add(imageButton5)
        buttons.add(imageButton6)
        buttons.add(imageButton7)
        buttons.add(imageButton8)
        buttons.add(imageButton9)
        buttons.add(imageButton10)
        buttons.add(imageButton11)
        buttons.add(imageButton12)
        buttons.add(imageButton13)
        buttons.add(imageButton14)
        buttons.add(imageButton15)
        buttons.add(imageButton16)

        var images = mutableListOf(R.drawable.ic_baseline_password_24,
            R.drawable.ic_baseline_lock_24, R.drawable.ic_baseline_email_24,
            R.drawable.ic_baseline_person_24)
        images.addAll(images)

        images.addAll(images)


        println(images.size)

        images.shuffle()


        cards = buttons.indices.map { index ->
            Card(images[index])
        }


        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {

               updateModel(index)

                updateViews()


            }
        }

        myTimer()


    }

    private fun updateViews() {
        println("----------------------------------")
        cards.forEachIndexed{index, card->
            var button = buttons[index]

            if(card.isFaceUp){

                println("Gozukmeli")
                button.setImageResource(card.identifier)
            }else{
                button.setImageResource(R.drawable.ic_launcher_foreground)
            }
        }
    }

    private fun updateModel(index: Int) {
        var card = cards[index]

        if(card.isFaceUp){
            Toast.makeText(this, "Bu kart zaten secili", Toast.LENGTH_SHORT).show()
            return
        }

        if(indexOfSingleSelectionCard == null){
            restoreCards()
            indexOfSingleSelectionCard = index
            card.isFaceUp = !card.isFaceUp
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

        if(cards[indexOfSingleSelectionCard].identifier == cards[index].identifier){

            Toast.makeText(this, "Eslesme Basarili", Toast.LENGTH_SHORT).show()


            cards[indexOfSingleSelectionCard].isMatched = true
            cards[index].isMatched = true
            cards[index].isFaceUp = !cards[index].isFaceUp

        }else{

            buttons[index].setImageResource(cards[index].identifier)
            Thread.sleep(1000)
            restoreCards()

        }
    }


    //Timer
    private fun myTimer(){
        object : CountDownTimer(45000,1000){
            override fun onTick(p0: Long) {
                binding.sayac.text = "${p0/1000}"
            }

            override fun onFinish() {
                binding.sayac.text = "0"
            }

        }.start()
    }
}