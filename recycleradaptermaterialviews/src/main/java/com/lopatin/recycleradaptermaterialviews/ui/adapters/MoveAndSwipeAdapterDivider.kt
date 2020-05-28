package com.lopatin.recycleradaptermaterialviews.ui.adapters

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 *  При переносе item'ов в адаптере (drag & drop) их рамки (divider) остаются на месте
 */
class MoveAndSwipeAdapterDivider(context: Context) : RecyclerView.ItemDecoration() {
    private val offset = 60 // в пикселях

    private var divider: Drawable?

    private val paint: Paint = Paint()

    init {
        val attrArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        divider = attrArray.getDrawable(0)
        attrArray.recycle()
    }


    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawVertical222(canvas,parent)
//        drawVertical(canvas, parent)
//        drawHorizontalBottomBorder(canvas, parent)
//        drawVerticalBorders(canvas, parent)
//        drawHorizontalTopBorder(canvas, parent)
    }

    private fun drawHorizontalBottomBorder(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        paint.color = Color.RED
        // толщина линии = 10
        paint.strokeWidth = 10F

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider!!.intrinsicHeight
            val circleRadius = 30f
            var horizontalCentreCircle = left.toFloat() + circleRadius
            while (horizontalCentreCircle < (right.toFloat()/* - circleRadius*/)) {
                paint.setColor(Color.RED)
                canvas.drawCircle(horizontalCentreCircle, top.toFloat(), circleRadius, paint)
                horizontalCentreCircle += circleRadius
                paint.setColor(Color.GREEN)
                canvas.drawCircle(horizontalCentreCircle, bottom.toFloat(), circleRadius, paint)
                horizontalCentreCircle += circleRadius
            }
        }
    }

    private fun drawHorizontalTopBorder(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        paint.color = Color.RED
        // толщина линии = 10
        paint.strokeWidth = 10F

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val circleRadius = 30f

            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.top - circleRadius
            val bottom = top + divider!!.intrinsicHeight


            var horizontalCentreCircle = left.toFloat() + circleRadius
            while (horizontalCentreCircle < (right.toFloat()/* - circleRadius*/)) {
                paint.setColor(Color.MAGENTA)
                canvas.drawCircle(horizontalCentreCircle, top.toFloat(), circleRadius, paint)
                horizontalCentreCircle += circleRadius
            }
            // прямоугольник
//            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    private fun drawVerticalBorders(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        Log.d("logDraw", "left $left")
        Log.d("logDraw", "right $right")
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.top - params.bottomMargin
            val bottom = child.bottom + params.bottomMargin
            val circleRadius = 30f
            Log.d("logDraw", "top $top")
            Log.d("logDraw", "bottom $bottom \n")
            var centreCircle = top.toFloat() + circleRadius
            while (centreCircle < (bottom.toFloat())) {
                paint.setColor(Color.GRAY)
                canvas.drawCircle(left.toFloat() + circleRadius, centreCircle, circleRadius, paint)
                paint.setColor(Color.BLUE)
                canvas.drawCircle(right.toFloat() - circleRadius, centreCircle, circleRadius, paint)
                centreCircle += circleRadius * 2
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        outRect.top = offset
//        outRect.right = offset
//        outRect.left = offset
        outRect.bottom = offset
    }

    fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
//c.drawColor(Color.RED) // красит весь холст
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            // проводит черту снизу
//            val top = child.bottom + params.bottomMargin
//            val bottom = top + divider!!.getIntrinsicHeight()

            // покрывает весь item
//            val top = child.top - divider!!.getIntrinsicHeight()
//            val bottom = child.bottom + divider!!.getIntrinsicHeight()

            // покрывает весь item
            val top = child.bottom + params.bottomMargin + offset / 2
            val bottom = top + divider!!.getIntrinsicHeight()
            divider!!.setBounds(left, top, right, bottom)
            divider!!.draw(c)
        }
    }

    fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + divider!!.getIntrinsicHeight()
            divider!!.setBounds(left, top, right, bottom)
            divider!!.draw(c)
        }
    }
    private fun drawVertical222(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        val dividerHeight = divider?.getIntrinsicHeight()?:1

        for (i in 1 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val ty = (child.translationY + 0.5f).toInt()
            val top = child.top - params.topMargin + ty
            val bottom = top + dividerHeight
            divider?.setBounds(left, top, right, bottom)
            divider?.draw(c)
        }
    }
}