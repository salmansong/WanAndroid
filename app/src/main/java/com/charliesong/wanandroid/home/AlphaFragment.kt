package com.charliesong.wanandroid.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.View
import com.charliesong.wanandroid.*
import com.charliesong.wanandroid.app.MyAPIManager
import com.charliesong.wanandroid.base.BaseRvAdapter
import com.charliesong.wanandroid.base.BaseRvHolder
import com.charliesong.wanandroid.base.FragmentBase
import com.charliesong.wanandroid.bean.ArticleBean
import com.charliesong.wanandroid.bean.BannerAlphaBean
import com.charliesong.wanandroid.retrofit.BaseFunction
import kotlinx.android.synthetic.main.fragment_alpha.*

class AlphaFragment : FragmentBase() {
    override fun getLayoutID(): Int {
        return R.layout.fragment_alpha
    }

    var titles = arrayListOf<String>("最新文章", "最新工程")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vp.adapter = object : FragmentPagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment {
                when (position) {
                    0 -> {
                        return AlphaArticleFragment()
                    }
                    1 -> {
                        return AlphaProjectsFragment()
                    }
                    else -> return Fragment()
                }
            }

            override fun getCount(): Int {
                return titles.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titles[position]
            }
        }

        tab_layout.setupWithViewPager(vp)
        getBannerData()
    }

    private fun click() {
        iv_next1.visibility = View.VISIBLE
        iv_next2.visibility = View.VISIBLE
        iv_next1.setOnClickListener {
            rv_left.apply {
                var layoutManager = layoutManager as LinearLayoutManager
                smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() + 1)
            }
        }
        iv_next2.setOnClickListener {
            rv_right.apply {
                var layoutManager = layoutManager as LinearLayoutManager
                smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() + 2)
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun getBannerData() {
        MyAPIManager.getAPI().getBanner()
                .compose(BaseFunction.handle())
                .subscribe({
                    handleBanner(it)
                }, {

                })

    }

    private fun handleBanner(list: List<BannerAlphaBean>) {
        var lists1 = arrayListOf<BannerAlphaBean>()
        var lists2 = arrayListOf<BannerAlphaBean>()
        list.forEach {
            if (it.type == 0) {
                lists1.add(it)
            } else if (it.type == 1) {
                lists2.add(it)
            }
        }
        rv_left.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = object : BaseRvAdapter<BannerAlphaBean>(lists1) {
                override fun getLayoutID(viewType: Int): Int {
                    return R.layout.item_simple_image
                }

                override fun onBindViewHolder(holder: BaseRvHolder, position: Int) {
                    var index = position % datas.size
                    with(getItemData(index)) {
                        holder.setImageUrl(R.id.iv_simple, imagePath)
                        holder.setText(R.id.tv_simple, this.title)
                        holder.setVisibleOrGone(R.id.tv_simple, true)
                        holder.itemView.setOnClickListener {
                            startActivity(Intent(activity, ArticleDetailActivity::class.java).putExtra("data", ArticleBean().also {
                                it.title = this.title
                                it.link = url
                            }))
                        }
                    }

                }

                override fun getItemCount(): Int {
                    return 100000
                }
            }
        }
        rv_left.scrollToPosition(lists1.size * 1000)
        PagerSnapHelper().attachToRecyclerView(rv_left)
        if (lists2.size == 0) {
            rv_right.visibility = View.GONE
            return
        }
        rv_right.visibility = View.VISIBLE
        rv_right.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = object : BaseRvAdapter<BannerAlphaBean>(lists2) {
                override fun getLayoutID(viewType: Int): Int {
                    return R.layout.item_simple_image
                }

                override fun onBindViewHolder(holder: BaseRvHolder, position: Int) {
                    holder.itemView.layoutParams.height = coslayout.height / 2
                    var index = position % datas.size
                    with(getItemData(index)) {
                        holder.setImageUrl(R.id.iv_simple, imagePath)
                        holder.setText(R.id.tv_simple, this.title)
                        holder.itemView.setOnClickListener {
                            startActivity(Intent(activity, ArticleDetailActivity::class.java).putExtra("data", ArticleBean().also {
                                it.title = this.title
                                it.link = url
                            }))
                        }
                    }

                }

                override fun getItemCount(): Int {
                    return 100000
                }
            }
        }
        rv_right.scrollToPosition(lists2.size * 1000)
        click()
    }
}