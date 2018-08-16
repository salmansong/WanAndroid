package com.charliesong.wanandroid.widget

import android.widget.LinearLayout
import android.widget.TextView
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.view.LayoutInflater
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.charliesong.wanandroid.R
import junit.framework.Assert


class SimpleProgressShow{
   private lateinit var mViewAdd: View
    private lateinit var mTv_toast: TextView
    private lateinit var mViewRoot: ViewGroup
    private var mIsOpen: Boolean = false

    private constructor()

    constructor(viewGroup: ViewGroup){
        Assert.assertNotNull(viewGroup)
        mViewAdd = LayoutInflater.from(viewGroup.context).inflate(R.layout.simple_progress_show, null)
        if (viewGroup is LinearLayout) {
            mViewAdd.setLayoutParams(LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT))
        } else if (viewGroup is RelativeLayout) {
            mViewAdd.setLayoutParams(RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT))
        } else if (viewGroup is FrameLayout) {
            mViewAdd.setLayoutParams(FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT))
        }

        mTv_toast = mViewAdd.findViewById(R.id.progressText)
        mViewRoot = viewGroup
        mIsOpen = false
    }

    fun setText(resId: Int) {
        if (mTv_toast != null) {
            mTv_toast!!.setText(resId)
        }
    }

    fun setText(text: String) {
        if (mTv_toast != null) {
            mTv_toast!!.text = text
        }
    }

//    fun setVisibility(visible: Boolean) {
//        if (visible) {
//            show()
//        } else {
//            hide()
//        }
//    }

    fun show() {
        if (!mIsOpen) {
            mIsOpen = true
            var index = -1
            if (mViewRoot is LinearLayout) {
                index = 0
            }
            mViewRoot.addView(mViewAdd, index)
        }
    }

    fun hide() {
        if (mIsOpen) {
            mIsOpen = false
            mViewRoot.removeView(mViewAdd)
        }
    }

    fun isOpen(): Boolean {
        return mIsOpen
    }
}