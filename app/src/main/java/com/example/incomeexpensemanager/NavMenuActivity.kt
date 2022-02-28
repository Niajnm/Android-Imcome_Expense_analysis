package com.example.incomeexpensemanager

import Fragment.AnalyzerFragment
import Fragment.NavDrawer.ProfileFragment
import Fragment.NavDrawer.SettingsFragment
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.os.Build
import android.view.Window

import android.view.WindowManager

class NavMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_menu)
        supportActionBar?.hide()
        setStatusBarTransparent()

//        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//        window.statusBarColor = Color.TRANSPARENT
        val intent = intent
        val frag = intent.getStringExtra("fragKey")

        if (frag == "profile") {
            val bpfragment = ProfileFragment()
            supportFragmentManager.beginTransaction().replace(R.id.frag_container, bpfragment)
                .commit()
        }else if(frag=="settings"){
            val bpfragment = SettingsFragment()
            supportFragmentManager.beginTransaction().replace(R.id.frag_container, bpfragment)
                .commit()
        }
    }

    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val decorView: View = window.getDecorView()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            window.setStatusBarColor(Color.TRANSPARENT)
        }
    }
}