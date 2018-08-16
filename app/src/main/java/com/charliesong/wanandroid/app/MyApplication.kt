package com.charliesong.wanandroid.app

import android.app.Application

class MyApplication:Application(){

    companion object {
       private  var application: MyApplication?=null
        fun getApp(): MyApplication {
            return application!!;
        }
    }

    override fun onCreate() {
        super.onCreate()
        application =this
    }
}