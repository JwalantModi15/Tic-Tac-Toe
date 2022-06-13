package com.techinnovator.tictactoe

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class SinglePlayerActivity : AppCompatActivity() {

    lateinit var btn1:Button
    lateinit var btn2:Button
    lateinit var btn3:Button
    lateinit var btn4:Button
    lateinit var btn5:Button
    lateinit var btn6:Button
    lateinit var btn7:Button
    lateinit var btn8:Button
    lateinit var btn9:Button
    lateinit var b1:Button
    lateinit var b2:Button
    lateinit var b3:Button
    lateinit var txtPlayerTurn: TextView
    lateinit var txtHeadingPlayers: TextView
    lateinit var txtPlayer1: TextView
    lateinit var txtPlayer2: TextView
    lateinit var sharedPreferences: SharedPreferences

    var list = mutableListOf<Button>()

    var c = 0

    var color: Int = -1

    var player1Name:String? = ""
    var player2Name:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player_layout)
        title = "Player vs Player"

        sharedPreferences = getSharedPreferences(getString(R.string.data_file), Context.MODE_PRIVATE)

        var isRematch = false

        if(intent!=null){
            player1Name = intent.getStringExtra("player1")
            player2Name = intent.getStringExtra("player2")
            isRematch = intent.getBooleanExtra("rematch", false)
        }
        println("1 = ${player1Name}, 2 = ${player2Name}")

        if(isRematch){
            player1Name = sharedPreferences.getString("player1Name", player1Name)
            player2Name = sharedPreferences.getString("player2Name", player2Name)
        }

        sharedPreferences.edit().putString("player1Name", player1Name).apply()
        sharedPreferences.edit().putString("player2Name", player2Name).apply()

        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        txtPlayerTurn = findViewById(R.id.txtPlayerTurn)
        txtHeadingPlayers = findViewById(R.id.txtHeadingPlayers)
        txtPlayer1 = findViewById(R.id.txtPlayer1)
        txtPlayer2 = findViewById(R.id.txtPlayer2)

        setBtnColor()

        list.add(btn1)
        list.add(btn2)
        list.add(btn3)
        list.add(btn4)
        list.add(btn5)
        list.add(btn6)
        list.add(btn7)
        list.add(btn8)
        list.add(btn9)

        txtPlayer1.text = "${player1Name} - O"
        txtPlayer2.text = "${player2Name} - X"

        txtPlayerTurn.text = "${player1Name} Turns"

        for (btn in list){
            btn.setOnClickListener {
                if(btn.text == ""){
                    if(c%2==0){
                        btn.text = "O"
                        txtPlayerTurn.text = "${player2Name} Turns"
                    }
                    else{
                        btn.text = "X"
                        txtPlayerTurn.text = "${player1Name} Turns"
                    }
                    c++
                }
                if(winner("O")){
                    disableBtn()
                    txtPlayerTurn.text = ""
                    Handler().postDelayed(Runnable {
                        reset()
                        var intent = Intent(this, SinglePlayerInfoActivity::class.java)
                        intent.putExtra("winnerInfo", "$player1Name Wins!")
                        startActivity(intent)
                        finish()
                    }, 1500)
                    b1.setTextColor(resources.getColor(R.color.dark_red))
                    b2.setTextColor(resources.getColor(R.color.dark_red))
                    b3.setTextColor(resources.getColor(R.color.dark_red))
                    b1.setBackgroundColor(resources.getColor(R.color.gray))
                    b2.setBackgroundColor(resources.getColor(R.color.gray))
                    b3.setBackgroundColor(resources.getColor(R.color.gray))
                }
                else if(winner("X")){
                    disableBtn()
                    txtPlayerTurn.text = ""
                    Handler().postDelayed(Runnable {
                        reset()
                        var intent = Intent(this, SinglePlayerInfoActivity::class.java)
                        intent.putExtra("winnerInfo", "$player2Name Wins!")
                        startActivity(intent)
                        finish()
                    }, 1500)
                    b1.setTextColor(resources.getColor(R.color.dark_red))
                    b2.setTextColor(resources.getColor(R.color.dark_red))
                    b3.setTextColor(resources.getColor(R.color.dark_red))
                    b1.setBackgroundColor(resources.getColor(R.color.gray))
                    b2.setBackgroundColor(resources.getColor(R.color.gray))
                    b3.setBackgroundColor(resources.getColor(R.color.gray))
                }
                else if(tie()){
                    disableBtn()
                    txtPlayerTurn.text = ""
                    Handler().postDelayed(Runnable {
                        reset()
                        var intent = Intent(this, SinglePlayerInfoActivity::class.java)
                        intent.putExtra("winnerInfo", "Match Ties")
                        startActivity(intent)
                        finish()
                    }, 1500)

                }
            }
        }
    }

    private fun disableBtn(){
        for(btn in list){
            btn.isClickable = false
        }
    }

    private fun tie():Boolean{
        return (btn1.text != "" && btn2.text != "" && btn3.text != "" &&
                btn4.text != "" && btn5.text != "" && btn6.text != "" &&
                btn7.text != "" && btn8.text != "" && btn9.text != "")
    }

    private fun winner(name: String):Boolean{
        if(btn1.text == name && btn2.text == name && btn3.text == name){
            b1 = btn1
            b2 = btn2
            b3 = btn3
            return true
        }
        else if(btn4.text == name && btn5.text == name && btn6.text == name){
            b1 = btn4
            b2 = btn5
            b3 = btn6
            return true
        }
        else if(btn7.text == name && btn8.text == name && btn9.text == name){
            b1 = btn7
            b2 = btn8
            b3 = btn9
            return true
        }
        else if(btn1.text == name && btn4.text == name && btn7.text == name){
            b1 = btn1
            b2 = btn4
            b3 = btn7
            return true
        }
        else if(btn2.text == name && btn5.text == name && btn8.text == name){
            b1 = btn2
            b2 = btn5
            b3 = btn8
            return true
        }
        else if(btn3.text == name && btn6.text == name && btn9.text == name){
            b1 = btn3
            b2 = btn6
            b3 = btn9
            return true
        }
        else if(btn1.text == name && btn5.text == name && btn9.text == name){
            b1 = btn1
            b2 = btn5
            b3 = btn9
            return true
        }
        else if(btn3.text == name && btn5.text == name && btn7.text == name){
            b1 = btn3
            b2 = btn5
            b3 = btn7
            return true
        }
        return false
//        return (btn1.text == name && btn2.text == name && btn3.text == name ||
//                btn4.text == name && btn5.text == name && btn6.text == name ||
//                btn7.text == name && btn8.text == name && btn9.text == name ||
//                btn1.text == name && btn4.text == name && btn7.text == name ||
//                btn2.text == name && btn5.text == name && btn8.text == name ||
//                btn3.text == name && btn6.text == name && btn9.text == name ||
//                btn1.text == name && btn5.text == name && btn9.text == name ||
//                btn3.text == name && btn5.text == name && btn7.text == name)
    }
    private fun reset(){
        btn1.text = ""
        btn2.text = ""
        btn3.text = ""
        btn4.text = ""
        btn5.text = ""
        btn6.text = ""
        btn7.text = ""
        btn8.text = ""
        btn9.text = ""
        c=0
    }

    private fun setBtnColor(){
        color = Colors().pickColor()
        btn1.setBackgroundColor(color)
        btn2.setBackgroundColor(color)
        btn3.setBackgroundColor(color)
        btn4.setBackgroundColor(color)
        btn5.setBackgroundColor(color)
        btn6.setBackgroundColor(color)
        btn7.setBackgroundColor(color)
        btn8.setBackgroundColor(color)
        btn9.setBackgroundColor(color)
        txtHeadingPlayers.setTextColor(color)
        txtPlayerTurn.setTextColor(color)
        txtPlayer1.setTextColor(color)
        txtPlayer2.setTextColor(color)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        AlertDialog.Builder(this).setMessage("Leave game?").setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            super.onBackPressed()
        }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }).setCancelable(false).show()
        return true
    }
    override fun onBackPressed() {
        AlertDialog.Builder(this).setMessage("Leave game?").setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            super.onBackPressed()
        }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }).setCancelable(false).show()
    }
}