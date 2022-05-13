package com.example.a2tamen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.makeramen.roundedimageview.RoundedImageView

class slideradapter internal constructor(
    sliderItems:MutableList<slideritem>,
    viewPager2: ViewPager2

): RecyclerView.Adapter<slideradapter.sliderviewholder>()
{
    private val sliderItems:List<slideritem>
    private val viewPager2:ViewPager2
    init {
        this.sliderItems = sliderItems
        this.viewPager2=viewPager2
    }
    class sliderviewholder(itemView:View):RecyclerView.ViewHolder(itemView){
        private val imageView:RoundedImageView=itemView.findViewById(R.id.imageslide)
        fun image (slideritem: slideritem){
            imageView.setImageResource(slideritem.image)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): sliderviewholder {
       return sliderviewholder(LayoutInflater.from(parent.context).inflate(R.layout.slide_item_container,parent,false))
    }

    override fun onBindViewHolder(holder: sliderviewholder, position: Int) {
        holder.image(sliderItems[position])
        if(position ==sliderItems.size-2)
            viewPager2.post(runnable)
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }
    private val runnable= Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }
}