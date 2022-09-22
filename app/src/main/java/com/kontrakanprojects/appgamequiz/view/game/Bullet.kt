package com.kontrakanprojects.appgamequiz.view.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.kontrakanprojects.appgamequiz.R
import com.kontrakanprojects.appgamequiz.view.game.GameView.Companion.screenRatioX
import com.kontrakanprojects.appgamequiz.view.game.GameView.Companion.screenRatioY

class Bullet(res: Resources) {

    var x = 0F
    var y = 0F
    var width = 0F
    var height = 0F
    var bullet: Bitmap = BitmapFactory.decodeResource(res, R.drawable.bullet)

    init {

        width = bullet.width.toFloat()
        height = bullet.height.toFloat()

        width /= 4
        height /= 4

        width *= screenRatioX
        height *= screenRatioY

        bullet = Bitmap.createScaledBitmap(bullet, width.toInt(), height.toInt(), false)
    }

    fun getCollisionShape(): Rect {
        return Rect(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt())
    }
}