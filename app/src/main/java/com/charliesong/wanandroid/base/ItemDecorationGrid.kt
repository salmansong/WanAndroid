package com.charliesong.wanandroid.base

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class ItemDecorationGrid:RecyclerView.ItemDecoration(){

    var space=20
    var withLeftRight=true
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        var spanCount=(parent.layoutManager as GridLayoutManager).spanCount
        var position=parent.getChildAdapterPosition(view)
        outRect.left=space/2
        outRect.right=space/2
        outRect.bottom=space
        if(withLeftRight)
        when(position%spanCount){
            0->{
                outRect.left=space
            }
            spanCount-1 ->{
                outRect.right=space
            }
            else ->{

            }
        }
    }
}