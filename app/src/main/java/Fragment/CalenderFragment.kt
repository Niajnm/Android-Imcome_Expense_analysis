package Fragment

import RoomDatabase.AppDatabase
import RoomDatabase.DisplayItem
import RoomDatabase.User
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.incomeexpensemanager.R
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.incomeexpensemanager.AdapterDeatils
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_calender.*
import kotlinx.android.synthetic.main.fragment_calender.view.*
import kotlinx.coroutines.launch

class CalenderFragment :Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_calender, container, false)
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext()).userDao()
            var dataList: MutableList<User> = db.getAllRecent()
            val Adapter = RecentAdapter(requireContext(), dataList,this@CalenderFragment)
            v.recent_recyler!!.adapter = Adapter

            v.recent_recyler.layoutManager = LinearLayoutManager(requireContext())
        }
        showRecent()
        return v
    }
    fun showRecent() {

    }
    fun itemDelete(getID: Int, getMoney: Int, getFlag: String, getDate: String) {
        val db = AppDatabase.getDatabase(requireContext()).userDao()
        db.deleteByuid(getID)
        // recycleList.removeAt(userPosition)
        if(getFlag == "1"){
            db.DeleteDisplayIn(getMoney,getDate)
        }else{
            db.DeleteDisplayExpense(getMoney,getDate)
        }
        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
        // text()
    }
}