package com.techinnovator.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PlayersNameActivity : AppCompatActivity() {

    lateinit var etPlayer1Name: EditText
    lateinit var etPlayer2Name: EditText
    lateinit var btnPlay: Button
    lateinit var btnHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players_name)
        title = "Player's Name"

        etPlayer1Name = findViewById(R.id.etPlayer1Name)
        etPlayer2Name = findViewById(R.id.etPlayer2Name)
        btnPlay = findViewById(R.id.btnPlay)
        btnHome = findViewById(R.id.btnHome)

        btnPlay.setOnClickListener {
            if(etPlayer1Name.text.toString().equals("") || etPlayer2Name.text.toString().equals("")){
                Toast.makeText(this, "Enter player's name", Toast.LENGTH_LONG).show()
            }
            else{
                var intent = Intent(this, SinglePlayerActivity::class.java)
                var s1 = etPlayer1Name.text.toString()
                var s2 = etPlayer2Name.text.toString()

                if(s1.length > 10){
                    s1 = s1.substring(0,10)
                }
                if(s2.length>10){
                    s2 = s2.substring(0,10);
                }
                intent.putExtra("player1", s1)
                intent.putExtra("player2", s2)
                startActivity(intent)
                finish()
            }

        }
        btnHome.setOnClickListener {
//            var intent = Intent(this, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            startActivity(intent)
            onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
    override fun onPause() {
        super.onPause()
    }
}