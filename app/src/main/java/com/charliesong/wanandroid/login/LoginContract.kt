package com.charliesong.wanandroid.login

import com.charliesong.wanandroid.base.PresenterBase
import com.charliesong.wanandroid.base.ViewBase

interface LoginContract{
    interface View: ViewBase<Presenter> {


         fun loginSuccess()
         fun loginFailed(error:String)
         fun registerSuccess()
         fun registerFailed(error:String)

         fun showLoading()
         fun hiddenLoading()
    }

    interface Presenter: PresenterBase {
        override fun start() {

        }
        fun login(userName: String, password: String)

        fun register(userName: String, password: String, password2: String)
    }
}