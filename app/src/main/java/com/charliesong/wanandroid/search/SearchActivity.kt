package com.charliesong.wanandroid.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.app.MyAPIManager
import com.charliesong.wanandroid.base.BaseActivity
import com.charliesong.wanandroid.base.BaseRvAdapter
import com.charliesong.wanandroid.base.BaseRvHolder
import com.charliesong.wanandroid.bean.SearchHotWord
import com.charliesong.wanandroid.retrofit.BaseFunction
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.inclue_simple_toolbar.*

class SearchActivity : BaseActivity() {
    lateinit var fragment:SearchResultFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setToolbarTitle("search")
        fragment= supportFragmentManager.findFragmentById(R.id.fragment_search) as SearchResultFragment
        fragment.getSwf()?.isEnabled=false
        getHotWords()
        hiddenResultView()
        handleEtSearchView()

    }
    var et_search:EditText?=null
    private fun handleEtSearchView(){
        et_search= vs_et.inflate() as EditText
        et_search?.apply {
            addTextChangedListener(object :TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    if(TextUtils.isEmpty(s.toString())){
                        hiddenResultView()
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

            setOnEditorActionListener { v, actionId, event ->

                if(actionId==EditorInfo.IME_ACTION_SEARCH){
                    if(v.editableText.toString().length==0){
                        return@setOnEditorActionListener false
                    }
                    startSearch(v)
                    return@setOnEditorActionListener  true
                }
                return@setOnEditorActionListener  false
            }
        }
    }
    private  fun startSearch(et_search:TextView){
        var old=et_search.editableText.toString().trim().replace(","," ")
        var arr=old.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var sb=StringBuffer()
        arr.forEach {
            sb.append(it+",")
        }
        var key=sb.substring(0,sb.length-1).toString()
        fragment.startSearch(key)
    }
    @SuppressLint("CheckResult")
    private fun getHotWords(){
        MyAPIManager.getAPI().getHotWords().compose(BaseFunction.handle())
                .subscribe({
                    initHotWords(it)
                },{

                })
    }
    private fun initHotWords(it:List<SearchHotWord>){
        rv_hot_words.apply {
            layoutManager=GridLayoutManager(this@SearchActivity,4)
            adapter=object :BaseRvAdapter<SearchHotWord>(it){
                override fun getLayoutID(viewType: Int): Int {
                    return R.layout.item_tree_text2
                }

                override fun onBindViewHolder(holder: BaseRvHolder, position: Int) {
                    holder.setText(R.id.tv_tree,getItemData(position).name)
                    holder.itemView.setOnClickListener {
                        et_search?.setText(getItemData(position).name)
                        fragment.startSearch(getItemData(position).name)
                    }
                }
            }
        }
    }

    fun showResultView(){
        rv_hot_words.visibility= View.GONE
        fragment.view?.visibility=View.VISIBLE
    }
    fun hiddenResultView(){
        rv_hot_words.visibility= View.VISIBLE
        fragment.view?.visibility=View.INVISIBLE
    }
}
