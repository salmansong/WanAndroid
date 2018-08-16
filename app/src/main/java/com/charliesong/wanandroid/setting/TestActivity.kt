package com.charliesong.wanandroid.setting

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.base.BaseActivity

class TestActivity:BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("setContentView 之前====================")
        setContentView(R.layout.activity_test)
        setToolbarTitle("测试页面")
        println("setContentView 之后=====================")
        supportFragmentManager.apply {
            findFragmentById(R.id.layout_fragment2)?:beginTransaction().replace(R.id.layout_fragment2,TestFragment2()).commitAllowingStateLoss()
        }
        println("replace fragment2 commit=====================")
    }

    override fun onStart() {
        super.onStart()
        println("activity onStart====================")
    }

    override fun onResume() {
        super.onResume()
        println("activity onResume================")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        println("activity=========${findViewById<View>(R.id.test222)}========${findViewById<View>(R.id.swf_simple)}")
        println(findViewById<TextView>(R.id.tv_tree))
        return super.onTouchEvent(event)
    }
}