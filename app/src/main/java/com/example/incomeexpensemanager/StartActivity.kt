package com.example.incomeexpensemanager

import Authentication.LoginActivity
import Authentication.SignUpActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

       /*lifecycleScope.launch(Dispatchers.IO){
           val time = measureTimeMillis {
              *//* val a = async { dowork1() }
               val b = async { dowork2() }

               Log.d("check","work 1::${a.await()}")
               Log.d("check","work 2::${b.await()}")*//*

               val a =  dowork1()
               Log.d("check","work 1::$a")

           }
           Log.d("check","finish work after::$time ms.")
           }
           */

//        val time = measureTimeMillis {
//           val a = dowork1()
//           val b = dowork2()
//            Log.d("check","work 1::$a")
//            Log.d("check","work 1::$b")
//        }
//        Log.d("check","finish work after::$time ms.")

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

   /* suspend fun dowork1(){
        delay(1000)
    }
      suspend fun dowork2(){
        delay(100)
    }*/

     fun dowork1(){
         lifecycleScope.launch(Dispatchers.IO){
             delay(1000)
         }

    }
     fun dowork2(){
         lifecycleScope.launch(Dispatchers.IO){
             delay(100)
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