package com.techinnovator.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ScoreTableActivity : AppCompatActivity() {

    lateinit var txtno1: TextView
    lateinit var txtno2: TextView
    lateinit var txtno3: TextView
    lateinit var txtno4: TextView
    lateinit var txtno5: TextView

    lateinit var txtLevel1: TextView
    lateinit var txtLevel2: TextView
    lateinit var txtLevel3: TextView
    lateinit var txtLevel4: TextView
    lateinit var txtLevel5: TextView

    lateinit var txtScore1: TextView
    lateinit var txtScore2: TextView
    lateinit var txtScore3: TextView
    lateinit var txtScore4: TextView
    lateinit var txtScore5: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_table)

        txtno1 = findViewById(R.id.txtno1)
        txtno2 = findViewById(R.id.txtno2)
        txtno3 = findViewById(R.id.txtno3)
        txtno4 = findViewById(R.id.txtno4)
        txtno5 = findViewById(R.id.txtno5)

        txtLevel1 = findViewById(R.id.txtLevel1)
        txtLevel2 = findViewById(R.id.txtLevel2)
        txtLevel3 = findViewById(R.id.txtLevel3)
        txtLevel4 = findViewById(R.id.txtLevel4)
        txtLevel5 = findViewById(R.id.txtLevel5)

        txtScore1 = findViewById(R.id.txtScore1)
        txtScore2 = findViewById(R.id.txtScore2)
        txtScore3 = findViewById(R.id.txtScore3)
        txtScore4 = findViewById(R.id.txtScore4)
        txtScore5 = findViewById(R.id.txtScore5)
    }
}