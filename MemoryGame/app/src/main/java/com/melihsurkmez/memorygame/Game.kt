package com.melihsurkmez.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class Game : AppCompatActivity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    private var flag: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val images = mutableListOf(
            R.drawable.ic_heart,
            R.drawable.ic_light,
            R.drawable.ic_plane,
            R.drawable.ic_smile
        )
        // Add each image twice so we can create pairs
        images.addAll(images)
        // Randomize the order of images
        images.shuffle()

        buttons = listOf(imageButton1, imageButton2, imageButton3, imageButton4, imageButton5,
            imageButton6, imageButton7, imageButton8)

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {

                // Update models
                updateModels(index)
                // Update the UI for the game
                updateViews()



            }
        }

    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = 0.1f
            }

            if(card.isFaceUp){
                println("Buton icin ressim ->>"+index)
                button.setImageResource(card.identifier)
            }else{
                println("Button icin default ->>"+index)
                button.setImageResource(R.drawable.ic_code)

            }
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]
        // Error checking:
        if (card.isFaceUp) {
            Toast.makeText(this, "Kart Zaten Acik!", Toast.LENGTH_SHORT).show()
            return
        }
        // Three cases
        // 0 cards previously flipped over => restore cards + flip over the selected card
        // 1 card previously flipped over => flip over the selected card + check if the images match
        // 2 cards previously flipped over => restore cards + flip over the selected card
        if (indexOfSingleSelectedCard == null) {
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {



            // exactly 1 card was selected previously
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }

        card.isFaceUp = !card.isFaceUp

    }

    private fun restoreCards() {
        println("---------------------------------------------")


        cards.forEachIndexed{index, card->
            if(!card.isMatched){
                println("Eslesmemis kart ->"+index)
                card.isFaceUp = false
            }else{
                println("Eslesen Kat ->>"+index)
            }
        }
    }

    private fun isDone(){
        cards.forEach { card->
            if(!card.isMatched){
                return
            }
        }
        Toast.makeText(this, "Oyun Bitti!!", Toast.LENGTH_SHORT).show()


    }

    private fun checkForMatch(position1: Int, position2: Int) {
        println("---------------------------------------------")

        if (cards[position1].identifier == cards[position2].identifier) {
            Toast.makeText(this, "Eslestirme Bulundu!!", Toast.LENGTH_SHORT).show()
            cards[position1].isMatched = true
            cards[position2].isMatched = true
            isDone()
        }
    }
}