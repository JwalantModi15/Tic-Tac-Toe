package com.techinnovator.tictactoe

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var btnDouble: Button
    lateinit var btnSingle: Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var mainMenu:Menu
    lateinit var mediaPlayer:MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDouble = findViewById(R.id.btnDouble)
        btnSingle = findViewById(R.id.btnSingle)

        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer.start()

        mediaPlayer.setOnCompletionListener {
            mediaPlayer.start()
        }

        sharedPreferences = getSharedPreferences(getString(R.string.data_file), Context.MODE_PRIVATE)

        btnDouble.setOnClickListener {
            var intent = Intent(this, PlayersNameActivity::class.java)
            startActivity(intent)
        }
        btnSingle.setOnClickListener {
            var intent = Intent(this, PlayerVsComputerActivity::class.java)
            startActivity(intent)
        }

        var isShow = sharedPreferences.getBoolean("isShow", false)
        if(!isShow){
            Toast.makeText(this, "Music: www.bensound.com", Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putBoolean("isShow", true).apply()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.music_menu, menu)
        if (menu != null) {
            mainMenu = menu
        }
        return true
    }

    private fun playMusic(isPlay:Boolean){
        if(isPlay){
            mediaPlayer.pause()
        }
        else{
            mediaPlayer.start()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemId = item.itemId
        if(itemId==R.id.musicOn){
            item.isVisible = false;
            mainMenu.getItem(3).isVisible = true
            playMusic(false)
        }
        else if(itemId == R.id.musicOff){
            item.isVisible = false;
            mainMenu.getItem(2).isVisible = true
            playMusic(true)
        }
        else if(itemId == R.id.giveRate){
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.techinnovator.tictactoe"))
            startActivity(intent)
        }
        else if(itemId == R.id.privacyPolicy) run {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://privacypolicytictactoe-thegame.blogspot.com/2022/02/privacy-policy-tic-tac-toe-game.html"))
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        sharedPreferences.edit().clear().apply()
        mediaPlayer.pause()
        super.onDestroy()
    }

    override fun onPause() {
        sharedPreferences.edit().clear().apply()
        super.onPause()
    }
}