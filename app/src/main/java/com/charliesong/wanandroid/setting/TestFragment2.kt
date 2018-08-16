package com.charliesong.wanandroid.setting

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.base.FragmentBase
import kotlinx.android.synthetic.main.fragment_navigation.*

class TestFragment2:FragmentBase(){

    override fun getLayoutID(): Int {
        return R.layout.fragment_navigation
    }
    open fun getRv2():RecyclerView{
        return  rv
    }
    open fun mySWF2():SwipeRefreshLayout{
        return swf_navigation
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("onActivityCreated2=========${getRv2()}======${mySWF2()}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("onViewCreated2===============${mySWF2()}=============${getRv2()}")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        println("onAttach2=====================")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate2=====================")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("onCreateView2================")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        println("onStart2================")
    }

    override fun onResume() {
        super.onResume()
        println("onResume2================")
    }

    override fun onPause() {
        super.onPause()
        println("onPause2================")
    }

    override fun onDetach() {
        super.onDetach()
        println("onDetach2===============")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("onDestroyView2================")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy2================")
    }

    override fun onStop() {
        super.onStop()
        println("onStop2================")
    }
}