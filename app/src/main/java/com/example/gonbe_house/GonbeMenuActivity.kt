package com.example.gonbe_house

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gonbe_house.R
import com.example.gonbe_house.adapter.MenuListAdapter
import com.example.gonbe_house.models.Menus
import com.example.gonbe_house.models.GonbeModel
import kotlinx.android.synthetic.main.activity_gonbe_menu.*

class GonbeMenuActivity : AppCompatActivity(), MenuListAdapter.MenuListClickListener {

    private var itemsInTheCartList: MutableList<Menus?>? = null
    private var totalItemInCartCount = 0
    private var menuList: List<Menus?>? = null
    private var menuListAdapter: MenuListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gonbe_menu)

        val gonbeModel = intent?.getParcelableExtra<GonbeModel>("GonbeModel")

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(gonbeModel?.name)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        menuList = gonbeModel?.menus

        initRecyclerView(menuList)

        checkoutButton.setOnClickListener {
            if(itemsInTheCartList != null && itemsInTheCartList!!.size <= 0) {
                Toast.makeText(this@GonbeMenuActivity, "カートにアイテムを追加してください", Toast.LENGTH_LONG).show()
            }
            else {
                gonbeModel?.menus = itemsInTheCartList
                val intent = Intent(this@GonbeMenuActivity, YourOrderActivity::class.java)
                intent.putExtra("GonbeModel", gonbeModel)
                startActivityForResult(intent, 1000)
            }
        }

    }
    private fun initRecyclerView(menus: List<Menus?>?) {
        //１行２メニューずつ表示
        menuRecyclerView.layoutManager = GridLayoutManager(this, 3)
        menuListAdapter = MenuListAdapter(menus,this)
        menuRecyclerView.adapter =menuListAdapter
    }

    override fun addToCartClickListener(menu: Menus) {
        if(itemsInTheCartList == null) {
            itemsInTheCartList = ArrayList()
        }
        itemsInTheCartList?.add(menu)
        totalItemInCartCount = 0
        for(menu in itemsInTheCartList!!) {
            totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
        }
        checkoutButton.text = "(" + totalItemInCartCount +")つのアイテムを注文"

    }

    override fun updateCartClickListener(menu: Menus) {
        val index = itemsInTheCartList!!.indexOf(menu)
        itemsInTheCartList?.removeAt(index)
        itemsInTheCartList?.add(menu)
        totalItemInCartCount = 0
        for(menu in itemsInTheCartList!!) {
            totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
        }
        checkoutButton.text = "(" + totalItemInCartCount +")つのアイテムを注文"
    }

    override fun removeFromCartClickListener(menu: Menus) {
        if(itemsInTheCartList!!.contains(menu)) {
            itemsInTheCartList?.remove(menu)
            totalItemInCartCount = 0
            for(menu in itemsInTheCartList!!) {
                totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
            }
            checkoutButton.text = "(" + totalItemInCartCount +")つのアイテムを注文"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1000 && resultCode == RESULT_OK) {
            finish()
        }
    }
}