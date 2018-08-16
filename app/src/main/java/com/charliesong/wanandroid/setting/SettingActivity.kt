package com.charliesong.wanandroid.setting

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Html
import android.text.TextUtils
import com.charliesong.wanandroid.NormalUtil
import com.charliesong.wanandroid.base.BaseActivity
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.login.ActivityLogin
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity: BaseActivity(){

    var txts= arrayListOf<String>("<font color=\"#ff0000\"> 第一个 </font>aaaa","<![CDATA[222<font color=\"#ff0000\"> 第2个 </font>222 ]]>",".............")
    var num=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setToolbarTitle("settting")
       changetxt()
        tv_test.setOnClickListener {
            num++
            changetxt()
        }
    }

    fun changetxt(){
        tv_test.setText(Html.fromHtml(txts[num%txts.size]))
    }
    override fun onResume() {
        super.onResume()
        btn_login.apply {
            var name=NormalUtil.checkCookie(this@SettingActivity)
            setText(if(TextUtils.isEmpty(name)) R.string.txt_login_in else R.string.txt_login_out)
            setOnClickListener {
                if(TextUtils.isEmpty(name)){
                    //登陆
                    startActivity(Intent(this@SettingActivity,ActivityLogin::class.java))
                }else{
                    //登出

                    loginOutMakesure()
                }
            }
        }
    }

    private fun loginOutMakesure(){
        AlertDialog.Builder(this).setTitle("Make sure").setMessage("are you sure to login out?")
                .setNegativeButton("cancel",null).setPositiveButton("yes",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        NormalUtil.loginOut(this@SettingActivity)
                        btn_login.setText(R.string.txt_login_in)
                    }
                }).show()
    }
}