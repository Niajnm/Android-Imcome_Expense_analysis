package com.example.incomeexpensemanager

import Authentication.LoginActivity
import Authentication.SignUpActivity
import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        supportActionBar?.hide()
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        val mauth = Firebase.auth
        val currentUser = mauth.currentUser

        if (currentUser!= null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        JoinButton_id.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        LoginButton_id.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        layout_google.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }


    override fun onBackPressed() {
        var builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder!!.setTitle("Exit !")
        builder!!.setMessage("Do you want to exit ?")
        builder!!.setIcon(R.drawable.ic_baseline_info_24)
        builder!!.setPositiveButton("Yes") { dialog, which -> finish() }
        builder!!.setNegativeButton("No") { dialog, which ->
            Toast.makeText(
                this,
                "Back to main menu !",
                Toast.LENGTH_SHORT
            ).show()
        }
        val alertDialog: androidx.appcompat.app.AlertDialog = builder!!.create()
        alertDialog.show()
    }

}