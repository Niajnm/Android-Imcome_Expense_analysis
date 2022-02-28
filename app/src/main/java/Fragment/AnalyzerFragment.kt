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
import kotlinx.android.synthetic.main.fragment_analyzer.*
import kotlinx.android.synthetic.main.fragment_analyzer.view.*
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel


class AnalyzerFragment : Fragment() {
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
        val v = inflater.inflate(R.layout.fragment_analyzer, container, false)
        mPieChart = v.findViewById<View>(R.id.month_piechart) as PieChart

        // chart(2,4,5,6,7,8)

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
        v.income_frag_btn.setOnClickListener {
            val frag = IncomeAnalyzerFragment()
            val fragmentManager = activity?.getSupportFragmentManager()
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, frag)
            fragmentTransaction?.addToBackStack(null);
            fragmentTransaction?.commit()
        }

        Log.d("tag", "::item.......${dataList.size}")
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

        var Shopping = 0
        var hRent = 0
        var Loan = 0
        var Fees = 0
        var high3 = 0
        var Salary = 0
        Log.d(ContentValues.TAG, "cursorAnalysis: ${items.size}")

        for (i in items.indices) {
            if (items[i].category == "Shopping" && items[i].tag == "expense") {
                Shopping += items[i].money!!

            } else if (items[i].category == "House Rent" && items[i].tag == "expense") {
                hRent += items[i].money!!

            } else if (items[i].category == "Loan" && items[i].tag == "expense") {
                Loan += items[i].money!!

            } else if (items[i].category == "Fees" && items[i].tag == "expense") {
                Fees += items[i].money!!

            }
            Log.d(ContentValues.TAG, "cursorAnalysis: ${items[i].money}")

        }

        chart(Shopping, hRent, Loan, Fees)
    }

    fun chart(Shopping: Int, hRent: Int, Loan: Int, Fees: Int) {

        tv_Shopping.setText(Integer.toString(Shopping))
        tv_homeRent.setText(Integer.toString(hRent))
        tv_Loan.setText(Integer.toString(Loan))
        tv_Fees.setText(Integer.toString(Fees))

        // Set the data and color to the pie chart
        mPieChart.clearChart()
        mPieChart.addPieSlice(
            PieModel(
                "Shopping",
                Shopping.toFloat(),
                resources.getColor(R.color.shopping)
            )
        )
        mPieChart.addPieSlice(
            PieModel(
                "Home rent",
                hRent.toFloat(),
                resources.getColor(R.color.homeRent)
            )
        )
        mPieChart.addPieSlice(PieModel("Loan", Loan.toFloat(), resources.getColor(R.color.Loan)))
        mPieChart.addPieSlice(PieModel("Fees", Fees.toFloat(), resources.getColor(R.color.Fees)))
        mPieChart.startAnimation()

    }
}