package com.example.a2tamen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    lateinit var aBtn:Button

    private lateinit var viewPager2: ViewPager2
    private val sliderhandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadlocate()
        setContentView(R.layout.activity_main)

        aBtn =findViewById(R.id.changelang)
        aBtn.setOnClickListener {
            showchangelang()

        }

        val secondactbut=findViewById<Button>(R.id.next)
        secondactbut.setOnClickListener {
            val Intent = Intent(this,MainActivity2::class.java)
            startActivity(Intent)
        }

        viewPager2=findViewById(R.id.viewpager_imageslider)
        val sliderItems:MutableList<slideritem> = ArrayList()
        sliderItems.add(slideritem(R.mipmap.pho))
        sliderItems.add(slideritem(R.mipmap.music))
        sliderItems.add(slideritem(R.mipmap.im))
        sliderItems.add(slideritem(R.mipmap.aut))
        sliderItems.add(slideritem(R.mipmap.auti))
        sliderItems.add(slideritem(R.mipmap.helpautism))
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
                sliderhandler.postDelayed(sliderrunnable,2000)
            }
        })
}
    private val sliderrunnable= Runnable {
        viewPager2.currentItem=viewPager2.currentItem+1
    }

    override fun onPause() {
        super.onPause()
        sliderhandler.postDelayed(sliderrunnable,2000)
    }

    override fun onResume() {
        super.onResume()
        sliderhandler.postDelayed(sliderrunnable,2000)
    }

   private fun showchangelang () {
        val listitems= arrayOf("عربي","English")
       val nbuilder =AlertDialog.Builder(this)
       nbuilder.setTitle("choose language")
       nbuilder.setSingleChoiceItems(listitems,-1) { dialog, which ->
           if (which == 0) {
                setlocate("ar")
               recreate()
           }
           else if(which == 1){
               setlocate("en")
               recreate()
           }
           dialog.dismiss()
       }
       val xDialog=nbuilder.create()
       xDialog.show()
    }
    private fun setlocate(lang: String){
        val local=Locale(lang)
        Locale.setDefault(local)
        val config= Configuration()
        config.locale= local
        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
        val editor=getSharedPreferences("setting",Context.MODE_PRIVATE).edit()
        editor.putString("my_lang",lang)
        editor.apply()
    }
    private fun loadlocate(){
        val sharedpreference=getSharedPreferences("setting",Activity.MODE_PRIVATE)
        val language = sharedpreference.getString( "my_lang" , "abc")

        language?.let { setlocate(it) }

    }
}