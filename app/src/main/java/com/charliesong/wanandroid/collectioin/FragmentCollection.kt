package com.charliesong.wanandroid.collectioin

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import com.charliesong.wanandroid.base.BaseRvHolder
import com.charliesong.wanandroid.app.MyAPIManager
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.bean.ArticleBean
import com.charliesong.wanandroid.bean.BaseObjData
import com.charliesong.wanandroid.bean.DataBean
import com.charliesong.wanandroid.home.ArticleDetailActivity
import com.charliesong.wanandroid.home.FragmentAbsAlpha
import com.charliesong.wanandroid.retrofit.BaseFunction
import io.reactivex.Observable

class FragmentCollection : FragmentAbsAlpha() {
    override fun getCall(key: Int): Observable<BaseObjData<DataBean<ArticleBean>>> {
        return MyAPIManager.getAPI().getMyCollections(key)
    }

    override fun getItemLayoutID(): Int {
        return R.layout.item_collection_article
    }

    override fun handleData(holder: BaseRvHolder, position: Int, data: ArticleBean?) {
        data?.apply {
            holder.setText(R.id.tv_title, this.title)
            holder.setText(R.id.tv_author, author)
            holder.setText(R.id.tv_catalog2, chapterName)
            holder.setText(R.id.tv_time, niceDate)
            holder.itemView.setOnClickListener {
                startActivity(Intent(activity, ArticleDetailActivity::class.java).putExtra("data", data!!))
            }
            holder.getView<View>(R.id.tv_dis_like).setOnClickListener {
                cancelCollection2(data,position)
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun cancelCollection2(data:ArticleBean, position: Int){
        MyAPIManager.getAPI().cancelMyCollection(data.id,data.originId).compose(BaseFunction.handle())
                .subscribe({
                    adapterPL.notifyItemRemoved(position)
                },{

                })
    }
}