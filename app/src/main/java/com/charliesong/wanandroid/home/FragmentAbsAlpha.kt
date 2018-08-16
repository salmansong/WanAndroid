package com.charliesong.wanandroid.home

import android.annotation.SuppressLint
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import android.support.v7.util.DiffUtil
import com.charliesong.wanandroid.app.MyAPIManager
import com.charliesong.wanandroid.bean.ArticleBean
import com.charliesong.wanandroid.bean.BaseObjData
import com.charliesong.wanandroid.bean.DataBean
import com.charliesong.wanandroid.retrofit.BaseFunction
import io.reactivex.Observable

abstract class FragmentAbsAlpha:FragmentBaseAlpha<ArticleBean>(){

    @SuppressLint("CheckResult")
    override fun createDataSource(): DataSource<Int, ArticleBean> {
        return object : PageKeyedDataSource<Int, ArticleBean>(){

            override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ArticleBean>) {
                getCall(initKey).compose(BaseFunction.handle()).subscribe({
                    if(it.datas.size==0){
                    }
                    if(!isAdded){
                        return@subscribe
                    }
                    println("load initial ===========${it.datas.size}")
                    callback.onResult(it?.datas as MutableList<ArticleBean>?
                            ?: arrayListOf<ArticleBean>(),null,if(it.isOver)null else (initKey+1))
                    refreshComplete()
                },{
                    if(!isAdded){
                        return@subscribe
                    }
                    println("loadInitial===========onFailure==${it?.toString()}")
                    refreshComplete()
                })
            }

            override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleBean>) {
                getCall(params.key).compose(BaseFunction.handle()).subscribe({
                    if(!isAdded){
                        return@subscribe
                    }
                    callback.onResult(it?.datas as MutableList<ArticleBean>?
                            ?: arrayListOf<ArticleBean>(),if(it.isOver)null else (params.key+1))
                    refreshComplete()
                },{
                    if(!isAdded){
                        return@subscribe
                    }
                    println("loadAfter===========onFailure==${it?.toString()}")
                    refreshComplete()
                })

            }

            override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleBean>) {

            }
        }
    }

    override fun createDiffCallback(): DiffUtil.ItemCallback<ArticleBean> {
        return  object :DiffUtil.ItemCallback<ArticleBean>(){
            override fun areContentsTheSame(oldItem: ArticleBean?, newItem: ArticleBean?): Boolean {
                return  oldItem?.id==newItem?.id
            }

            override fun areItemsTheSame(oldItem: ArticleBean?, newItem: ArticleBean?): Boolean {
                return  oldItem?.publishTime==newItem?.publishTime
            }
        }
    }

    abstract fun getCall(key:Int): Observable<BaseObjData<DataBean<ArticleBean>>>

    @SuppressLint("CheckResult")
     fun addCollection(data:ArticleBean, position: Int){
        MyAPIManager.getAPI().addCollection(data.id).compose(BaseFunction.handle())
                .subscribe({
                    data.isCollect=!data.isCollect
                    adapterPL.notifyItemChanged(position)
                },{
                    println("=========收藏失败======${it.message}")
                })
    }

    @SuppressLint("CheckResult")
     fun cancelCollection(data:ArticleBean, position: Int){
        MyAPIManager.getAPI().cancelCollection(data.id).compose(BaseFunction.handle())
                .subscribe({
                    data.isCollect=!data.isCollect
                    adapterPL.notifyItemChanged(position)
                },{
                    println("=========取消收藏失败======${it.message}")
                })
    }
}