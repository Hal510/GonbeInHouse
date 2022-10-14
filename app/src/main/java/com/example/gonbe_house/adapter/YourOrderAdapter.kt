package com.example.gonbe_house.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gonbe_house.R
import com.example.gonbe_house.models.Menus

class YourOrderAdapter(val menuList: List<Menus?>?): RecyclerView.Adapter<YourOrderAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.yourorder_list_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(menuList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if(menuList == null) 0  else menuList.size
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val thumbImage: ImageView = view.findViewById(R.id.thumbImage)
        val menuName: TextView = view.findViewById(R.id.menuName)
        val menuQty: TextView = view.findViewById(R.id.menuQty)
        val menuPrice: TextView = view.findViewById(R.id.menuPrice)

        fun bind(menu: Menus) {
            menuName.text  = menu?.name!!
            menuQty.text   = "数量 :" + menu?.totalInCart
            menuPrice.text = "合計価格 :" + String.format("%d円",menu?.price * menu.totalInCart)


            Glide.with(thumbImage)
                .load(menu?.url)
                .into(thumbImage)
        }
    }
}