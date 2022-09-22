package com.kontrakanprojects.appgamequiz.view.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.kontrakanprojects.appgamequiz.R

class Background internal constructor(screenX: Int, screenY: Int, res: Resources?) {
    var x = 0f
    var y = 0f
    var background: Bitmap = BitmapFactory.decodeResource(res, R.drawable.bg)

    init {
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false)
    }
}