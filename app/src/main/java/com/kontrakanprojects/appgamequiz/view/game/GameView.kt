package com.kontrakanprojects.appgamequiz.view.game

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import java.util.*

class GameView internal constructor(context: Context, screenX: Int, screenY: Int) :
    SurfaceView(context), Runnable {

    private var screenX = 0f
    private var screenY = 0f

    private lateinit var thread: Thread
    private var isPlaying = false
    private var isGameOver = false
    private var paint: Paint
    private var flight: Flight
    private var birds: Array<Bird?>
    private var random: Random
    private var bullets: ArrayList<Bullet>
    private var background1: Background
    private var background2: Background

    companion object {
        var screenRatioX = 0f
        var screenRatioY = 0f
    }

    init {
        this.screenX = screenX.toFloat()
        this.screenY = screenY.toFloat()
        screenRatioX = 1920f / screenX
        screenRatioY = 1080f / screenY

        background1 = Background(screenX, screenY, resources)
        background2 = Background(screenX, screenY, resources)

        flight = Flight(this, screenY, resources)

        bullets = arrayListOf()

        background2.x = screenX.toFloat()
        paint = Paint()

        // birds syntax
        birds = arrayOfNulls(4)

        for (i in birds.indices) {
            val bird = Bird(resources)
            birds[i] = bird
        }

        random = Random()
    }

    override fun run() {
        while (isPlaying) {
            update()
            draw()
            sleep()
        }
    }

    private fun update() {
        background1.x -= 10 * screenRatioX
        background2.x -= 10 * screenRatioX

        if (background1.x + background1.background.width < 0) {
            background1.x = screenX
        }

        if (background2.x + background2.background.width < 0) {
            background2.x = screenX
        }

        if (flight.isGoingUp) {
            flight.y -= 20 * screenRatioY
        } else {
            flight.y += 20 * screenRatioY
        }

        if (flight.y < 0) {
            flight.y = 0F
        }

        if (flight.y >= screenY - flight.height) {
            flight.y = screenY - flight.height
        }

        val trash = arrayListOf<Bullet>()

        // bullets syntax line
        for (bullet in bullets) {
            if (bullet.x > screenX) {
                trash.add(bullet)
            }
            bullet.x += 50 * screenRatioX

            for (bird in birds) {
                if (bird != null) {
                    // jika bersentuhan peluru dan burung maka burung mati
                    if (Rect.intersects(bird.getCollisionShape(), bullet.getCollisionShape())) {
                        bird.x = (-500).toFloat()
                        bullet.x = screenX + 500
                        bird.wasShot = true
                    }
                }
            }
        }

        for (bullet in trash) {
            bullets.remove(bullet)
        }

        // birds syntax line
        for (bird in birds) {
            if (bird != null) {
                bird.x -= bird.speed

                if (bird.x + bird.width < 0) {
                    // jika burung melewati lebar
                    if (!bird.wasShot) {
                        isGameOver = true
                        return
                    }

                    val bound = (30 * screenRatioX).toInt()
                    bird.speed = random.nextInt(bound)

                    if (bird.speed < 10 * screenRatioX) {
                        bird.speed = (10 * screenRatioX).toInt()
                    }

                    bird.x = screenX
                    bird.y = random.nextInt((screenY - bird.height).toInt()).toFloat()

                    bird.wasShot = false
                }

                if (Rect.intersects(bird.getCollisionShape(), flight.getCollisionShape())) {
                    isGameOver = true
                    return
                }
            }
        }
    }

    private fun draw() {
        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawBitmap(
                background1.background,
                background1.x,
                background1.y,
                paint
            )
            canvas.drawBitmap(
                background2.background,
                background2.x,
                background2.y,
                paint
            )

            if (isGameOver) {
                isPlaying = false
                canvas.drawBitmap(flight.getDead(), flight.x, flight.y, paint)
                holder.unlockCanvasAndPost(canvas)
                return
            }

            for (bird in birds) {
                if (bird != null) {
                    canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint)
                }
            }

            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, paint)

            for (bullet in bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint)
            }

            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun sleep() {
        Thread.sleep(17)
    }

    fun resume() {
        isPlaying = true
        thread = Thread(this)
        thread.start()
    }

    fun pause() {
        isPlaying = false
        thread.join()
    }

    private val TAG = GameView::class.simpleName

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.x < screenX / 2) {
                    flight.isGoingUp = true

                    Log.d(TAG, "onTouchEvent: Pesawat Ditekan")
                }
            }
            MotionEvent.ACTION_UP -> {
                flight.isGoingUp = false
                if (event.x > screenX / 2) flight.toShoot++
                Log.d(TAG, "onTouchEvent: Peluru Ditekan")
            }
        }
        return true
    }

    /* saat animasi flight menembak selesai
        lanjutkan dengan menggambar peluru
     */
    fun newBullet() {
        val bullet = Bullet(resources)
        bullet.x = flight.x + flight.width
        bullet.y = flight.y + (flight.height / 2)
        bullets.add(bullet)
    }
}