package Fragment

import RoomDatabase.AppDatabase
import RoomDatabase.DisplayItem
import RoomDatabase.User
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.incomeexpensemanager.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
var recycler : RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_home, container, false)
        recycler = v.findViewById(R.id.Recycler_id)


//          val act = MainActivity()
//        val lst =act.listSearch()
//        showlist(lst)

        (activity as MainActivity).listener(object  : MonthSearch{
            override fun monthYearpass(items: List<DisplayItem>) {
                showlist(items)
            }

            override fun monthYearpassUser(items: List<User>) {
              //fdgfg
            }
        })
        return v
    }

    override fun onStart() {
        super.onStart()
    }

    fun showlist(date: List<DisplayItem>) {
        lifecycleScope.launch {
            Log.d(ContentValues.TAG, "monthFrag $date")
            val homeAdapter = HomeAdapter(requireContext(), date, this@HomeFragment)
            recycler?.adapter = homeAdapter
            recycler?.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    fun deatailsList(date: String, income: String?, expense: String?) {

        // Log.d(ContentValues.TAG, "myList:${dataList.size}")

        val intent = Intent(requireContext(), DetailsActivity::class.java)

        intent.putExtra("dateKey", date)
        intent.putExtra("incomeKey", income)
        intent.putExtra("expenseKey", expense)
        startActivity(intent)
    }

//    override fun monthYearpass(month: String) {
//        showlist(month)
//    }
}