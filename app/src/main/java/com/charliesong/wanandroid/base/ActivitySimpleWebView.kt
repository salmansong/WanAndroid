package com.charliesong.wanandroid.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.charliesong.wanandroid.R
import kotlinx.android.synthetic.main.include_simple_webview.*

open class ActivitySimpleWebView : BaseActivity() {

    var wv:WebView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_webview)
        wv=wv_simple
        initWebView()
    }

    override fun onBackPressed() {
        if(wv?.canGoBack()?:false){
            wv?.goBack()
        }else{
            super.onBackPressed()
        }
    }
    private fun initWebView() {
        wv?.settings?.apply {
            javaScriptEnabled = true

            setSupportZoom(true)
            displayZoomControls = true
            builtInZoomControls = true

            domStorageEnabled = true

            setAppCacheEnabled(true)
            setAppCachePath(cacheDir.absolutePath + "/MyWebView")
            if (isNetworkConnected(this@ActivitySimpleWebView)){
                cacheMode=WebSettings.LOAD_NO_CACHE

            }else{
                cacheMode=WebSettings.LOAD_CACHE_ONLY

            }
            databaseEnabled = true//启用数据库

            loadWithOverviewMode = true
            useWideViewPort = true
            defaultTextEncodingName = "GBK"
            loadsImagesAutomatically = true

            setGeolocationEnabled(true)

        }
        wv?.webChromeClient = MyWebChromeClient(pb_simple)
        wv?.webViewClient= WebViewClient()
    }

    companion object {

        class MyWebChromeClient : WebChromeClient {
            var pb: ProgressBar? = null

            constructor(pb: ProgressBar?) : super() {
                this.pb = pb
            }

            constructor() : super()

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                pb?.apply {
                    setProgress(newProgress)
                    visibility = if (newProgress >= 99) View.GONE else View.VISIBLE
                }
            }
        }
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

    var internetReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (ConnectivityManager.CONNECTIVITY_ACTION == intent?.action) {
                val activeNetwork = (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)?.activeNetworkInfo
                if (activeNetwork != null) { // connected to the internet
                    if (activeNetwork.isConnected) {
                        connectedChange(true)
                    } else {
                        connectedChange(false)
                    }

                } else {
                    connectedChange(false)
                }
            }
        }
    }
    fun connectedChange(isConnected: Boolean) {
        var mode = if (isConnected) WebSettings.LOAD_NO_CACHE else WebSettings.LOAD_CACHE_ONLY
        var modeOld=wv?.settings?.cacheMode?:WebSettings.LOAD_DEFAULT
        if(modeOld!=mode){
            wv?.settings?.cacheMode = mode
            if(modeOld== WebSettings.LOAD_CACHE_ONLY){
                wv?.reload()
            }
        }
        println("connectedChange mode=====================$mode")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_close,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.action_close){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onResume() {
        super.onResume()
        registerConnected()
    }

    override fun onPause() {
        super.onPause()
        unRegisterConnected()
    }
    private fun registerConnected(){
        var filter = IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(internetReceiver, filter)
    }
    private fun unRegisterConnected(){
        unregisterReceiver(internetReceiver)
    }

}