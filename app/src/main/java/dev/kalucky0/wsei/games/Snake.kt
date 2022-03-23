package dev.kalucky0.wsei.games

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*

class Snake(context: Context?, attributes: AttributeSet) : View(context, attributes) {

    private var paint: Paint = Paint()

    private val boardWidth = 20
    private val boardHeight = 20
    private val randPos = 19
    private val allDots = 400

    private val x = IntArray(allDots)
    private val y = IntArray(allDots)

    private var nOfDots: Int = 0
    private var appleX: Int = 0
    private var appleY: Int = 0

    private var direction: Int = 0
    private var inGame = true

    private var cellSize: Float = 0f

    private fun initGame() {
        nOfDots = 3

        for (z in 0 until nOfDots) {
            x[z] = 15 - z * 10
            y[z] = 15
        }

        locateApple()

        Timer().schedule(object : TimerTask() {
            override fun run() {
                update()
                postInvalidate()
            }
        }, 50, 200)
    }

    var onScoreUpdate: ((x: Int) -> Unit)? = null

    private fun checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            nOfDots++
            locateApple()
            onScoreUpdate?.invoke(nOfDots - 3)
        }
    }

    private fun move() {
        for (z in nOfDots downTo 1) {
            x[z] = x[z - 1]
            y[z] = y[z - 1]
        }

        if (direction == 0)
            x[0] += 1

        if (direction == 1)
            y[0] += 1

        if (direction == 2)
            x[0] -= 1

        if (direction == 3)
            y[0] -= 1
    }

    private fun checkCollision() {
        for (z in nOfDots downTo 1)
            if (z > 4 && x[0] == x[z] && y[0] == y[z])
                inGame = false

        if (y[0] >= boardHeight)
            y[0] = 0

        if (y[0] < 0)
            y[0] = boardHeight - 1

        if (x[0] >= boardWidth)
            x[0] = 0

        if (x[0] < 0)
            x[0] = boardWidth - 1
    }

    private fun locateApple() {
        var r = (Math.random() * randPos).toInt()
        appleX = r

        r = (Math.random() * randPos).toInt()
        appleY = r
    }

    private fun update() {
        if (inGame) {
            Thread {
                checkApple()
                checkCollision()
                move()
            }.start()
        }
    }

    private val bgColor = Color.parseColor("#121212")
    private val cellColor = Color.parseColor("#1a1a1a")
    private val appleColor = Color.parseColor("#E53935")
    private val grayAppleColor = Color.parseColor("#E53935")
    private val snakeColor = Color.parseColor("#689F38")
    private val graySnakeColor = Color.parseColor("#6c6c6c")

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(bgColor)

        if (cellSize == 0f)
            return

        paint.color = cellColor
        for (x in 0..boardWidth) {
            for (y in 0..boardHeight) {
                canvas.drawRect(
                    x * cellSize + 2f,
                    y * cellSize + 2f,
                    x * cellSize + cellSize,
                    y * cellSize + cellSize,
                    paint
                )
            }
        }

        // draw apple
        paint.color = if (inGame) appleColor else grayAppleColor
        canvas.drawRect(
            appleX * cellSize + 2f,
            appleY * cellSize + 2f,
            appleX * cellSize + cellSize,
            appleY * cellSize + cellSize,
            paint
        )

        // draw snake
        paint.color = if (inGame) snakeColor else graySnakeColor
        for (z in 0 until nOfDots) {
            canvas.drawRect(
                x[z] * cellSize + 2f,
                y[z] * cellSize + 2f,
                x[z] * cellSize + cellSize,
                y[z] * cellSize + cellSize,
                paint
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val screenWidth = MeasureSpec.getSize(widthMeasureSpec)
        cellSize = (screenWidth - 2f) / boardWidth
        setMeasuredDimension(screenWidth, screenWidth)
    }

    fun changeDirection() {
        if (inGame) {
            when (direction) {
                0 -> direction = 1
                1 -> direction = 2
                2 -> direction = 3
                3 -> direction = 0
            }
        } else {
            nOfDots = 3
            onScoreUpdate?.invoke(0)
            inGame = true
        }
        invalidate()
    }

    init {
        paint.color = Color.WHITE
        paint.isAntiAlias = true

        initGame()
    }
}