package com.example.incomeexpensemanager

import Fragment.*
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.DatePicker

import android.app.DatePickerDialog
import com.example.e_itmedi.Database.DatabaseHelper

import RoomDatabase.AppDatabase
import RoomDatabase.DisplayItem
import RoomDatabase.User
import android.content.Context
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener

import com.kal.rackmonthpicker.RackMonthPicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(){
    var yeatMonth = ""
    var flag = 0
    var currentDate = SimpleDateFormat("MMM yyyy").format(Date())

    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val databaseHelper = DatabaseHelper(this)
        val sqLiteDatabase = databaseHelper!!.writableDatabase
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_id)
        title = ""
        val toggle = ActionBarDrawerToggle(this, drawer_id, toolbar_id, R.string.open, R.string.close)
        toggle.drawerArrowDrawable.color = resources.getColor(R.color.neutral)
        drawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()
        monthPick_id.setOnClickListener {
            monthpick()
        }

        nav_id.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Profile_ID -> {
                    var intent = Intent(this,NavMenuActivity::class.java)
                    intent.putExtra("fragKey","profile")
                    startActivity(intent)
                    Toast.makeText(
                        this, "Profile",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.menuSetting_id -> {

                    var intent = Intent(this,NavMenuActivity::class.java)
                    intent.putExtra("fragKey","settings")
                    startActivity(intent)
                    Toast.makeText(
                        this,
                        "Settings",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.menuShare_id -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/app"
                    val subject = "This is first app"
                    val head = " Download this app - com.example.fullcountryapplication"
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                    intent.putExtra(Intent.EXTRA_TEXT, head)
                    startActivity(Intent.createChooser(intent,"Share with"))
                    Toast.makeText(this, "Share",Toast.LENGTH_SHORT).show()
                }

                R.id.menuFeedback_id -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "email/app"
                    val subject = "app feedback"
                    val head = " this app .........."
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                    intent.putExtra(Intent.EXTRA_TEXT, head)
                    startActivity(Intent.createChooser(intent,"Open Mail"))

                    Toast.makeText(
                        this,
                        "Settings",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.menuLogout_id -> {
                    LogOut(this)
                    nav_id.menu.findItem(R.id.menuLogIn_id).isVisible = true
                }
                R.id.menuLogIn_id -> {
                    LogIn()
                    nav_id.menu.findItem(R.id.menuLogout_id).isVisible = true
                }
            }
            true
        }
       // bottom_navigation.menu.findItem(R.id.menuAnalysis_id).setChecked(true)
        bottom_navigation.setOnNavigationItemSelectedListener {
            //mBottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_nav);

            when (it.itemId) {
                R.id.menuScale_id -> {

                    val bpfragment = HomeFragment()
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, bpfragment)
                    fragmentTransaction.commit()
                }
                R.id.menuBloodHistory_id -> {
                    val bpfragment = CalenderFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, bpfragment).commit()
                }
                R.id.menuAnalysis_id -> {
                    val bpfragment = AnalyzerFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, bpfragment).commit()
                }
            }
            true
        }
    }

    fun monthpick() {

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
        val rack=  RackMonthPicker(this)
        RackMonthPicker(this)
            .setLocale(Locale.ENGLISH)
            .setPositiveText("set")
            .setPositiveButton { month, startDate, endDate, year, monthLabel ->

                val upMonth = month - 1
                yeatMonth = "${listMonth[upMonth]} $year"
                monthPick_id.text = yeatMonth
                currentDate = yeatMonth
                Log.d("this", "monthAfter $currentDate")
                listSearch()
                userlistSearch()
            }
            .setNegativeButton(object : OnCancelMonthDialogListener {
                fun onCancel(dialog: AlertDialog?) {}
                override fun onCancel(p0: androidx.appcompat.app.AlertDialog?) {
                    p0?.dismiss()

                }
            }).show()
    }

    fun LogOut(context :Context) {

        val builder1 = AlertDialog.Builder(context)
        builder1.setMessage("Do you want to Logout ?")
        builder1.setCancelable(true)

        builder1.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
                Firebase.auth.signOut()
                nav_id!!.menu.findItem(R.id.menuLogout_id).setVisible(false)
                nav_id!!.menu.findItem(R.id.Profile_ID).setVisible(false)

                val sharedpreferences: SharedPreferences = getSharedPreferences("MyPREFERENCES", 0)
                val editor = sharedpreferences!!.edit()
                editor.clear().commit()
            })

        builder1.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }

    lateinit var monthSearch: MonthSearch
    fun listener(monthSearch: MonthSearch) {
        this.monthSearch = monthSearch
        listSearch()
        userlistSearch()
    }

    fun listSearch() {
        val dataList = mutableListOf<DisplayItem>()

        var tk = currentDate
        var incomeMonthly = 0
        var expenseMonthly = 0

        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(this@MainActivity).userDao()
            // val displayData = db.getAllDisplay()
            val displayData = db.getAllbyMonth(tk)
            dataList.addAll(displayData)
            Log.d("this", "listSearch: ${displayData.size}")
            Log.d("this", "flag: $tk")
            monthSearch.monthYearpass(dataList)
            for (i in dataList.indices) {
                incomeMonthly = incomeMonthly!! + dataList[i].income!!
                expenseMonthly = expenseMonthly!! + dataList[i].expense!!
            }
            TextView_IncmMonth_id.text = incomeMonthly.toString()
            TextView_expnsMonth_id.text = expenseMonthly.toString()
            TextView_BalanceMonth_id.text = (incomeMonthly - expenseMonthly).toString()
        }
    }

    fun userlistSearch() {
        val dataList = mutableListOf<User>()

        var tk = currentDate
        var incomeMonthly = 0
        var expenseMonthly = 0

        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(this@MainActivity).userDao()
            // val displayData = db.getAllDisplay()
            val userData = db.getAllListbyMonth(tk)
            dataList.addAll(userData)
            Log.d("this", "listuser: ${userData.size}")
            Log.d("this", "flag: $tk")
            monthSearch.monthYearpassUser(dataList)
        }
    }

    fun LogIn() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }
}