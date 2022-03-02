package Fragment

import RoomDatabase.DisplayItem
import RoomDatabase.User
import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.incomeexpensemanager.MainActivity
import com.example.incomeexpensemanager.MonthSearch
import com.example.incomeexpensemanager.R
import kotlinx.android.synthetic.main.fragment_analyzer_income.*
import kotlinx.android.synthetic.main.fragment_analyzer_income.view.*
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel


class IncomeAnalyzerFragment : Fragment() {
    private lateinit var mPieChart: PieChart
    var loan = 0

    var dataList = mutableListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_analyzer_income, container, false)
        mPieChart = v.findViewById<View>(R.id.month_income_piechart) as PieChart

        (activity as MainActivity).listener(object : MonthSearch {
            override fun monthYearpass(items: List<DisplayItem>) {
                //yui
            }

            override fun monthYearpassUser(items: List<User>) {
                dataList.addAll(items)
                Log.d("tag", "::item${dataList.size}")
                requireActivity().runOnUiThread {
                    showData(items)
                }
            }
        })

        Log.d("tag", "::item.......${dataList.size}")
        v.frag_btn_to_ex.setOnClickListener {
            val fragment = AnalyzerFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container,fragment)
                ?.commit()
        }

        return v
    }

//    fun dodo(){
////
////        Handler().postDelayed({
////            showData(dataList)
////        }, 500)
//        showData(dataList)
//    }

    private fun showData(items: List<User>) {
        var Salary = 0
        var Interest = 0
        var Profit = 0
        var Pension = 0
        var high3 = 0

        Log.d(ContentValues.TAG, "cursorAnalysis: ${items.size}")

        for (i in items.indices) {
            if (items[i].category == "Salary" && items[i].tag == "income") {
                Salary += items[i].money!!
            } else if (items[i].category == "Interest" && items[i].tag == "income") {
                Interest += items[i].money!!
            } else if (items[i].category == "Profit" && items[i].tag == "income") {
                Profit += items[i].money!!
            } else if (items[i].category == "Pension" && items[i].tag == "income") {
                Pension += items[i].money!!
            }
            Log.d(ContentValues.TAG, "IncomecursorAnalysis: ${items[i].money}")
        }
        chart(Salary, Interest, Profit, Pension)
    }

    private fun chart(Salary: Int, Interest: Int, Profit: Int, Pension: Int) {

        tv_Salary.setText(Integer.toString(Salary))
        tv_Interest.setText(Integer.toString(Interest))
        tv_Profit.setText(Integer.toString(Profit))
        tv_Pension.setText(Integer.toString(Pension))

        // Set the data and color to the pie chart
        mPieChart.clearChart()
        mPieChart.addPieSlice(
            PieModel(
                "Salary",
                Salary.toFloat(),
                resources.getColor(R.color.salary)
            )
        )
        mPieChart.addPieSlice(PieModel("Interest", Interest.toFloat(), resources.getColor(R.color.Interest)))
        mPieChart.addPieSlice(PieModel("Profit", Profit.toFloat(), resources.getColor(R.color.profit)))
        mPieChart.addPieSlice(PieModel("Pension", Pension.toFloat(), resources.getColor(R.color.pension)))
        mPieChart.startAnimation()

    }

}