package com.example.gonbe_house.startup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.gonbe_house.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //１）Viewの取得
        val btnStart :Button =findViewById(R.id.btnStart)

        //２）ボタンを押したら次の画面へ
        btnStart.setOnClickListener {
            val intent2 = Intent(this,PostActivity::class.java)
            startActivity(intent2)

        }
    }
}