package com.example.incomeexpensemanager

import Fragment.CalenderFragment
import Fragment.ExpenseFragment
import Fragment.IncomeFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_insert_data.*

class InsertDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_data)
        supportActionBar?.hide()

        switch_id.setOnClickListener {

            if (switch_id.isChecked) {
                val exFragment = ExpenseFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.insert_fragment_container, exFragment).commit()
            } else {
                val  inFragment = IncomeFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.insert_fragment_container, inFragment).commit()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
    }
}