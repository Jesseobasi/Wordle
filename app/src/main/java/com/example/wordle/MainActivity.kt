package com.example.wordle

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var wordToGuess: String
    private var guessCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Pick a random word at start
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()

        // Views
        val wordText = findViewById<TextView>(R.id.main_word)
        val guessInput = findViewById<EditText>(R.id.guess_edit_text)
        val guessButton = findViewById<Button>(R.id.guess_button)

        // For results (you can make IDs in XML for each if needed)
        val guess1 = findViewById<TextView>(R.id.guess1)       // <-- give IDs in XML
        val guess1Check = findViewById<TextView>(R.id.guess1Check)
        val guess2 = findViewById<TextView>(R.id.guess2)
        val guess2Check = findViewById<TextView>(R.id.guess2Check)
        val guess3 = findViewById<TextView>(R.id.guess3)
        val guess3Check = findViewById<TextView>(R.id.guess3Check)

        wordText.text = "_ _ _ _"

        guessButton.setOnClickListener {
            val guess = guessInput.text.toString().uppercase()
            if (guess.length != 4) {
                guessInput.error = "Enter a 4-letter word"
                return@setOnClickListener
            }

            val result = checkGuess(guess, wordToGuess)
            guessCount++

            when (guessCount) {
                1 -> {
                    guess1.text = guess
                    guess1Check.text = result
                }
                2 -> {
                    guess2.text = guess
                    guess2Check.text = result
                }
                3 -> {
                    guess3.text = guess
                    guess3Check.text = result
                }
            }

            if (guess == wordToGuess) {
                wordText.text = "You win! Word was $wordToGuess"
                guessButton.isEnabled = false
            } else if (guessCount >= 3) {
                wordText.text = "You lose! Word was $wordToGuess"
                guessButton.isEnabled = false
                Toast.makeText(it.context, "No more guesses", Toast.LENGTH_SHORT).show()
            }

            guessInput.text.clear()
        }
    }

    object FourLetterWordList {
        private val fourLetterWords =
            "AREA,ARMY,BABY,BACK,BALL,BAND,BANK,BASE,BILL,BODY,BOOK,CALL,CARD,CARE,CASE,CASH,CITY,CLUB,COST,DATE,DEAL,DOOR,DUTY,EAST,EDGE,FACE,FACT,FARM,FEAR,FILE,FILM,FIRE,FIRM,FISH,FOOD,FOOT,FORM,FUND,GAME,GIRL,GOAL,GOLD,HAIR,HALF,HALL,HAND,HEAD,HELP,HILL,HOME,HOPE,HOUR,IDEA,JACK,JOHN,KIND,KING,LACK,LADY,LAND,LIFE,LINE,LIST,LOOK,LORD,LOSS,LOVE,MARK,MARY,MIND,MISS,MOVE,NAME,NEED,NEWS,NOTE,PAGE,PAIN,PAIR,PARK,PART,PAST,PATH,PAUL,PLAN,PLAY,POST,RACE,RAIN,RATE,REST,RISE,RISK,ROAD,ROCK,ROLE,ROOM,RULE,SALE,SEAT,SHOP,SHOW,SIDE,SIGN,SITE,SIZE,SKIN,SORT,STAR,STEP,TASK,TEAM,TERM,TEST,TEXT,TIME,TOUR,TOWN,TREE,TURN,TYPE,UNIT,USER,VIEW,WALL,WEEK,WEST,WIFE,WILL,WIND,WINE,WOOD,WORD,WORK,YEAR"

        fun getAllFourLetterWords(): List<String> {
            return fourLetterWords.split(",")
        }

        fun getRandomFourLetterWord(): String {
            val allWords = getAllFourLetterWords()
            val randomNumber = (0 until allWords.size).random()
            return allWords[randomNumber].uppercase()
        }
    }

    private fun checkGuess(guess: String, wordToGuess: String): String {
        var result = ""
        for (i in 0..3) {
            result += when {
                guess[i] == wordToGuess[i] -> "O"
                guess[i] in wordToGuess -> "+"
                else -> "X"
            }
        }
        return result
    }
}
