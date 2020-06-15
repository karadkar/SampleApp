package io.github.karadkar.sample

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat

class SurfaceView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttrs: Int) : super(context, attrs, defStyleAttrs)

    private val defaultPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        defaultPaint.apply {
            color = ContextCompat.getColor(context, R.color.colorAccent)
        }
    }

    private var progress = 0f
    fun transformToCircle() {
        val animator = ValueAnimator.ofFloat(0f, 125f)
        animator.duration = 2000
        animator.addUpdateListener {
            progress = it.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        val midX = width / 2f // middle point on X-axis
        val midY = height / 2f // middle point on  Y-axis

        val maxRadius = (maxOf(width, height) / 2f)
        val progress = this.progress / 100 // 15% to 0.15

        val radius = maxRadius * progress


        val leftTopX = midX - radius
        val leftTopY = midY - radius

        val rightBotX = midX + radius
        val rightBotY = midY + radius

        canvas.drawRoundRect(leftTopX, leftTopY, rightBotX, rightBotY, radius, radius, defaultPaint)
        Log.e(
            "SurfaceView",
            "radius:$radius leftTopX:$leftTopX, leftTopY$leftTopY, rightBotX$rightBotX, rightBotY:$rightBotY"
        )
    }

    private fun drawCircularReveal(canvas: Canvas) {

        val midX = width / 2f // middle point on X-axis
        val midY = height / 2f // middle point on  Y-axis
        val maxRadius = (maxOf(width, height) / 2f)

        // 50 * 0.1 = 10
        val radius = maxRadius * (progress / 100)
        canvas.drawCircle(midX, midY, radius, defaultPaint)
    }
}