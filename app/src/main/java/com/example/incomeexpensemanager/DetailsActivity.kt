package com.example.incomeexpensemanager

import RoomDatabase.AppDatabase
import RoomDatabase.User
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.launch

class DetailsActivity: AppCompatActivity() {
    var rcvDate=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intent = intent
         rcvDate = intent.getStringExtra("dateKey").toString()
        val rcvIncome = intent.getStringExtra("incomeKey")
        val rcvexpense = intent.getStringExtra("expenseKey")
        textDayIncome_id.text = rcvIncome
        textDayExpense_id.text = rcvexpense

        supportActionBar?.hide()
        Tool_title.text=rcvDate

        toolbar_details.setNavigationOnClickListener{
            onBackPressed()
        }

        val balance = rcvIncome?.toInt()!! - rcvexpense?.toInt()!!
        textDayRemain_id.text = balance.toString()

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@DetailsActivity).userDao()
            var dataList:List<User>
            dataList = db.loadAllBbyDate(rcvDate!!)

            val Adapter = AdapterDeatils(this@DetailsActivity, dataList)
            detailsRecycler_id!!.adapter = Adapter
            detailsRecycler_id.layoutManager = LinearLayoutManager(this@DetailsActivity)
        }
    }
    fun text(){


    }
}