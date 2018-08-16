package com.charliesong.wanandroid.search

import android.text.Html
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.app.MyAPIManager
import com.charliesong.wanandroid.base.BaseRvHolder
import com.charliesong.wanandroid.bean.ArticleBean
import com.charliesong.wanandroid.bean.BaseObjData
import com.charliesong.wanandroid.bean.DataBean
import com.charliesong.wanandroid.home.AlphaArticleFragment
import io.reactivex.Observable
import java.util.concurrent.CountDownLatch

class SearchResultFragment:AlphaArticleFragment(){
    var keyword=""
    override fun getCall(key: Int): Observable<BaseObjData<DataBean<ArticleBean>>> {
        if(cd.count>0){
            cd.await()
        }
            return MyAPIManager.getAPI().getSearchResultByWords(key,keyword)
    }

    var cd=CountDownLatch(1)
    override fun onDestroy() {
        super.onDestroy()
        if(cd.count>0){
            cd.countDown()
        }
    }
    fun startSearch(words:String){
        keyword=words
        println("key==============${keyword}")
        if(cd.count>0){
            cd.countDown()
        }
        startRefreshData()
        (activity as SearchActivity).showResultView()
    }

    override fun handleData(holder: BaseRvHolder, position: Int, data: ArticleBean?) {
        super.handleData(holder, position, data)
        //"<font color=\"#ff0000\"> 第一个 </font>aaaa"
        var change=data?.title?.replace("<em class=\'highlight\'>","<font color=\'#ff0000\'>")?.replace("</em>","</font>")
        data?.title=change
        holder.setText(R.id.tv_title, Html.fromHtml(change))
    }
}