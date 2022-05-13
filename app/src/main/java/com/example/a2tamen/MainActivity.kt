package com.example.a2tamen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private val sliderhandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2=findViewById(R.id.viewpager_imageslider)
        val sliderItems:MutableList<slideritem> = ArrayList()
        sliderItems.add(slideritem(R.mipmap.pho))
        sliderItems.add(slideritem(R.mipmap.im))
        sliderItems.add(slideritem(R.mipmap.pht))
        viewPager2.adapter=slideradapter(sliderItems,viewPager2)
        viewPager2.clipToPadding=false
        viewPager2.clipChildren=false
        viewPager2.offscreenPageLimit=3
        viewPager2.getChildAt(0).overScrollMode= RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer=CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(30))
        compositePageTransformer.addTransformer{page,position ->
            val r=1- abs(position)
            page.scaleY= 0.85f + r * 0.25f
        }
        viewPager2.setPageTransformer(compositePageTransformer)
        viewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderhandler.removeCallbacks(sliderrunnable)
                sliderhandler.postDelayed(sliderrunnable,3000)
            }
        })
}
    private val sliderrunnable= Runnable {
        viewPager2.currentItem=viewPager2.currentItem+1
    }

    override fun onPause() {
        super.onPause()
        sliderhandler.postDelayed(sliderrunnable,3000)
    }

    override fun onResume() {
        super.onResume()
        sliderhandler.postDelayed(sliderrunnable,3000)
    }


}