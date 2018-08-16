package com.charliesong.wanandroid.setting

import android.arch.paging.DataSource
import android.arch.paging.ItemKeyedDataSource
import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.base.BaseRvHolder
import com.charliesong.wanandroid.base.FragmentBase
import com.charliesong.wanandroid.home.FragmentBaseAlpha
import kotlinx.android.synthetic.main.simple_recyclerview.*

class TestFragment:FragmentBase(){

    override fun getLayoutID(): Int {
        return R.layout.simple_recyclerview
    }
    open fun getRv2():RecyclerView{
        return  rv_simple
    }
    open fun mySWF2():SwipeRefreshLayout{
        return swf_simple
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        println("onActivityCreated=========${getRv2()}======${mySWF2()}")
        view?.setOnTouchListener { v, event ->
            println("2================$swf_simple====${view?.findViewById<View>(R.id.test222)}========${view?.findViewById<View>(R.id.swf_simple)}")
            return@setOnTouchListener false
        }
        println("3====================$view")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("4================$swf_simple")
//        println("onViewCreated===============${mySWF2()}=============${getRv2()}")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        println("onAttach=====================")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate=====================")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("onCreateView================")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        println("onStart================")
    }

    override fun onResume() {
        super.onResume()
        println("onResume================")
    }

    override fun onPause() {
        super.onPause()
        println("onPause================")
    }

    override fun onDetach() {
        super.onDetach()
        println("onDetach================")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("onDestroyView================")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy================")
    }

    override fun onStop() {
        super.onStop()
        println("onStop================")
    }
}