package com.charliesong.wanandroid.login

import android.annotation.SuppressLint
import android.text.TextUtils
import com.charliesong.wanandroid.app.MyAPIManager
import com.charliesong.wanandroid.retrofit.BaseFunction
import io.reactivex.schedulers.Schedulers

class PresenterLogin(val viewLogin: LoginContract.View) : LoginContract.Presenter {

    init {
        viewLogin.presenter = this
    }

    override fun start() {

    }

    @SuppressLint("CheckResult")
    override fun login(userName: String, password: String) {
        var check = checkNamePsw(userName, password, password)
        if (!TextUtils.isEmpty(check)) {
            viewLogin.loginFailed(check)
            return
        }
        MyAPIManager.getAPI().login(userName, password).subscribeOn(Schedulers.io())
                .compose(BaseFunction.handle())
                .subscribe({
                    println("登陆成功")
                    viewLogin.loginSuccess()
                }, {
                    viewLogin.loginFailed(it.message + "")
                })

    }

    private fun checkNamePsw(userName: String, psw: String, psw2: String): String {
        if (TextUtils.isEmpty(userName)) {
            return "手机号不能为空"
        }
        if (userName.length != 11) {
            return "请输入正确的手机号"
        }
        if (psw.length < 6 || psw2.length < 6) {
            return "密码最少为6位"
        }
        if (!TextUtils.equals(psw, psw2)) {
            return "两次密码不一致"
        }
        return ""
    }

    @SuppressLint("CheckResult")
    override fun register(userName: String, password: String, password2: String) {
        var check = checkNamePsw(userName, password, password2)
        if (!TextUtils.isEmpty(check)) {
            viewLogin.registerFailed(check)
            return
        }
        MyAPIManager.getAPI().register(userName, password, password2)
                .compose(BaseFunction.handle())
                .subscribe({
                    println("注册成功")
                    viewLogin.registerSuccess()
                }, {
                    viewLogin.registerFailed(it.message + "")
                })
    }
}