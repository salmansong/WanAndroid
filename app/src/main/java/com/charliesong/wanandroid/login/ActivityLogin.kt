package com.charliesong.wanandroid.login

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.charliesong.wanandroid.base.BaseActivity
import com.charliesong.wanandroid.NormalUtil
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.widget.SimpleProgressShow
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : BaseActivity(), LoginContract.View {
    override lateinit var presenter: LoginContract.Presenter


    override fun loginSuccess() {
        showToast("login success")
        finish()
    }

    override fun loginFailed(error: String) {
        showToast("login failed: $error")
    }

    override fun registerSuccess() {
        showToast("register success")
        presenter.login(et_phone.text.toString(), et_psw.text.toString())
    }

    override fun registerFailed(error: String) {
        showToast("register failed: $error")
    }

    var simpleProgressShow: SimpleProgressShow? = null
    override fun showLoading() {
        var simpleProgressShow = simpleProgressShow ?: SimpleProgressShow(layout_root)
        simpleProgressShow.show()
    }

    override fun hiddenLoading() {
        simpleProgressShow?.hide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        PresenterLogin(this)
        registerListener()
        handleAction()
        btn_login.setOnClickListener {
            if (rb1.isChecked) {
                presenter.login(et_phone.text.toString(), et_psw.text.toString())
            } else {
                presenter.register(et_phone.text.toString(), et_psw.text.toString(), et_psw2.text.toString())
            }
        }
    }

    private fun handleAction() {
        rg.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb1 -> {
                    layout_psw2.visibility = View.GONE
                    btn_login.text = "登陆"
                }
                R.id.rb2 -> {
                    layout_psw2.visibility = View.VISIBLE
                    btn_login.text = "注册"
                }
            }
        }
    }

    var transY = 0f
    private fun registerListener() {
        colorBG = ColorDrawable(resources.getColor(R.color.colorPrimary))
        view_state.layoutParams.apply {
            height = NormalUtil.getStatusBarHeight(this@ActivityLogin)
            view_state.layoutParams = this
            transY = (-NormalUtil.dp2px(this@ActivityLogin, 255) + this.height)
        }
        SoftKeyBoardListener.setListener(this, object : SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                startUIAnimator(true)
            }

            override fun keyBoardHide(height: Int) {
                startUIAnimator(false)
            }
        })
    }
    var lastAnimator:ValueAnimator?=null
    private fun startUIAnimator(softInputShow: Boolean) {
        lastAnimator?.cancel()
        ValueAnimator.ofFloat(0f, 1f)
                .apply {
                    lastAnimator=this;
                    duration = 500
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            if (softInputShow) {
                                view_state.visibility = View.VISIBLE
                            }
                        }

                        override fun onAnimationCancel(animation: Animator?) {
                            super.onAnimationCancel(animation)
                        }
                        override fun onAnimationStart(animation: Animator?) {
                            super.onAnimationStart(animation)
                            if (!softInputShow) {
                                view_state.visibility = View.INVISIBLE
                            }
                        }
                    })
                    addUpdateListener {
                        var value = it.animatedValue as Float
                        if (!softInputShow) {
                            value = 1 - value
                        }
                        layout_root.translationY = transY * value
                        if (rb1.isChecked) {
                            updateState(rb1, rb2, 1 - value)
                        } else {
                            updateState(rb2, rb1, 1 - value)
                        }
                    }
                    start()
                }
    }

    lateinit var colorBG: ColorDrawable
    /**
     * @param vCheck 当前选中的那个，用来变色
     * @param vUncheck 没有选中的那个
     * @param factor 要隐藏的那个vuncheck的当前比重*/
    private fun updateState(vCheck: View, vUncheck: View, factor: Float) {
        //vcheck 修改背景色
        colorBG.alpha = ((1 - factor) * 255).toInt()
        vCheck.background = colorBG
        //vuncheck修改显示的比重
        var param = vUncheck.layoutParams as LinearLayout.LayoutParams
        param.weight = factor
        vUncheck.alpha = factor
        vUncheck.layoutParams = param;
        vUncheck.background = ColorDrawable(Color.TRANSPARENT)
    }
}