package com.example.gonbe_house

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gonbe_house.adapter.YourOrderAdapter
import com.example.gonbe_house.models.GonbeModel
import kotlinx.android.synthetic.main.activity_your_order.*

class YourOrderActivity : AppCompatActivity() {

    var YourOrderAdapter: YourOrderAdapter? = null
    var isDeliveryOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_order)

        val gonbeModel: GonbeModel? = intent.getParcelableExtra("GonbeModel")
        val actionbar: ActionBar? = supportActionBar
        actionbar?.setTitle("注文内容確認")
        actionbar?.setDisplayHomeAsUpEnabled(true)

        buttonYourOrder.setOnClickListener {
            onOrderButtonCLick(gonbeModel)
        }
        isDeliveryOn = true
        calculateTotalAmount(gonbeModel)

        initRecyclerView(gonbeModel)
        calculateTotalAmount(gonbeModel)
    }

    private fun initRecyclerView(gonbeModel: GonbeModel?) {
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        YourOrderAdapter = YourOrderAdapter(gonbeModel?.menus)
        cartItemsRecyclerView.adapter =YourOrderAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun calculateTotalAmount(gonbeModel: GonbeModel?) {
        var TotalAmount = 0
        for(menu in gonbeModel?.menus!!) {
            TotalAmount += menu?.price!!  * menu.totalInCart
        }

        tvTotalAmount.text = String.format("%d", TotalAmount) + "円"
    }


    private fun onOrderButtonCLick(gonbeModel: GonbeModel?) {
        if(TextUtils.isEmpty(inputName.text.toString())) {
            inputName.error        =  "名前を入力してください"
            return
        } else if( TextUtils.isEmpty(inputPhoneNumber.text.toString())) {
            inputPhoneNumber.error =  "電話番号を入力してください"
            return
        } else if( TextUtils.isEmpty(inputReceiveTime.text.toString())) {
            inputReceiveTime.error =  "受け取り時間を入力してください"
            return
        }
        val intent = Intent(this@YourOrderActivity, SuccessOrderActivity::class.java)
        intent.putExtra("GonbeModel", gonbeModel)
        startActivityForResult(intent, 1000)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1000) {
            setResult(RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}
