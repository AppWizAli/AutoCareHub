package com.hiskytechs.autocarehub.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import kotlin.math.max

class DragResizeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val parts = mutableListOf<Part>()
    private var selectedPart: Part? = null
    private var mode = Mode.NONE
    private var startX = 0f
    private var startY = 0f
    private var startWidth = 0f
    private var startHeight = 0f

    init {
        setOnTouchListener { _, event -> handleTouch(event) }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (part in parts) {
            part.draw(canvas)
        }
    }

    fun addPart(drawable: Drawable) {
        val bitmap = drawable.toBitmap()
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, width / 4, height / 4, true)
        val part = Part(scaledBitmap, width / 2f, height / 2f)
        parts.add(part)
        invalidate()
    }

    private fun handleTouch(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                selectedPart = findPartAt(event.x, event.y)
                selectedPart?.let {
                    startX = event.x
                    startY = event.y
                    startWidth = it.width
                    startHeight = it.height
                    mode = if (it.isInResizeArea(event.x, event.y)) Mode.RESIZE else Mode.DRAG
                }
            }
            MotionEvent.ACTION_MOVE -> {
                selectedPart?.let {
                    val dx = event.x - startX
                    val dy = event.y - startY
                    if (mode == Mode.DRAG) {
                        it.x += dx
                        it.y += dy
                    } else if (mode == Mode.RESIZE) {
                        it.width = max(50f, startWidth + dx)
                        it.height = max(50f, startHeight + dy)
                    }
                    startX = event.x
                    startY = event.y
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                mode = Mode.NONE
            }
        }
        return true
    }

    private fun findPartAt(x: Float, y: Float): Part? {
        for (part in parts.reversed()) {
            if (part.contains(x, y)) {
                return part
            }
        }
        return null
    }

    private enum class Mode {
        NONE, DRAG, RESIZE
    }

    private class Part(val bitmap: Bitmap, var x: Float, var y: Float) {
        var width = bitmap.width.toFloat()
        var height = bitmap.height.toFloat()

        fun draw(canvas: Canvas) {
            val rect = RectF(x - width / 2, y - height / 2, x + width / 2, y + height / 2)
            canvas.drawBitmap(bitmap, null, rect, null)
        }

        fun contains(px: Float, py: Float): Boolean {
            val rect = RectF(x - width / 2, y - height / 2, x + width / 2, y + height / 2)
            return rect.contains(px, py)
        }

        fun isInResizeArea(px: Float, py: Float): Boolean {
            val rect = RectF(x + width / 2 - 50, y + height / 2 - 50, x + width / 2, y + height / 2)
            return rect.contains(px, py)
        }
    }
}
