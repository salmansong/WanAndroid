package com.charliesong.wanandroid.home

import android.content.Intent
import android.graphics.Color
import android.widget.ImageView
import com.charliesong.wanandroid.base.BaseRvHolder
import com.charliesong.wanandroid.app.MyAPIManager
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.bean.ArticleBean
import com.charliesong.wanandroid.bean.BaseObjData
import com.charliesong.wanandroid.bean.DataBean
import io.reactivex.Observable

class AlphaProjectsFragment : FragmentAbsAlpha() {
    override fun getCall(key: Int): Observable<BaseObjData<DataBean<ArticleBean>>> {
        return MyAPIManager.getAPI().getProjects(key, 294)
    }

    override fun getItemLayoutID(): Int {
        return R.layout.item_alpha_project
    }

    override fun handleData(holder: BaseRvHolder, position: Int, data: ArticleBean?) {
        data?.apply {
            holder.setText(R.id.tv_title, title)
            holder.setText(R.id.tv_des, desc)
            holder.setText(R.id.tv_author, author)
            holder.setText(R.id.tv_time, niceDate)
            holder.setImageUrl(R.id.iv_cover, envelopePic)
            holder.itemView.setOnClickListener {
                startActivity(Intent(activity, ArticleDetailActivity::class.java).putExtra("data", data!!))
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

    override var initKey = 1;
}