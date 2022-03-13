package com.example.incomeexpensemanager

import RoomDatabase.AppDatabase
import RoomDatabase.DisplayItem
import RoomDatabase.User
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_insert_data.*
import kotlinx.android.synthetic.main.activity_insert_data.insert_date
import kotlinx.android.synthetic.main.fragment_expense.*
import kotlinx.android.synthetic.main.fragment_expense.view.*
import kotlinx.android.synthetic.main.fragment_income.*
import kotlinx.android.synthetic.main.fragment_income.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class InsertDataActivity : AppCompatActivity() {

    var flag = 0
    var payType = ""
    var cattype = ""
    val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

    var currentDate = SimpleDateFormat("d MMM yyyy").format(Date())
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

        if (!insertSwitch_id.isChecked) {
            test()
            switch_tag_ex.isInvisible = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setNavigationBarColor(getResources().getColor(R.color.mycustom))
            }
        }
        insertSwitch_id.setOnClickListener {
            if (insertSwitch_id.isChecked) {
                income()
                scroll_view.setBackgroundResource(R.color.grn)
                flag = 1
                switch_tag_in.isInvisible = false
                switch_tag_ex.isInvisible = true
                Title_id.text = "Income"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setNavigationBarColor(getResources().getColor(R.color.grn))
                }

            } else {
                test()
                scroll_view.setBackgroundResource(R.color.mycustom)
                flag = 0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setNavigationBarColor(getResources().getColor(R.color.mycustom))
                }
                switch_tag_ex.isInvisible = false
                switch_tag_in.isInvisible = true
                Title_id.text = "Expense"
            }

        }
        floating_button.setOnClickListener {
            if (flag == 1) {

                dataSetincm()
            } else {
                setDataExpn()
            }
        }
        insert_date.setOnClickListener {

            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)

            //  bpResult_id.text= "$y-$m-$d"

            val datepickerdialog =
                DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                    val listMonth = listOf(
                        "Jan",
                        "Feb",
                        "Mar",
                        "Apr",
                        "May",
                        "Jun",
                        "Jul",
                        "Aug",
                        "Sept",
                        "Oct",
                        "Nov",
                        "Dec"
                    )

                    //date="$dayOfMonth-$month-$year"
                    currentDate = "$dayOfMonth ${listMonth[monthOfYear]} $year"
                    insert_date.text = currentDate

                    val calender = Calendar.getInstance()
                    calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    calender.set(Calendar.MONTH, monthOfYear)
                    calender.set(Calendar.YEAR, year)
                    val dt1 = calender.timeInMillis
                    val cal_milisec = dt1

                }, y, m, d)
            datepickerdialog.show()
        }
    }

    private fun test() {

        insert_date.text = currentDate
        val expnCategory = resources.getStringArray(R.array.expense_category)
        var expnAdapter = ArrayAdapter<String>(
            this, R.layout.support_simple_spinner_dropdown_item, expnCategory!!
        )
        category_spinner.adapter = expnAdapter
        category_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View, position: Int, id: Long
                ) {
                    cattype = expnCategory!![position].toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        val incmPayment = resources.getStringArray(R.array.income_payment)
        var paymentAdapter = ArrayAdapter<String>(
            this, R.layout.support_simple_spinner_dropdown_item, incmPayment!!
        )
        payment_spinner.adapter = paymentAdapter
        payment_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View, position: Int, id: Long
                ) {
                    payType = incmPayment!![position].toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setDataExpn() {
        var edit1 = text_money.text.toString()
        var payNcat = "$cattype ($payType)"

        if (edit1.isEmpty()) {
            text_money!!.error = "Money!"
            text_money!!.requestFocus()
        } else {

            val money = text_money.text.toString().toInt()
            lifecycleScope.launch(Dispatchers.IO) {
                val db = AppDatabase.getDatabase(this@InsertDataActivity).userDao()
                val user = User(0, currentDate, money, cattype, payType, "0", currentTime)
                db.insertAll(user)
                try {
                    val datekeyList = db.getUniqueDate(currentDate)

                    if (datekeyList.isEmpty()) {
                        val dis = DisplayItem(0, currentDate, 0, money, payNcat)
                        db.insertAllDisplay(dis)
                    } else {
                        db.updateDisplayExpense(money, currentDate)
                    }
                } catch (e: Exception) {
                }
            }

            finish()
            //  startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

    fun income() {
        val dataCategory = mutableListOf<String>()
        val incmCategory = resources.getStringArray(R.array.income_category)
        lifecycleScope.launch(Dispatchers.IO) {
            val dataspinList = mutableListOf<String>()
            val db = AppDatabase.getDatabase(this@InsertDataActivity).userDao()
            // val displayData = db.getAllDisplay()
            val dataSpin = db.getCategorySpinner()
            for(i in dataSpin.indices){
                dataspinList.add(dataSpin[i].catType)
            }
           runOnUiThread {
               dataCategory.addAll(dataspinList)
           }


        }
        var countryAdapter = ArrayAdapter<String>(
            this@InsertDataActivity, R.layout.support_simple_spinner_dropdown_item, incmCategory
        )

        Log.d(TAG, "income-$dataCategory")
        category_spinner.adapter = countryAdapter
        category_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    cattype = dataCategory[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        val incmPayment = resources.getStringArray(R.array.income_payment)
        var paymentAdapter = ArrayAdapter<String>(
            this@InsertDataActivity, R.layout.support_simple_spinner_dropdown_item, incmPayment!!
        )
        payment_spinner.adapter = paymentAdapter
        payment_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View, position: Int, id: Long
                ) {
                    payType = incmPayment!![position].toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun dataSetincm() {
        var edit1 = text_money.text.toString()
        var payNcat = "$cattype ($payType)"

        if (edit1.isEmpty()) {
            text_money!!.error = "Money!"
            text_money!!.requestFocus()
        } else {
            val money = text_money.text.toString().toInt()

            lifecycleScope.launch(Dispatchers.IO) {
                val db = AppDatabase.getDatabase(this@InsertDataActivity).userDao()
                val user = User(0, currentDate, money, cattype, payType, "1", currentTime)
                db.insertAll(user)
                try {
                    val datekeyList = db.getUniqueDate(currentDate)
                    if (datekeyList.size == 0) {
                        val dis = DisplayItem(0, currentDate, money, 0, payNcat)
                        db.insertAllDisplay(dis)
                    } else {
                        db.updateDisplayIn(money, currentDate)
                    }
                } catch (e: Exception) {
                }
            }
            finish()
            //  startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // startActivity(Intent(this,MainActivity::class.java))
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setNavigationBarColor(getResources().getColor(R.color.navColor))
            }
        }
    }
}