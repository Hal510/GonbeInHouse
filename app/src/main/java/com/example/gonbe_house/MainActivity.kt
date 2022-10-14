package com.example.gonbe_house

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gonbe_house.Post.PostActivity
import com.example.gonbe_house.adapter.CategoryListAdapter
import com.example.gonbe_house.models.GonbeModel
import com.google.gson.Gson
import java.io.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle("注文を始める")

        val floatingActionButton : View =findViewById(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            val intent2 = Intent(this@MainActivity, PostActivity::class.java)
            startActivity(intent2)
        }

        val gonbeModel = getGonbeData()
        initRecyclerView(gonbeModel)
    }

    private fun initRecyclerView(categoryList: List<GonbeModel?>?) {
        val recyclerViewCategory = findViewById<RecyclerView>(R.id.recyclerViewCategory)
        recyclerViewCategory.layoutManager = LinearLayoutManager(this)
        val adapter = CategoryListAdapter(categoryList,this)
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
        } catch (e: Exception) {
        }
        val jsonStr: String = writer.toString()
        val gson = Gson()
        val gonbeModel =
            gson.fromJson<Array<GonbeModel>>(jsonStr, Array<GonbeModel>::class.java)
                .toList()

        return gonbeModel
    }

    fun onItemClick(gonbeModel: GonbeModel) {
        val intent = Intent(this@MainActivity, GonbeMenuActivity::class.java)
        intent.putExtra("GonbeModel",gonbeModel)
        startActivity(intent)
    }
    //値を受け渡す MainからGonbeMenuActivityへ
}