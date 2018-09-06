package com.charliesong.wanandroid.setting

import android.annotation.SuppressLint
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.GridLayoutManager
import com.charliesong.wanandroid.base.BaseRvHolder
import com.charliesong.wanandroid.base.ItemDecorationLine
import com.charliesong.wanandroid.app.MyAPIManager
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.base.ItemDecorationGrid
import com.charliesong.wanandroid.bean.ArticleBean
import com.charliesong.wanandroid.bean.WebsiteUrl
import com.charliesong.wanandroid.home.ArticleDetailActivity
import com.charliesong.wanandroid.home.FragmentBaseAlpha
import com.charliesong.wanandroid.retrofit.BaseFunction
import com.charliesong.wanandroid.widget.CustomLayoutManager
import com.charliesong.wanandroid.widget.SpaceItemDecoration

class FragmentWebsites:FragmentBaseAlpha<WebsiteUrl>(){
    override fun createDataSource(): DataSource<Int, WebsiteUrl> {
            return object :PageKeyedDataSource<Int,WebsiteUrl>(){
                @SuppressLint("CheckResult")
                override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, WebsiteUrl>) {
                    MyAPIManager.getAPI().getWebsiteUrls().compose(BaseFunction.handle())
                            .subscribe({
                                if(!isAdded){
                                    return@subscribe
                                }

                                callback.onResult(it.subList(0,it.size-1),null,1)
                                refreshComplete()
                            },{
                                println("FragmentWebsites=========loadInitial failed=========${it.message}")
                                if(!isAdded){
                                    return@subscribe
                                }
                                refreshComplete()
                            })
                }

                override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, WebsiteUrl>) {
                    MyAPIManager.getAPI().getWebsiteUrls().compose(BaseFunction.handle())
                            .subscribe({
                                if(!isAdded){
                                    return@subscribe
                                }
                                callback.onResult(it,null)
                                refreshComplete()
                            },{
                                println("FragmentWebsites=========loadInitial failed=========${it.message}")
                                if(!isAdded){
                                    return@subscribe
                                }
                                refreshComplete()
                            })
                }

                override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, WebsiteUrl>) {

                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getRv().apply {
//            layoutManager=GridLayoutManager(activity,3).apply {
//                spanSizeLookup=lookup
//            }
            layoutManager=CustomLayoutManager()
            repeat(itemDecorationCount){
                removeItemDecorationAt(0)
            }
            addItemDecoration( SpaceItemDecoration(10));
//            addItemDecoration(ItemDecorationGrid())
        }
    }

    override fun createDiffCallback(): DiffUtil.ItemCallback<WebsiteUrl> {
        return object :DiffUtil.ItemCallback<WebsiteUrl>(){
            override fun areItemsTheSame(oldItem: WebsiteUrl?, newItem: WebsiteUrl?): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: WebsiteUrl?, newItem: WebsiteUrl?): Boolean {
                return false
            }
        }
    }

    override fun getItemLayoutID(): Int {
        return R.layout.item_website_container
    }
    val colors= arrayListOf(Color.RED,Color.BLUE,Color.GREEN)
    override fun handleData(holder: BaseRvHolder, position: Int, data: WebsiteUrl?) {
        data?.apply {

            holder.itemView.apply {
                setBackgroundColor(colors[position%colors.size])

                holder.setText(R.id.tv_tree,name+"${position}")
            }
            holder.itemView.setOnClickListener {
                var data=ArticleBean()
                data.title=name
                data.link=link
                startActivity(Intent(activity,ArticleDetailActivity::class.java).putExtra("data",data))
            }
        }


    }
}