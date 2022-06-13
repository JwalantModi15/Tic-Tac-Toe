package com.techinnovator.tictactoe

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class SinglePlayerInfoActivity : AppCompatActivity() {
    lateinit var txtInfo: TextView
    lateinit var btnRestartSingle: Button
    lateinit var btnHome: Button
    lateinit var btnNewMatch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player_info)
        title = "Level Information"

        txtInfo = findViewById(R.id.txtInfo)
        btnRestartSingle = findViewById(R.id.btnRestartSingle)
        btnHome = findViewById(R.id.btnHome)
        btnNewMatch = findViewById(R.id.btnNewMatch)

        if(intent!=null){
            var info = intent.getStringExtra("winnerInfo")
            txtInfo.text = info
        }
        btnRestartSingle.setOnClickListener {
            var intent = Intent(this, SinglePlayerActivity::class.java)
            intent.putExtra("rematch", true)
            startActivity(intent)
            finish()
        }
        btnHome.setOnClickListener {
            onBackPressed()
        }
        btnNewMatch.setOnClickListener {
            var intent = Intent(this, PlayersNameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).setMessage("Leave game?").setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            super.onBackPressed()
        }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }).setCancelable(false).show()
    }
}