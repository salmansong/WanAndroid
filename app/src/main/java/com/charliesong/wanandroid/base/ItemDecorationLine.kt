package com.charliesong.wanandroid.base

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class ItemDecorationLine:RecyclerView.ItemDecoration{
    var lineHeight=1
    var lineColor= Color.LTGRAY

    var paint=Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(lineHeight: Int=1, lineColor: Int=Color.GRAY) : super() {
        this.lineHeight = lineHeight
        this.lineColor = lineColor
    }

    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom=lineHeight
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
        (0 until parent.childCount).forEach {
            parent.getChildAt(it)?.apply {
                c.drawRect(paddingLeft.toFloat(),this.bottom.toFloat(),right-paddingRight.toFloat(),this.bottom+lineHeight.toFloat(),paint)
            }
        }
    }
}