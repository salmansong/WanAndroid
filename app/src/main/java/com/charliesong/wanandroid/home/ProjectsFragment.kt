package com.charliesong.wanandroid.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import com.charliesong.wanandroid.*
import com.charliesong.wanandroid.app.MyAPIManager
import com.charliesong.wanandroid.base.BaseRvAdapter
import com.charliesong.wanandroid.base.BaseRvHolder
import com.charliesong.wanandroid.bean.ArticleBean
import com.charliesong.wanandroid.bean.BaseObjData
import com.charliesong.wanandroid.bean.DataBean
import com.charliesong.wanandroid.bean.TreeBean
import com.charliesong.wanandroid.retrofit.BaseFunction
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_projects.*
import kotlinx.android.synthetic.main.inclue_simple_toolbar.*
import java.util.concurrent.CountDownLatch

class ProjectsFragment : FragmentAbsAlpha() {
    var cid = 0
    override var initKey = 1;
    override fun getCall(key: Int): Observable<BaseObjData<DataBean<ArticleBean>>> {
        if (countDownLatch.count > 0) {
            countDownLatch.await()
        }
        return MyAPIManager.getAPI().getProjects(key, cid)
    }

    override fun getItemLayoutID(): Int {
        return R.layout.item_project
    }

    override fun handleData(holder: BaseRvHolder, position: Int, data: ArticleBean?) {
        data?.apply {
            holder.setText(R.id.tv_title, title)
            holder.setText(R.id.tv_des, desc)
            holder.setText(R.id.tv_author, author)
            holder.setText(R.id.tv_time, niceDate)
            holder.setImageUrl(R.id.iv_cover, envelopePic)
            holder.getView<View>(R.id.layout_click).setOnClickListener {
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
        return R.layout.fragment_projects
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_close_open, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
//            supportActionBar?.title = "project"
        }
        getRv().apply {
            var spanCount=if(Configuration.ORIENTATION_PORTRAIT==resources.configuration.orientation) 2 else 3
            layoutManager = GridLayoutManager(activity, spanCount, LinearLayoutManager.VERTICAL, false)
            if (itemDecorationCount > 0) {
                removeItemDecorationAt(0)
            }
            addItemDecoration(object :RecyclerView.ItemDecoration(){
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                    var spanCount=(parent.layoutManager as GridLayoutManager).spanCount
                    var position=parent.getChildAdapterPosition(view)
                    outRect.left=10
                    outRect.right=10
                    outRect.bottom=20
                    when(position%spanCount){
                        0->{
                          outRect.left=20
                        }
                        spanCount-1 ->{
                            outRect.right=20
                        }
                        else ->{

                        }
                    }
                }
            })
        }
        handelRvTree()
        getTreeData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_choose -> {
                if (dl_layout.isDrawerOpen(Gravity.RIGHT)) {
                    dl_layout.closeDrawer(Gravity.RIGHT)
                } else {
                    dl_layout.openDrawer(Gravity.RIGHT)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    var countDownLatch = CountDownLatch(1)
    private fun handelRvTree() {
        rv_type.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = object : BaseRvAdapter<TreeBean>() {
                override fun getLayoutID(viewType: Int): Int {
                    return R.layout.item_tree_text2
                }

                var checkedIndex = 0
                override fun onBindViewHolder(holder: BaseRvHolder, position: Int) {
                    holder.apply {
                        itemView.setBackgroundColor(if (checkedIndex == position) Color.BLUE else Color.WHITE)
                        setText(R.id.tv_tree, getItemData(position).name)
                        itemView.setOnClickListener {
                            dl_layout.closeDrawer(Gravity.RIGHT)
                            if (checkedIndex != position) {
                                it.setBackgroundColor(Color.BLUE)
                                notifyItemChanged(checkedIndex)
                                checkedIndex = position
                                changeCid(getItemData(position))
                                getSwf()?.isRefreshing=true
                                startRefreshData()
                            }
                        }
                    }
                }

            }
        }
    }

    @SuppressLint("CheckResult")
    private fun getTreeData() {
        MyAPIManager.getAPI().getAllTreeChildren()
                .compose(BaseFunction.handle())
                .subscribe({
                    it?.apply {
                        (rv_type.adapter as BaseRvAdapter<TreeBean>).initData(this)
                        changeCid(this.get(0))
                        countDownLatch.countDown()
                    }
                },{

                })
    }
    private fun changeCid(treeBean: TreeBean){
        cid = treeBean.id
        (activity as AppCompatActivity).supportActionBar?.title = treeBean.name
    }
    override fun onDestroy() {
        super.onDestroy()
        if (countDownLatch.count > 0) {
            countDownLatch.countDown()
        }
    }
}