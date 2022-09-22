package com.kontrakanprojects.appgamequiz.view.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.kontrakanprojects.appgamequiz.R
import com.kontrakanprojects.appgamequiz.view.game.GameView.Companion.screenRatioX
import com.kontrakanprojects.appgamequiz.view.game.GameView.Companion.screenRatioY

class Flight internal constructor(private val gameView: GameView, screenY: Int, res: Resources) {

    var isGoingUp = false
    var x = 0f
    var y = 0f
    var width = 0F
    var height = 0F
    var toShoot = 0
    private var wingCounter = 0
    private var shootCounter = 1
    private var flight1: Bitmap
    private var flight2: Bitmap
    private var shoot1: Bitmap
    private var shoot2: Bitmap
    private var shoot3: Bitmap
    private var shoot4: Bitmap
    private var shoot5: Bitmap
    private var dead: Bitmap

    init {
        flight1 = BitmapFactory.decodeResource(res, R.drawable.fly1)
        flight2 = BitmapFactory.decodeResource(res, R.drawable.fly2)

        width = flight1.width.toFloat()
        height = flight1.height.toFloat()

        width /= 4
        height /= 4

        width *= screenRatioX
        height *= screenRatioY

        flight1 = Bitmap.createScaledBitmap(flight1, width.toInt(), height.toInt(), false)
        flight2 = Bitmap.createScaledBitmap(flight2, width.toInt(), height.toInt(), false)

        y = (screenY / 2).toFloat()
        x = 64 * screenRatioX

        /* Shoot Code */
        shoot1 = BitmapFactory.decodeResource(res, R.drawable.shoot1)
        shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot2)
        shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot3)
        shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot4)
        shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot5)

        shoot1 = Bitmap.createScaledBitmap(shoot1, width.toInt(), height.toInt(), false)
        shoot2 = Bitmap.createScaledBitmap(shoot2, width.toInt(), height.toInt(), false)
        shoot3 = Bitmap.createScaledBitmap(shoot3, width.toInt(), height.toInt(), false)
        shoot4 = Bitmap.createScaledBitmap(shoot4, width.toInt(), height.toInt(), false)
        shoot5 = Bitmap.createScaledBitmap(shoot5, width.toInt(), height.toInt(), false)

        dead = BitmapFactory.decodeResource(res, R.drawable.dead)
        dead = Bitmap.createScaledBitmap(dead, width.toInt(), height.toInt(), false)

    }

    fun getFlight(): Bitmap {
        /* Shoot */
        if (toShoot != 0) {
            when (shootCounter) {
                1 -> {
                    shootCounter++
                    return shoot1
                }
                2 -> {
                    shootCounter++
                    return shoot2
                }
                3 -> {
                    shootCounter++
                    return shoot3
                }
                4 -> {
                    shootCounter++
                    return shoot4
                }
                else -> {
                    shootCounter = 1
                    toShoot--
                    gameView.newBullet()
                    return shoot5
                }
            }
        }


        if (wingCounter == 0) {
            wingCounter++
            return flight1
        }

        wingCounter--
        return flight2
    }

    fun getCollisionShape(): Rect {
        return Rect(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt())
    }

    fun getDead(): Bitmap {
        return dead
    }
}