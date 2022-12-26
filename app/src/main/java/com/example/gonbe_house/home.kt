package com.example.gonbe_house

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gonbe_house.Post.PostActivity
import com.example.gonbe_house.Post.post
//import com.example.gonbe_house.Post.PostActivity
import com.example.gonbe_house.adapter.CategoryListAdapter
import com.example.gonbe_house.models.GonbeModel
import com.google.gson.Gson
import java.io.*

class home : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "注文を始める"

        val floatingActionButton : View =view.findViewById(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            val intent2 = Intent(context, PostActivity::class.java)
            startActivity(intent2)
        }

        val gonbeModel = getGonbeData()
        initRecyclerView(view, gonbeModel)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun initRecyclerView(view: View, categoryList: List<GonbeModel?>?) {
        val recyclerViewCategory = view.findViewById<RecyclerView>(R.id.recyclerViewCategory)
        recyclerViewCategory.layoutManager = LinearLayoutManager(view.context)
        val adapter = CategoryListAdapter(categoryList,this@home)
        recyclerViewCategory.adapter =adapter
    }
    //https://somachob.com/android-recyclerview/
    //１つ１つのデータをどのような並びで表示させるかを決めるのがレイアウトマネージャー
    //  LinearLayoutManager       ：リストで表示する
    //  GridLayoutManager         ：それぞれのデータの高さ（幅）がそろった格子状に表示する
    //  StaggeredGridLayoutManager：格子状に表示するが、高さ（幅）がそれぞれのデータで異なる。


    private fun getGonbeData(): List<GonbeModel?>? {
        val inputStream: InputStream = resources.openRawResource(R.raw.takeout_menu)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        } catch (_: Exception) {
        }
        val jsonStr: String = writer.toString()
        val gson = Gson()
        val gonbeModel =
            gson.fromJson<Array<GonbeModel>>(jsonStr, Array<GonbeModel>::class.java)
                .toList()

        return gonbeModel
    }

    fun onItemClick(gonbeModel: GonbeModel) {
        val intent = Intent(context, GonbeMenuActivity::class.java)
        intent.putExtra("GonbeModel",gonbeModel)
        startActivity(intent)
    }
    //値を受け渡す MainからGonbeMenuActivityへ
}