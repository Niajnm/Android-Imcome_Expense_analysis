package com.example.incomeexpensemanager

import RoomDatabase.AppDatabase
import RoomDatabase.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_calender.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {
    var rcvDate = ""

    var recycleList = mutableListOf<User>()
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
        Tool_title.text = rcvDate

        toolbar_details.setNavigationOnClickListener {
            onBackPressed()
        }

        val balance = rcvIncome?.toInt()!! - rcvexpense?.toInt()!!
        textDayRemain_id.text = balance.toString()

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@DetailsActivity).userDao()
            var dataList: List<User>
            dataList = db.loadAllBbyDate(rcvDate!!)
            runOnUiThread {
               recycleList.addAll(dataList)
            }
        }
        loadRecycler()
    }

    fun loadRecycler() {
        val Adapter = AdapterDeatils(this@DetailsActivity, recycleList)
        detailsRecycler_id!!.adapter = Adapter
        detailsRecycler_id.layoutManager = LinearLayoutManager(this@DetailsActivity)
    }
    fun itemEdit() {
        startActivity(Intent(this, InsertDataActivity::class.java))
    }

    fun itemDelete(getID: Int, userPosition: Int, getMoney: Int, getFlag: String, getDate: String) {
        val db = AppDatabase.getDatabase(this@DetailsActivity).userDao()
        db.deleteByuid(getID)
       // recycleList.removeAt(userPosition)

        if(getFlag == "1"){
            db.DeleteDisplayIn(getMoney,getDate)
        }else{
            db.DeleteDisplayExpense(getMoney,getDate)
        }

        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
       // text()
    }
}