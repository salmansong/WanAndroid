package com.charliesong.wanandroid.home

import android.arch.lifecycle.Observer
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charliesong.wanandroid.base.BaseRvHolder
import com.charliesong.wanandroid.base.FragmentBase
import com.charliesong.wanandroid.base.ItemDecorationLine
import com.charliesong.wanandroid.R
import kotlinx.android.synthetic.main.simple_recyclerview.*

abstract class FragmentBaseAlpha<T> : FragmentBase() {
    override fun getLayoutID(): Int {
        return R.layout.simple_recyclerview
    }
    open fun getRv():RecyclerView{
        return  rv_simple
    }
    open fun getSwf():SwipeRefreshLayout{
        return swf_simple
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSwf()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getSwf().setOnRefreshListener {
           startRefreshData()
        }
        getRv().apply {
            layoutManager=LinearLayoutManager(activity)
            addItemDecoration(ItemDecorationLine())
            adapter=adapterPL

        }
       makePageList()
    }
    fun makePageList(){
        var liveData=LivePagedListBuilder<Int,T>(MyFactory(), createPageListConfig()).setInitialLoadKey(initKey).build()
        liveData.observe(this, Observer {
            adapterPL.submitList(it)
        })
    }
    fun startRefreshData(){
        adapterPL.submitList(null)
        makePageList()
    }
    fun refreshComplete(){
        getSwf().isRefreshing=false
    }
    open var initKey=0
    abstract fun createDataSource(): DataSource<Int, T>
    inner class MyFactory : DataSource.Factory<Int, T>() {
        override fun create(): DataSource<Int, T> {
            return createDataSource()
        }
    }
    fun createPageListConfig():PagedList.Config{
        return PagedList.Config.Builder().setPageSize(10)
                .setPrefetchDistance(5)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .build()
    }

    abstract fun createDiffCallback():DiffUtil.ItemCallback<T>
    open val adapterPL = object : PagedListAdapter<T, BaseRvHolder>(createDiffCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvHolder {
            return BaseRvHolder(LayoutInflater.from(parent.context).inflate(getItemLayoutID(), parent, false))
        }

        override fun onBindViewHolder(holder: BaseRvHolder, position: Int) {
            handleData(holder,position,getItem(position))
        }

    }

   abstract fun getItemLayoutID():Int
   abstract fun handleData(holder: BaseRvHolder, position: Int, data:T?)
}