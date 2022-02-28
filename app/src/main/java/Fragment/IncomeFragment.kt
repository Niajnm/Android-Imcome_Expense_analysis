package Fragment

import RoomDatabase.AppDatabase
import RoomDatabase.DisplayItem
import RoomDatabase.User
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.incomeexpensemanager.MainActivity
import com.example.incomeexpensemanager.R
import kotlinx.android.synthetic.main.fragment_expense.*
import kotlinx.android.synthetic.main.fragment_expense.view.*
import kotlinx.android.synthetic.main.fragment_income.*
import kotlinx.android.synthetic.main.fragment_income.view.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class IncomeFragment : Fragment() {

    var payType = ""
    var cattype = ""
    var currentDate = SimpleDateFormat("dd MMM yyyy").format(Date())
    var date = currentDate
    var monthYr = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_income, container, false)
        v.income_date.text = date
        v.income_date.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)

            //  bpResult_id.text= "$y-$m-$d"
            val datepickerdialog =
                DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
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

                    date = "$dayOfMonth ${listMonth[monthOfYear]} $year"
                    income_date.text = date
                    monthYr = "${listMonth[monthOfYear]} $year"

                    val calender = Calendar.getInstance()
                    calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    calender.set(Calendar.MONTH, monthOfYear)
                    calender.set(Calendar.YEAR, year)
                    val dt1 = calender.timeInMillis
                    val cal_milisec = dt1

                }, y, m, d)

            datepickerdialog.show()
        }

        val incmCategory = resources.getStringArray(R.array.income_category)
        var countryAdapter = ArrayAdapter<String>(
            requireContext(), R.layout.support_simple_spinner_dropdown_item, incmCategory!!
        )
        v.incm_category_spinner.adapter = countryAdapter
        v.incm_category_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    cattype = incmCategory!![position].toString()
                    Toast.makeText(
                        context,
                        incmCategory!![position].toString() + " selected",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        val incmPayment = resources.getStringArray(R.array.income_payment)
        var paymentAdapter = ArrayAdapter<String>(
            requireContext(), R.layout.support_simple_spinner_dropdown_item, incmPayment!!
        )
        v.incm_payment_spinner.adapter = paymentAdapter
        v.incm_payment_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    payType = incmPayment!![position].toString()
                    Toast.makeText(
                        context,
                        incmPayment!![position].toString() + " selected",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        v.floating_button_Income.setOnClickListener {
            var edit1 = textIncome_money.text.toString()

            var payNcat = "$cattype ($payType)"
            if (edit1.isEmpty()) {
                textIncome_money!!.error = "Money!"
                textIncome_money!!.requestFocus()
            } else {
                val money = textIncome_money.text.toString().toInt()
                lifecycleScope.launch {
                    val db = AppDatabase.getDatabase(requireContext()).userDao()
                    val user = User(0, date, money, cattype, payType, "income")
                    db.insertAll(user)
                    try {
                        val datekeyList = db.getUniqueDate(date)
                        if (datekeyList.size == 0) {
                            val dis = DisplayItem(0, date, money, 0, payNcat)
                            db.insertAllDisplay(dis)
                        } else {
                            db.updateDisplayIn(money, date)
                        }

                    } catch (e: Exception) {
                    }
                }
                activity?.finish()
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
        }
        return v
    }
}