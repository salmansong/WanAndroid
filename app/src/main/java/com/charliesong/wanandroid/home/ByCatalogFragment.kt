package com.charliesong.wanandroid.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.charliesong.wanandroid.base.BaseRvAdapter
import com.charliesong.wanandroid.base.BaseRvHolder
import com.charliesong.wanandroid.app.MyAPIManager
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.bean.*
import com.charliesong.wanandroid.retrofit.BaseFunction
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_navigation.*
import java.util.concurrent.CountDownLatch

class ByCatalogFragment:FragmentAbsAlpha(){
    var countDownLatch=CountDownLatch(1)
    override fun getCall(key: Int): Observable<BaseObjData<DataBean<ArticleBean>>> {
        if(countDownLatch.count>0){
            countDownLatch.await()
        }
        return MyAPIManager.getAPI().getTreeArticle(key,queryID)
    }
    var queryID:Int=294
    override fun getItemLayoutID(): Int {
      return  R.layout.item_alpha_article
    }

    override fun getSwf(): SwipeRefreshLayout {
        return swf_navigation
    }

    override fun getRv(): RecyclerView {
        return  rv
    }
    override fun handleData(holder: BaseRvHolder, position: Int, data: ArticleBean?) {
        data?.apply {
            holder.setText(R.id.tv_title, this.title)
            holder.setText(R.id.tv_author, author)
            holder.setText(R.id.tv_catalog1, superChapterName)
            holder.setText(R.id.tv_catalog2, chapterName)
            holder.setText(R.id.tv_time, niceDate)
            holder.setVisibleOrGone(R.id.tv_new_tag, isFresh)
            holder.itemView.setOnClickListener {
                startActivity(Intent(activity, ArticleDetailActivity::class.java).putExtra("data", data))
            }
            holder.getView<ImageView>(R.id.iv_like).apply {
                setColorFilter(if (isCollect) Color.RED else Color.GRAY)
                setOnClickListener {
                    if(isCollect){
                        cancelCollection(data,position)
                    }else{
                        addCollection(data,position)
                    }
                }
            }
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_navigation
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            getTree()
    }
    @SuppressLint("CheckResult")
    private fun getTree(){
        MyAPIManager.getAPI().getTree()
                .compose(BaseFunction.handle())
                .subscribe({
                    try {
                        queryID=it.get(0).children[0].id
                        countDownLatch.countDown()
                        setDataForTree(it)
                    }catch (e:Exception){
                        println("getTree exception==========${e.message}")
                    }
                },{

                })
    }
    var select1=0
    var select2=0
    private fun setDataForTree(baseData:List<TreeBean>){
        rv_one.apply {
            layoutManager=GridLayoutManager(activity,2,LinearLayoutManager.HORIZONTAL,false)
            adapter=object : BaseRvAdapter<TreeBean>(baseData){
                override fun getLayoutID(viewType: Int): Int {
                    return R.layout.item_tree_text
                }

                override fun getItemCount(): Int {
                    return super.getItemCount()
                }
                override fun onBindViewHolder(holder: BaseRvHolder, position: Int) {
                    getItemData(position).apply {
                        holder.getView<TextView>(R.id.tv_tree).apply{
                            text=name
                            setBackgroundResource(if(position==select1) R.drawable.shape_c5_red_stroke else android.R.drawable.list_selector_background)

                            setOnClickListener {
                                if(position!=select1){
                                    notifyItemChanged(select1)
                                    select1=position
                                    setBackgroundResource(if(position==select1) R.drawable.shape_c5_red_stroke else android.R.drawable.list_selector_background)
                                    select2=0
                                    getSecondRV().initData(children)
                                    queryID=children[0].id
                                    startRefreshData()
                                }
                            }
                        }
                    }

                }
            }
        }
        rv_two.apply {
            layoutManager=GridLayoutManager(activity,1,LinearLayoutManager.HORIZONTAL,false)
//            addItemDecoration(object :RecyclerView.ItemDecoration(){
//                override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//                    super.getItemOffsets(outRect, view, parent, state)
//                    outRect.bottom=10
//                }
//            })
            adapter=object : BaseRvAdapter<TreeBean>(baseData[0].children){
                override fun getLayoutID(viewType: Int): Int {
                    return R.layout.item_tree_text
                }

                override fun onBindViewHolder(holder: BaseRvHolder, position: Int) {
                        holder.getView<TextView>(R.id.tv_tree).apply{
                            text=getItemData(position).name
                            setBackgroundResource(if(position==select2) R.drawable.shape_c5_red_stroke else android.R.drawable.list_selector_background)

                            setOnClickListener {
                                if(position!=select2){
                                    notifyItemChanged(select2)
                                    select2=position
                                    setBackgroundResource(if(position==select2) R.drawable.shape_c5_red_stroke else android.R.drawable.list_selector_background)
                                    queryID=getItemData(position).id
                                    startRefreshData()
                                }
                            }
                        }
                }
            }
        }
    }

    private fun getSecondRV(): BaseRvAdapter<TreeBean> {
        return rv_two.adapter as BaseRvAdapter<TreeBean>
    }
    override fun onDestroy() {
        super.onDestroy()
        if(countDownLatch.count>0)
            countDownLatch.countDown()
    }


}