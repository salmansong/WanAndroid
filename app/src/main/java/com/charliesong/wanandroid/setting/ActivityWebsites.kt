package com.charliesong.wanandroid.setting

import android.os.Bundle
import com.charliesong.wanandroid.base.BaseActivity
import com.charliesong.wanandroid.R

class ActivityWebsites: BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_collection)
        setToolbarTitle("常用网站")
        supportFragmentManager.findFragmentById(R.id.layout_fragment)?:(supportFragmentManager.beginTransaction().replace(R.id.layout_fragment,FragmentWebsites()).commitAllowingStateLoss())
    }
}