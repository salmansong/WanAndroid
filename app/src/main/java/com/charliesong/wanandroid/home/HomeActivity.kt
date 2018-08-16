package com.charliesong.wanandroid.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.charliesong.wanandroid.R
import com.charliesong.wanandroid.search.SearchActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        vp_fragment.adapter=object :FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(position: Int): Fragment {
                when(position){
                    0->{
                       return AlphaFragment()
                    }
                    1->{
                        return ByCatalogFragment()
                    }
                    2->{
                        return ProjectsFragment()
                    }
                    3->{
                        return FragmentMe()
                    }
                    else -> return Fragment()
                }
            }

            override fun getCount(): Int {
               return nav_bottom.menu.size()
            }

        }
        vp_fragment.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
               nav_bottom.selectedItemId =nav_bottom.menu.getItem(position).itemId
            }
        })
        vp_fragment.offscreenPageLimit=4
        nav_bottom.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.tab_alpha ->{
                   vp_fragment.currentItem=0
                }
                R.id.tab_catalog->{
                    vp_fragment.currentItem=1
                }
                R.id.tab_projects ->{
                    vp_fragment.currentItem=2
                }
                R.id.tab_me->{
                    vp_fragment.currentItem=3
                }
                else ->{
                    return@setOnNavigationItemSelectedListener false
                }
            }
            return@setOnNavigationItemSelectedListener  true
        }

        fab_search.setOnClickListener {
            startActivity(Intent(this@HomeActivity,SearchActivity::class.java))
        }
    }

    public fun AppCompatActivity.replaceFM(containerResid:Int,cla:Class<*>){
        var fragment=supportFragmentManager.findFragmentByTag(cla.name)?:cla.newInstance()
        supportFragmentManager.beginTransaction().replace(containerResid,fragment as Fragment,cla.name)
                .commitAllowingStateLoss()
    }
}
