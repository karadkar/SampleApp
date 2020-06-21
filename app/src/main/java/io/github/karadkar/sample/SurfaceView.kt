package io.github.karadkar.sample

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class SurfaceView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttrs: Int) : super(context, attrs, defStyleAttrs)

    private val defaultPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        defaultPaint.apply {
            color = ContextCompat.getColor(context, R.color.colorAccent)
            style = Paint.Style.FILL
        }
        borderPaint.apply {
            color = ContextCompat.getColor(context, R.color.colorAccent)
            style = Paint.Style.STROKE
            strokeWidth = 2.toPx.toFloat()
        }
    }

    private var progress = 0f
    fun transformToCircle() {
        val animator = ValueAnimator.ofFloat(0f, 100f)
        animator.duration = resources.getInteger(R.integer.box_animation_speed).toLong()
        animator.addUpdateListener {
            progress = (it.animatedValue as Float)
            invalidate()
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        progressiveCircle(canvas)
    }

    private fun progressiveCircle(canvas: Canvas) {

        val midX = width / 2f // middle point on X-axis
        val midY = height / 2f // middle point on  Y-axis
        val maxRadius = (maxOf(width, height) / 2f)
        val progress = (this.progress / 100) // 15% == 0.15

//        Log.e(this.javaClass.simpleName, "progress: $progress")
        val radius = maxRadius * progress

        canvas.drawCircle(midX, midY, radius, defaultPaint)

        val rect = Rect(0, 0, width, height)
        // draw rectangle border
        canvas.drawRect(rect, borderPaint)

        // draw filled rectangle
        if (progress >= 1.0f) {
            canvas.drawRect(rect, defaultPaint)
        }
    }

    private fun progressingRectandle(canvas: Canvas) {

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
//        Log.e(
//            "SurfaceView",
//            "radius:$radius leftTopX:$leftTopX, leftTopY$leftTopY, rightBotX$rightBotX, rightBotY:$rightBotY"
//        )
    }
}