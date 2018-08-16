package com.charliesong.wanandroid.setting

import android.annotation.SuppressLint
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import android.content.Intent
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.app.MyAPIManager
import com.charliesong.wanandroid.base.BaseRvAdapter
import com.charliesong.wanandroid.base.BaseRvHolder
import com.charliesong.wanandroid.base.ItemDecorationGrid
import com.charliesong.wanandroid.bean.ArticleBean
import com.charliesong.wanandroid.bean.ThirdNavigation
import com.charliesong.wanandroid.home.ArticleDetailActivity
import com.charliesong.wanandroid.home.FragmentBaseAlpha
import com.charliesong.wanandroid.retrofit.BaseFunction

class FragmentHotNavigation:FragmentBaseAlpha<ThirdNavigation>(){
    override fun createDataSource(): DataSource<Int, ThirdNavigation> {
        return object :PageKeyedDataSource<Int,ThirdNavigation>(){
            @SuppressLint("CheckResult")
            override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ThirdNavigation>) {
                MyAPIManager.getAPI().getThirdNavigation().compose(BaseFunction.handle())
                        .subscribe({
                            if(!isAdded){
                                return@subscribe
                            }
                            callback.onResult(it,null,null)
                            refreshComplete()
                        },{
                            println("hot navigation load initial failed=================${it.message}")
                            if(!isAdded){
                                return@subscribe
                            }
                            refreshComplete()
                        })
            }

            override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ThirdNavigation>) {

            }

            override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ThirdNavigation>) {

            }
        }
    }

    override fun createDiffCallback(): DiffUtil.ItemCallback<ThirdNavigation> {
       return  object :DiffUtil.ItemCallback<ThirdNavigation>(){
           override fun areItemsTheSame(oldItem: ThirdNavigation?, newItem: ThirdNavigation?): Boolean {
               return  oldItem?.cid==newItem?.cid
           }

           override fun areContentsTheSame(oldItem: ThirdNavigation?, newItem: ThirdNavigation?): Boolean {
               return  TextUtils.equals(oldItem?.name,newItem?.name)
           }
       }
    }

    override fun getItemLayoutID(): Int {
        return R.layout.item_hot_navigation
    }

    var mSharedPool = RecyclerView.RecycledViewPool()
    override fun handleData(holder: BaseRvHolder, position: Int, data: ThirdNavigation?) {
            holder.apply {
                data!!.let {
                    setText(R.id.tv_title,data.name)
                }
                getView<RecyclerView>(R.id.rv_urls).also {
                    var oldAdapter=it.adapter

                    if(oldAdapter==null){
                        oldAdapter=object :BaseRvAdapter<ArticleBean>(data.articles){
                            override fun getLayoutID(viewType: Int): Int {
                                return R.layout.item_tree_text2
                            }

                            override fun onBindViewHolder(holder: BaseRvHolder, position: Int) {
                                with(getItemData(position)){
                                    holder.setText(R.id.tv_tree,this.title)
                                    holder.itemView.setOnClickListener {
                                        startActivity(Intent(activity,ArticleDetailActivity::class.java).putExtra("data",this))
                                    }
                                }

                            }
                        }
                        it.layoutManager=GridLayoutManager(activity,3).apply {
                            recycleChildrenOnDetach=true
                        }
                        it.addItemDecoration(ItemDecorationGrid())
                        it.adapter=oldAdapter
                        it.recycledViewPool=mSharedPool

                    }else{
                        (oldAdapter as BaseRvAdapter<ArticleBean>).initData(data!!.articles)
                    }
                }

            }
    }
}