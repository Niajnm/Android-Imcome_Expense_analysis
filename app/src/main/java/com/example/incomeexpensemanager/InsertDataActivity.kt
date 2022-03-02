package com.example.incomeexpensemanager

import Fragment.CalenderFragment
import Fragment.ExpenseFragment
import Fragment.IncomeFragment
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.android.synthetic.main.activity_insert_data.*

class InsertDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_data)
        supportActionBar?.hide()
        setStatusBarTransparent()

//        switch_id.setOnClickListener {
//            if (switch_id.isChecked) {
//                val exFragment = ExpenseFragment()
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.insert_fragment_container, exFragment).commit()
//            } else {
//                val  inFragment = IncomeFragment()
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.insert_fragment_container, inFragment).commit()
//            }
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
    }
    private fun setStatusBarTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val decorView: View = window.getDecorView()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


                val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView)
                windowInsetsController?.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())

            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            window.setStatusBarColor(Color.TRANSPARENT)
        }
    }
}