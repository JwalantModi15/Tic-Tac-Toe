package com.techinnovator.tictactoe

import android.graphics.Color

class Colors {
    var colorsList = arrayListOf<Int>(Color.parseColor("#2196F3"), Color.parseColor("#FF5722"), Color.parseColor("#CDDC39"), Color.parseColor("#00BCD4"), Color.parseColor("#FFC107"))
    init {
        colorsList.shuffle()
    }
    fun pickColor(): Int {
        return colorsList[0]
    }
}