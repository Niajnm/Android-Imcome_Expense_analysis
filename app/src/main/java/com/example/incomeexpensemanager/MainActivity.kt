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

import RoomDatabase.AppDatabase
import RoomDatabase.DisplayItem
import RoomDatabase.User
import android.Manifest
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener

import com.kal.rackmonthpicker.RackMonthPicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(){
    var yeatMonth = ""
    var currentDate = SimpleDateFormat("MMM yyyy").format(Date())
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(getResources().getColor(R.color.navColor))
        }
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_id)
        title = ""
        val toggle = ActionBarDrawerToggle(this, drawer_id, toolbar_id, R.string.open, R.string.close)
        toggle.drawerArrowDrawable.color = resources.getColor(R.color.neutral)
        drawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()
        monthPick_id.text=currentDate
        monthPick_id.setOnClickListener {
            monthpick()
        }
        open_add_btn.setOnClickListener {
            startActivity(Intent(this, InsertDataActivity::class.java))
        }

        nav_id.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Profile_ID -> {
                    val intent = Intent(this,NavMenuActivity::class.java)
                    intent.putExtra("fragKey","profile")
                    startActivity(intent)
                }
                R.id.menuSetting_id -> {

                    val intent = Intent(this,NavMenuActivity::class.java)
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
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                }
                R.id.menuAbout_id -> {
                    val intent = Intent(this,NavMenuActivity::class.java)
                    intent.putExtra("fragKey","about")
                    startActivity(intent)
                    Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
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
                R.id.menuRecent_id -> {
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

    private fun monthpick() {

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

//    override fun onResume() {
//        super.onResume()
//        val bpfragment = HomeFragment()
//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, bpfragment).commit()
//
//    }

    fun LogOut(context :Context) {
        val builder1 = AlertDialog.Builder(context)
        builder1.setMessage("Do you want to Logout ?")
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "Yes"
        ) { dialog, id ->
            dialog.cancel()
            Firebase.auth.signOut()
            nav_id!!.menu.findItem(R.id.menuLogout_id).isVisible = false
            nav_id!!.menu.findItem(R.id.Profile_ID).setVisible(false)

            val sharedpreferences: SharedPreferences = getSharedPreferences("MyPREFERENCES", 0)
            val editor = sharedpreferences.edit()
            editor.clear().apply()
        }

        builder1.setNegativeButton(
            "No") { dialog, id -> dialog.cancel() }

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
                incomeMonthly += dataList[i].income!!
                expenseMonthly += dataList[i].expense!!
            }
            TextView_IncmMonth_id.text = incomeMonthly.toString()
            TextView_expnsMonth_id.text = expenseMonthly.toString()
            TextView_BalanceMonth_id.text = (incomeMonthly - expenseMonthly).toString()
        }
    }

    fun userlistSearch() {
        val dataList = mutableListOf<User>()
        var tk = currentDate

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

    fun LogIn() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }
}