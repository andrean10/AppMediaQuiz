package com.kontrakanprojects.appgamequiz.view.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.kontrakanprojects.appgamequiz.R
import com.kontrakanprojects.appgamequiz.view.game.GameView.Companion.screenRatioX
import com.kontrakanprojects.appgamequiz.view.game.GameView.Companion.screenRatioY

class Bird(res: Resources) {

    var x = 0F
    var y = 0F
    var width = 0F
    var height = 0F
    var wasShot = true
    var speed = 20
    var birdCounter = 1
    private var bird1: Bitmap
    private var bird2: Bitmap
    private var bird3: Bitmap
    private var bird4: Bitmap

    init {
        bird1 = BitmapFactory.decodeResource(res, R.drawable.bird1)
        bird2 = BitmapFactory.decodeResource(res, R.drawable.bird2)
        bird3 = BitmapFactory.decodeResource(res, R.drawable.bird3)
        bird4 = BitmapFactory.decodeResource(res, R.drawable.bird4)

        width = bird1.width.toFloat()
        height = bird1.height.toFloat()
        
        width /= 6
        height /= 6

        width *= screenRatioX
        height *= screenRatioY

        bird1 = Bitmap.createScaledBitmap(bird1, width.toInt(), height.toInt(), false)
        bird2 = Bitmap.createScaledBitmap(bird2, width.toInt(), height.toInt(), false)
        bird3 = Bitmap.createScaledBitmap(bird3, width.toInt(), height.toInt(), false)
        bird4 = Bitmap.createScaledBitmap(bird4, width.toInt(), height.toInt(), false)

        y = -height
    }

    fun getBird(): Bitmap {
        when(birdCounter) {
            1 -> {
                birdCounter++
                return bird1
            }
            2 -> {
                birdCounter++
                return bird2
            }
            3 -> {
                birdCounter++
                return bird3
            }
        }

        birdCounter = 1
        return bird4
    }

    fun getCollisionShape(): Rect {
        return Rect(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt())
    }
}