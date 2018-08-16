package com.charliesong.wanandroid.home

import android.os.Bundle
import android.text.Html
import com.charliesong.wanandroid.base.ActivitySimpleWebView
import com.charliesong.wanandroid.bean.ArticleBean

class ArticleDetailActivity: ActivitySimpleWebView(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (intent.getSerializableExtra("data") as ArticleBean)?.apply {
            setToolbarTitle(Html.fromHtml(title))
            wv?.loadUrl(link)
        }

    }
}