package com.steptracker.app.ui.today

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.steptracker.app.R

class RingProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    var progress: Int = 0
        set(value) {
            field = value.coerceIn(0, 100)
            invalidate()
        }

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 28f
        color = 0x1A000000
    }

    private val fgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 28f
        strokeCap = Paint.Cap.ROUND
        color = 0xFF1D9E75.toInt()
    }

    private val oval = RectF()

    override fun onDraw(canvas: Canvas) {
        val cx = width / 2f
        val cy = height / 2f
        val r = (minOf(width, height) / 2f) - 20f

        oval.set(cx - r, cy - r, cx + r, cy + r)
        canvas.drawArc(oval, 0f, 360f, false, bgPaint)

        if (progress > 0) {
            val sweep = 360f * progress / 100f
            canvas.drawArc(oval, -90f, sweep, false, fgPaint)
        }
    }
}
