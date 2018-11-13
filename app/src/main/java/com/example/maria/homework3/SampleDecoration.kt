package com.example.maria.homework3

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

internal class SampleDecoration(context: Context): RecyclerView.ItemDecoration() {

    private val divider: Drawable
    private var mPaint: Paint = Paint()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val viewType = parent.adapter!!.getItemViewType(position)
        when (viewType) {
            0 -> outRect.set(0.px, 16.px, 0.px, 16.px)
            1 -> outRect.set(36.px, 28.px, 120.px, 28.px)
            2 -> outRect.set(120.px, 28.px, 36.px, 28.px)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        mPaint.style = Paint.Style.FILL
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            val viewType = parent.adapter!!.getItemViewType(position)
            when (viewType) {
                0 -> {
                    mPaint.color = Color.parseColor("#90A4AE")
                    c.drawRect(view.left.toFloat()-16.px, view.bottom.toFloat()+16.px, view.right.toFloat()+16.px, (view.bottom - view.height-16.px).toFloat(), mPaint)
                }
                1 -> {
                    mPaint.color = Color.parseColor("#f5964e")
                    c.drawOval(view.left.toFloat()-23.px, view.bottom.toFloat()+23.px, view.right.toFloat()+23.px, (view.bottom - view.height-23.px).toFloat(), mPaint)
                    mPaint.color = Color.parseColor("#ffb15e")
                    c.drawOval(view.left.toFloat()-20.px, view.bottom.toFloat()+20.px, view.right.toFloat()+20.px, (view.bottom - view.height-20.px).toFloat(), mPaint)
                }
                2 -> {
                    var width:Int
                    val tv: TextView=view.findViewById(R.id.textView)
                    val et: TextView=view.findViewById(R.id.editText)
                    width = if(tv.width>et.width){
                        tv.width
                    } else et.width
                    mPaint.color = Color.parseColor("#389aeb")
                    c.drawOval(view.right.toFloat()-23.px-width, view.bottom.toFloat()+23.px, view.right.toFloat()+23.px, (view.bottom - view.height-23.px).toFloat(), mPaint)
                    mPaint.color = Color.parseColor("#5cb6ff")
                    c.drawOval(view.right.toFloat()-20.px-width, view.bottom.toFloat()+20.px, view.right.toFloat()+20.px, (view.bottom - view.height-20.px).toFloat(), mPaint)
                }
            }
        }
    }

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        divider = a.getDrawable(0)
        a.recycle()
    }

    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}

