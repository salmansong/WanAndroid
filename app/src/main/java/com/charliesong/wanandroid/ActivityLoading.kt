package com.charliesong.wanandroid

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker.PERMISSION_GRANTED
import android.widget.Toast
import com.charliesong.wanandroid.base.BaseActivity
import com.charliesong.wanandroid.home.HomeActivity
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

class ActivityLoading : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        requestExternalStorage()
        handler.postDelayed({
            thread {
                countdownLatch.await()
                if(isFinishing){
                    return@thread
                }
                startActivity(Intent(this@ActivityLoading,HomeActivity::class.java))
                finish()
            }

        },1000)
    }
    var countdownLatch:CountDownLatch= CountDownLatch(1)


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        countdownLatch.countDown()
    }
    var handler=Handler()

    fun requestExternalStorage() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }else{
            countdownLatch.countDown()
        }
    }
    fun requestPermission(){
       if( ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED){
           ActivityCompat.requestPermissions(this,
                   arrayOf(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.ACCESS_WIFI_STATE), 2)
       }else{
           countdownLatch.countDown()
       }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            for (i in permissions.indices) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show()
                    countdownLatch.countDown()
                } else {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        if(requestCode==2){
            for (i in permissions.indices) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show()
                    countdownLatch.countDown()
                } else {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
