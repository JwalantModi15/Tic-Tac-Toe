package com.techinnovator.tictactoe

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class PlayerVsComputerInfo : AppCompatActivity() {
    lateinit var txtPlayerInfo: TextView
    lateinit var txtLevelInfo: TextView
    lateinit var txtScoreInfo: TextView
    lateinit var txtHighestScore: TextView
    lateinit var btnNextLevel: Button
    lateinit var btnHomePage: Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferences1: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_vs_computer_info)

        title = "Level Information"
        txtPlayerInfo = findViewById(R.id.txtPlayerInfo)
        txtLevelInfo = findViewById(R.id.txtLevelInfo)
        txtScoreInfo = findViewById(R.id.txtScoreInfo)
        btnNextLevel = findViewById(R.id.btnNextLevel)
        btnHomePage = findViewById(R.id.btnHomePage)
        txtHighestScore = findViewById(R.id.txtHighestScore);

        sharedPreferences = getSharedPreferences(getString(R.string.data_file), Context.MODE_PRIVATE)
        sharedPreferences1 = getSharedPreferences(getString(R.string.score_file), Context.MODE_PRIVATE)
        var isSameLevel = false

        if(intent!=null){
            txtPlayerInfo.text = intent.getStringExtra("winnerInformation")
            var score = intent.getStringExtra("score")
            var level = intent.getStringExtra("level")
            var name = intent.getStringExtra("name")
            var highestScore = sharedPreferences1.getString("highestScore", score)
            var isHigh = intent.getBooleanExtra("isHigh", false)

            if(name!=""){
                btnNextLevel.text = name
                isSameLevel = true
                btnNextLevel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_replay, 0,0,0);
            }
            else{
                btnNextLevel.text = "Next Level"
                btnNextLevel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_forward, 0,0,0);
                isSameLevel = false
            }
            txtLevelInfo.append(level)
            txtScoreInfo.append(score)
            txtHighestScore.append(highestScore)
            txtHighestScore.append(")")

            if(isHigh){
//                AlertDialog.Builder(this).setCancelable(false).setTitle("Congratulations!").setMessage("Your new highscore: "+highestScore)
//                        .setIcon(R.drawable.ic_celebration).setPositiveButton("Ok",  DialogInterface.OnClickListener { dialog, which ->  dialog.dismiss()})
//                        .show()
                var sn = Snackbar.make(txtHighestScore, "Your New Highest Score: $highestScore", Snackbar.LENGTH_INDEFINITE)
                sn.setAction("Ok", View.OnClickListener {
                    sn.dismiss()
                }).show()
            }
        }
        btnNextLevel.setOnClickListener {
            var intent = Intent(this, PlayerVsComputerActivity::class.java)
            intent.putExtra("isSameLevel", isSameLevel)
            startActivity(intent)
            finish()
        }
        btnHomePage.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        AlertDialog.Builder(this).setMessage("Leave game?").setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            sharedPreferences.edit().clear().apply()
            super.onBackPressed()
        }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }).setCancelable(false).show()
        return true
    }
    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).setMessage("Leave game?").setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            sharedPreferences.edit().clear().apply()
            super.onBackPressed()
        }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }).setCancelable(false).show()
    }
}