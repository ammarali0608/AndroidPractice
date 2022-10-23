package com.example.myapplication1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.util.Scanner


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val REQ_CODE = 681
    private val wordToDefn = HashMap<String,String>()
    private val dfns = ArrayList<String>()
    private val words = ArrayList<String>()
    private lateinit var adapter1 : ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val reader = Scanner(resources.openRawResource(R.raw.grewords))
        readDictFile(reader)

        try{
            val reader1 = Scanner(openFileInput("extraWords.txt"))
            readDictFile(reader1)
            Toast.makeText(this, "Exists", Toast.LENGTH_SHORT).show()

        }
        catch(e:Exception){
            Toast.makeText(this, "Not Exist", Toast.LENGTH_SHORT).show()

        }



        listMaker()
        findViewById<ListView>(R.id.lis1).setOnItemClickListener{ _,_,index,_ ->

            val word = findViewById<TextView>(R.id.txt2).text.toString()
            val defCor = wordToDefn[word]
            val defChosen = dfns[index]
            if(defCor == defChosen){
                Toast.makeText(this, "You got it!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Sorry! Incorrect", Toast.LENGTH_SHORT).show()
            }
            listMaker()
        }
    }

    private fun readDictFile(Reader:Scanner) {
        val reader = Reader
        while (reader.hasNext()){

            val line =  reader.nextLine()
            Log.d("ammar","The Line is: $line")
            val pieces = line.split("\t")
            if(pieces.size >= 2){
                words.add(pieces[0])
                wordToDefn.put(pieces[0],pieces[1])

            }

        }
    }

    private fun listMaker(){
        val txt2 = findViewById<TextView>(R.id.txt2)
        val txt1 = findViewById<TextView>(R.id.txt1)
        val lis = findViewById<ListView>(R.id.lis1)

        val rand  = java.util.Random()
        val index = rand.nextInt(words.size)
        val word = words[index]


        txt2.text = word

        dfns.clear()
        dfns.add(wordToDefn[word]!!)
        words.shuffle()
        for (otherword in words.subList(0,5)){
            if(otherword == word || dfns.size == 5){
                continue;
            }
                dfns.add(wordToDefn[otherword]!!)
        }
        dfns.shuffle()

        //List adpater
        adapter1 = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dfns)
        lis.adapter = adapter1

    }

    // Intents

    fun addWordActivity(view: View){
        val intent:Intent = Intent(this,MainActivity2::class.java)
        intent.putExtra("name","Ammar Ali")
        startActivityForResult(intent,REQ_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQ_CODE ){
            if(data!=null){
                val word1 = data.getStringExtra("word")
                val def1 = data.getStringExtra("def")
                wordToDefn.put(word1!!,def1!!)
                words.add(word1)
            }


        }
    }

}