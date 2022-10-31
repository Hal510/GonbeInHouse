package com.example.gonbe_house.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gonbe_house.R
import com.example.gonbe_house.home
import com.example.gonbe_house.models.GonbeModel

class CategoryListAdapter(val categoryList: List<GonbeModel?>?, val clickListener: home): RecyclerView.Adapter<CategoryListAdapter.MyViewHolder>() {

    //ビューホルダー(1行分のレイアウト)を作成
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.category_list_row, parent, false)

        return MyViewHolder(view)
    }

    //上で作成したビューホルダーと表示される位置を受け取る
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(categoryList?.get(position))
        holder.itemView.setOnClickListener{
            clickListener.onItemClick(categoryList?.get(position)!!)
        }
    }

    //リストのサイズ分作成
    override fun getItemCount(): Int {
        return categoryList?.size!!
    }

    //サムネ画像とタイトルを呼び出し
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val thumbImage: ImageView       = view.findViewById(R.id.thumbImage)
        val tvCategoryName: TextView    = view.findViewById(R.id.tvCategoryName)

        fun bind(gonbeModel: GonbeModel?) {
            tvCategoryName.text    = gonbeModel?.name

            Glide.with(thumbImage)
                .load(gonbeModel?.image)
                .into(thumbImage)
        }
    }

    interface CategoryListClickListener {
        fun onItemClick(gonbeModel: GonbeModel)
    }

}