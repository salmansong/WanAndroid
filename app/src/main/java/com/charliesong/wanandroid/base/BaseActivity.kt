package com.charliesong.wanandroid.base

import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.inclue_simple_toolbar.*

open class BaseActivity :AppCompatActivity(){


    fun setToolbarTitle(title:CharSequence){
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setTitle(title)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private var toast: Toast?=null
    fun showToast(msg:String){
        toast?.cancel()
        toast= Toast.makeText(this,msg,Toast.LENGTH_SHORT)
        toast?.setGravity(Gravity.CENTER,0,0)
        toast?.show()
    }
}