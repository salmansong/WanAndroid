package com.charliesong.wanandroid.collectioin

import android.os.Bundle
import com.charliesong.wanandroid.base.BaseActivity
import com.charliesong.wanandroid.R

class ActivityMyCollection: BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_collection)
        setToolbarTitle("我的收藏")
        supportFragmentManager.apply {
            findFragmentById(R.id.layout_fragment)?:(beginTransaction().replace(R.id.layout_fragment,FragmentCollection(),FragmentCollection::class.java.name).commitAllowingStateLoss())
        }
    }
}