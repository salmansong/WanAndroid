package com.charliesong.wanandroid.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class FragmentBase:Fragment(){

    abstract  fun getLayoutID():Int
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        println("onCreateView==================${javaClass.name}")
        return layoutInflater.inflate(getLayoutID(),container,false)
    }
}