package com.charliesong.wanandroid

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.widget.ImageView
import com.charliesong.wanandroid.retrofit.cookie.PersistentCookieStore

object NormalUtil{

    fun loadSimpleImage(imageView: ImageView,url:String){

    }
    fun isNetworkConnected(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (manager == null) {
            println("ConnectivityManager is null")
            return false
        }
        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun dp2px(context: Activity,dp:Int): Float {
        var displayMetrics= DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.density*dp
    }
    var result=0
     fun getStatusBarHeight(context: Context): Int {
        if(result>0){
            return result
        }
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun checkCookie(context: Context):String{
        var cookieStore=PersistentCookieStore(context)
        cookieStore.cookies.apply {
            this.forEach {
                println("==${it.toString()}====${java.text.SimpleDateFormat("yy-MM-dd HH:mm:ss",java.util.Locale.CHINA).format(java.util.Date(it.expiresAt()))}===" +
                        "${it.name()}===${it.value()}")
                if(System.currentTimeMillis()>it.expiresAt()){
                    cookieStore.removeAll()
                    return ""
                }
                if(android.text.TextUtils.equals("loginUserName",it.name())){
                    return  it.value()
                }
            }
        }
        return ""
    }
    fun loginOut(context: Context){
        PersistentCookieStore(context).removeAll()
    }
}