package com.example.myapplication1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.io.PrintStream

class MainActivity2 : AppCompatActivity() {
    private val WORDS_FILE_NAME = "extraWords.txt"
    private var name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        name = intent.getStringExtra("name").toString()
        Toast.makeText(this, "Game Played By $name", Toast.LENGTH_SHORT).show()
    }

    fun addTheWord(view: View){
        val word = findViewById<TextView>(R.id.word_to_add).text.toString()
        val def = findViewById<TextView>(R.id.def_to_add).text.toString()

        val line = "$word\t$def"

        val outStream = PrintStream(openFileOutput(WORDS_FILE_NAME, MODE_PRIVATE))
        outStream.println(line)
        outStream.close()

        // Go back to main activity
        val myIntent = Intent()
        myIntent.putExtra("word",word)
        myIntent.putExtra("def",def)
        setResult(RESULT_OK,myIntent)
        finish()
        Toast.makeText(this, "Word has been added!", Toast.LENGTH_SHORT).show()

    }
}