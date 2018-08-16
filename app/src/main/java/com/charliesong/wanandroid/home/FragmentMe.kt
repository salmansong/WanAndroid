package com.charliesong.wanandroid.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.charliesong.wanandroid.*
import com.charliesong.wanandroid.base.BaseRvAdapter
import com.charliesong.wanandroid.base.BaseRvHolder
import com.charliesong.wanandroid.base.FragmentBase
import com.charliesong.wanandroid.base.ItemDecorationLine
import com.charliesong.wanandroid.bean.MeItem
import com.charliesong.wanandroid.collectioin.ActivityMyCollection
import com.charliesong.wanandroid.login.ActivityLogin
import com.charliesong.wanandroid.setting.ActivityWebsites
import com.charliesong.wanandroid.setting.HotNavigationActivity
import com.charliesong.wanandroid.setting.SettingActivity
import com.charliesong.wanandroid.setting.TestActivity
import kotlinx.android.synthetic.main.fragment_me.*

class FragmentMe : FragmentBase() {
    override fun getLayoutID(): Int {
        return R.layout.fragment_me
    }
    var items = arrayListOf<MeItem>()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        items.add(MeItem("我的收藏",R.mipmap.icon_red_star_collect,ActivityMyCollection::class.java))
        items.add(MeItem("常用网站",R.mipmap.icon_red_url,ActivityWebsites::class.java))
        items.add(MeItem("热门导航",R.mipmap.icon_red_url,HotNavigationActivity::class.java))
        items.add(MeItem("测试页面",R.mipmap.icon_red_star_collect,TestActivity::class.java))
        items.add(MeItem("测试百度",R.mipmap.icon_red_star_collect,ArticleDetailActivity::class.java))
//        repeat(21) {
//            items.add(MeItem("测试item$it", R.mipmap.setting,ActivityLogin::class.java))
//        }
        rv_items.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(ItemDecorationLine())
            adapter = object : BaseRvAdapter<MeItem>(items) {
                override fun getLayoutID(viewType: Int): Int {
                    return R.layout.item_me_item
                }

                override fun onBindViewHolder(holder: BaseRvHolder, position: Int) {
                    holder.apply {
                        var data = getItemData(position)
                        setText(R.id.tv_name, data.item)
                        setImageRes(R.id.iv_icon, data.icon)
                        itemView.setOnClickListener {
                            startActivity(Intent(activity,data.clazz).apply {
                                if(TextUtils.equals("测试百度",data.item)){
                                    this.putExtra("data",com.charliesong.wanandroid.bean.ArticleBean().apply {
                                        title="百度"
                                        link="http://www.wanandroid.com/index"
                                    })
                                }
                            })
                        }
                    }
                }
            }
        }
        colsSet()
        iv_setting.setOnClickListener {
            startActivity(Intent(activity, SettingActivity::class.java))
        }
        cols_layout.setOnClickListener {
            var name= NormalUtil.checkCookie(activity!!)
            if(TextUtils.isEmpty(name)){
                startActivity(Intent(activity,ActivityLogin::class.java))
            }
        }
    }

    private fun colsSet() {
        appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (cols_layout.bottom == toolbar2.bottom) {
                tv_title.visibility = View.VISIBLE
            } else {
                tv_title.visibility = View.GONE
            }
        }

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        println("setMenuVisibility=================$menuVisible")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        println("setUserVisibleHint===================$isVisibleToUser")
        if(isVisibleToUser){
            resumeUserState()
        }

    }

    override fun onResume() {
        super.onResume()
        resumeUserState()
    }

    private fun resumeUserState(){
        var name= NormalUtil.checkCookie(activity!!)
        tv_name.text=name
        tv_title.text=name
        if(TextUtils.isEmpty(name)){
            //没有登陆，或登陆过期了
        tv_name.text="未登录，点击登录或者注册"
        }
    }
}