package com.charliesong.wanandroid.setting

import android.os.Bundle
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.base.BaseActivity

class HotNavigationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hot_navigation)
        setToolbarTitle("热门导航")

    }
}
