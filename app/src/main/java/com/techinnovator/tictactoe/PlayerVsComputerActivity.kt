package com.techinnovator.tictactoe

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class PlayerVsComputerActivity : AppCompatActivity() {
    lateinit var b1: Button
    lateinit var b2: Button
    lateinit var b3: Button
    lateinit var b4: Button
    lateinit var b5: Button
    lateinit var b6: Button
    lateinit var b7: Button
    lateinit var b8: Button
    lateinit var b9: Button
    lateinit var txtLevel: TextView
    lateinit var txtScore: TextView
    lateinit var txtHeading: TextView
    lateinit var txtTurn:TextView

    var list = arrayListOf<Button>()
    var handlerList = mutableListOf<Button>()
    var arr = arrayOf("X", "O")
    var unUsedBtns = mutableListOf<Button>()
    var score = 0
    var level = 1
    var color: Int = -1
    lateinit var btn1:Button;
    lateinit var btn2:Button;
    lateinit var btn3:Button;
    var isHigh = false

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesHS: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_vs_computer)

        title = "Single Player"

        b1 = findViewById(R.id.b1)
        b2 = findViewById(R.id.b2)
        b3 = findViewById(R.id.b3)
        b4 = findViewById(R.id.b4)
        b5 = findViewById(R.id.b5)
        b6 = findViewById(R.id.b6)
        b7 = findViewById(R.id.b7)
        b8 = findViewById(R.id.b8)
        b9 = findViewById(R.id.b9)
        txtLevel = findViewById(R.id.txtLevel)
        txtScore = findViewById(R.id.txtScore)
        txtHeading = findViewById(R.id.txtHeading)
        txtTurn = findViewById(R.id.txtTurn)

        sharedPreferences = getSharedPreferences(getString(R.string.data_file), Context.MODE_PRIVATE)
        sharedPreferencesHS = getSharedPreferences(getString(R.string.score_file), Context.MODE_PRIVATE);

        var scoreData = sharedPreferences.getString("scoreData", score.toString())
        var levelData = sharedPreferences.getString("levelData", level.toString())
        var highestScoreData = sharedPreferencesHS.getString("highestScore", score.toString());
        var isDataAvail = sharedPreferences.getBoolean("dataAvail", false)
        var defaultColor = sharedPreferences.getInt("levelColor", color)

        var isSameLevel = false

        if(isDataAvail){
            txtScore.text = "Score: "
            txtLevel.text = "Level: "
            txtLevel.append(levelData)
            txtScore.append(scoreData)
            if (scoreData != null) {
                score = scoreData.toInt()
            }
            if (levelData != null) {
                level = levelData.toInt()
            }
        }
        if(intent!=null){
            isSameLevel = intent.getBooleanExtra("isSameLevel", false)
        }

        if(isSameLevel){
            setBtnLevelColor(defaultColor)
            color = defaultColor
        }
        else{
            setBtnColor()
        }

        list.add(b1)
        list.add(b2)
        list.add(b3)
        list.add(b4)
        list.add(b5)
        list.add(b6)
        list.add(b7)
        list.add(b8)
        list.add(b9)
        unUsedBtns.addAll(list)
        unUsedBtns.shuffle()

        for (btn in list){
            btn.setOnClickListener {
                if(btn.text == "") {
                    btn.text = "O"
                    handlerList.add(btn)
                    unUsedBtns.remove(btn)
                    disableBtn()
                    txtTurn.setText("Android's turn")

                    if (winner("O")) {
                        disableBtn()
                        txtTurn.setText("")
                        Handler().postDelayed(Runnable {
                            score+=10
                            level+=1

                            var l = level-1
                            reset()

                            sharedPreferences.edit().putString("scoreData", score.toString()).apply()
                            sharedPreferences.edit().putString("levelData", level.toString()).apply()
                            sharedPreferences.edit().putBoolean("dataAvail", true).apply()
                            if(score > Integer.parseInt(highestScoreData)){
                                sharedPreferencesHS.edit().putString("highestScore", score.toString()).apply()
                                isHigh = true
                            }

                            var intent = Intent(this, PlayerVsComputerInfo::class.java)
                            intent.putExtra("winnerInformation", "You Wins!")
                            intent.putExtra("score", score.toString())
                            intent.putExtra("level", l.toString())
                            intent.putExtra("name", "")
                            intent.putExtra("isHigh", isHigh)
                            startActivity(intent)
                            finish()
                        }, 1500)
                        btn1.setTextColor(resources.getColor(R.color.dark_red))
                        btn2.setTextColor(resources.getColor(R.color.dark_red))
                        btn3.setTextColor(resources.getColor(R.color.dark_red))
                        btn1.setBackgroundColor(resources.getColor(R.color.gray))
                        btn2.setBackgroundColor(resources.getColor(R.color.gray))
                        btn3.setBackgroundColor(resources.getColor(R.color.gray))
                        return@setOnClickListener
                    }
                    else if(tie()){
                        txtTurn.setText("")
                        Handler().postDelayed(Runnable {
                            score+=5
                            level+=1
                            var l = level-1
                            reset()

                            sharedPreferences.edit().putString("scoreData", score.toString()).apply()
                            sharedPreferences.edit().putString("levelData", level.toString()).apply()
                            sharedPreferences.edit().putBoolean("dataAvail", true).apply()

                            if(score > Integer.parseInt(highestScoreData)){
                                sharedPreferencesHS.edit().putString("highestScore", score.toString()).apply();
                                isHigh = true
                            }

                            var intent = Intent(this, PlayerVsComputerInfo::class.java)
                            intent.putExtra("winnerInformation", "Match Ties")
                            intent.putExtra("score", score.toString())
                            intent.putExtra("level", l.toString())
                            intent.putExtra("name", "")
                            intent.putExtra("isHigh", isHigh)
                            startActivity(intent)
                            finish()
                        }, 1500)

                        return@setOnClickListener
                    }

                    Handler().postDelayed(Runnable {
                        var button: Button? = aI()
                        button?.text = "X"
                        enableBtn()
                        txtTurn.setText("Your turn (O)")
                        if (button != null) {
                            handlerList.add(button)
                            unUsedBtns.remove(button)
                        }

                        if (winner("X")) {
                            disableBtn()
                            txtTurn.setText("")
                            Handler().postDelayed(Runnable {
                                score-=10
                                reset()
                                sharedPreferences.edit().putString("scoreData", score.toString()).apply()
                                sharedPreferences.edit().putString("levelData", level.toString()).apply()
                                sharedPreferences.edit().putBoolean("dataAvail", true).apply()
                                sharedPreferences.edit().remove("levelColor").apply()
                                sharedPreferences.edit().putInt("levelColor", color).apply()

                                var intent = Intent(this, PlayerVsComputerInfo::class.java)
                                intent.putExtra("winnerInformation", "You Lose")
                                intent.putExtra("score",score.toString())
                                intent.putExtra("level",level.toString())
                                intent.putExtra("name", "Retry")
                                startActivity(intent)
                                finish()
                            }, 1500)
                            btn1.setTextColor(resources.getColor(R.color.dark_red))
                            btn2.setTextColor(resources.getColor(R.color.dark_red))
                            btn3.setTextColor(resources.getColor(R.color.dark_red))
                            btn1.setBackgroundColor(resources.getColor(R.color.gray))
                            btn2.setBackgroundColor(resources.getColor(R.color.gray))
                            btn3.setBackgroundColor(resources.getColor(R.color.gray))
                            return@Runnable
                        }
                        else if(tie()){
                            txtTurn.setText("")
                            Handler().postDelayed(Runnable {
                                score+=5

                                level+=1

                                var l = level-1

                                reset()

                                sharedPreferences.edit().putString("scoreData", score.toString()).apply()
                                sharedPreferences.edit().putString("levelData", level.toString()).apply()
                                sharedPreferences.edit().putBoolean("dataAvail", true).apply()

                                var intent = Intent(this, PlayerVsComputerInfo::class.java)
                                intent.putExtra("winnerInformation", "Match Ties")
                                intent.putExtra("score", score.toString())
                                intent.putExtra("level", l.toString())
                                intent.putExtra("name", "")
                                startActivity(intent)
                                finish()
                            }, 1500)

                            return@Runnable
                        }
                    }, 1000)

                }

            }
        }
    }
    private fun disableBtn(){
        for(btn in list){
            btn.isClickable = false
        }
    }
    private fun enableBtn(){
        for(btn in list){
            btn.isClickable = true
        }
    }
    private fun aI():Button?{
        var flag = 0
        var n = -1
        for (i in arr){
            for (j in list){
                if(!handlerList.contains(j)){
                    j.text = i
                    if(winner(i)){
                        j.text = ""
                        flag = 1
                        return j
                    }
                    j.text = ""
                }
            }
        }
        if(flag == 0){
            return unUsedBtns[0]
        }
        return null
    }
    private fun winner(name: String):Boolean{
        if(b1.text == name && b2.text == name && b3.text == name){
            btn1 = b1
            btn2 = b2
            btn3 = b3
            return true
        }
        else if(b4.text == name && b5.text == name && b6.text == name){
            btn1 = b4
            btn2 = b5
            btn3 = b6
            return true
        }
        else if(b7.text == name && b8.text == name && b9.text == name){
            btn1 = b7
            btn2 = b8
            btn3 = b9
            return true
        }
        else if(b1.text == name && b4.text == name && b7.text == name){
            btn1 = b1
            btn2 = b4
            btn3 = b7
            return true
        }
        else if(b2.text == name && b5.text == name && b8.text == name){
            btn1 = b2
            btn2 = b5
            btn3 = b8
            return true
        }
        else if(b3.text == name && b6.text == name && b9.text == name){
            btn1 = b3
            btn2 = b6
            btn3 = b9
            return true
        }
        else if(b1.text == name && b5.text == name && b9.text == name){
            btn1 = b1
            btn2 = b5
            btn3 = b9
            return true
        }
        else if(b3.text == name && b5.text == name && b7.text == name){
            btn1 = b3
            btn2 = b5
            btn3 = b7
            return true
        }
        return false
//        return (b1.text == name && b2.text == name && b3.text == name ||
//                b4.text == name && b5.text == name && b6.text == name ||
//                b7.text == name && b8.text == name && b9.text == name ||
//                b1.text == name && b4.text == name && b7.text == name ||
//                b2.text == name && b5.text == name && b8.text == name ||
//                b3.text == name && b6.text == name && b9.text == name ||
//                b1.text == name && b5.text == name && b9.text == name ||
//                b3.text == name && b5.text == name && b7.text == name)
    }
    private fun tie():Boolean{
        return (b1.text != "" && b2.text != "" && b3.text != "" &&
                b4.text != "" && b5.text != "" && b6.text != "" &&
                b7.text != "" && b8.text != "" && b9.text != "")
    }

    private fun reset(){
        b1.text = ""
        b2.text = ""
        b3.text = ""
        b4.text = ""
        b5.text = ""
        b6.text = ""
        b7.text = ""
        b8.text = ""
        b9.text = ""
        handlerList.clear()
        unUsedBtns.addAll(list)
        unUsedBtns.shuffle()
    }
    private fun setBtnColor(){
        color = Colors().pickColor()
        b1.setBackgroundColor(color)
        b2.setBackgroundColor(color)
        b3.setBackgroundColor(color)
        b4.setBackgroundColor(color)
        b5.setBackgroundColor(color)
        b6.setBackgroundColor(color)
        b7.setBackgroundColor(color)
        b8.setBackgroundColor(color)
        b9.setBackgroundColor(color)
        txtHeading.setTextColor(color)
        txtLevel.setTextColor(color)
        txtScore.setTextColor(color)
    }
    private fun setBtnLevelColor(btnColor: Int){
        b1.setBackgroundColor(btnColor)
        b2.setBackgroundColor(btnColor)
        b3.setBackgroundColor(btnColor)
        b4.setBackgroundColor(btnColor)
        b5.setBackgroundColor(btnColor)
        b6.setBackgroundColor(btnColor)
        b7.setBackgroundColor(btnColor)
        b8.setBackgroundColor(btnColor)
        b9.setBackgroundColor(btnColor)
        txtHeading.setTextColor(btnColor)
        txtLevel.setTextColor(btnColor)
        txtScore.setTextColor(btnColor)
    }
    override fun onPause() {
        super.onPause()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        AlertDialog.Builder(this).setMessage("Leave game?").setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            sharedPreferences.edit().clear().apply()
            super.onBackPressed()
        }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }).setCancelable(false).show()
        return true
    }
    override fun onBackPressed() {
        AlertDialog.Builder(this).setMessage("Leave game?").setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            sharedPreferences.edit().clear().apply()
            super.onBackPressed()
        }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }).setCancelable(false).show()

    }
}