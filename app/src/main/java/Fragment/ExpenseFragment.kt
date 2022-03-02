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


class ExpenseFragment : Fragment() {
    var payType = ""
    var cattype = ""
    val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())


    var currentDate = SimpleDateFormat("dd MMM yyyy").format(Date())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_expense, container, false)

        v.expense_date.text = currentDate
        v.expense_date.setOnClickListener {

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
                    currentDate = "$dayOfMonth ${listMonth[monthOfYear]} $year"
                    expense_date.text = currentDate

                    val calender = Calendar.getInstance()
                    calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    calender.set(Calendar.MONTH, monthOfYear)
                    calender.set(Calendar.YEAR, year)
                    val dt1 = calender.timeInMillis
                    val cal_milisec = dt1

                }, y, m, d)

            datepickerdialog.show()
        }

        val expnCategory = resources.getStringArray(R.array.expense_category)
        var expnAdapter = ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, expnCategory!!
        )
        v.expn_category_spinner.adapter = expnAdapter
        v.expn_category_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long
                ) {
                    cattype = expnCategory!![position].toString()
                    Toast.makeText(context, expnCategory!![position].toString()+" selected",Toast.LENGTH_SHORT).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        val incmPayment = resources.getStringArray(R.array.income_payment)
        var paymentAdapter = ArrayAdapter<String>(
            requireContext(), R.layout.support_simple_spinner_dropdown_item, incmPayment!!
        )
        v.expn_payment_spinner.adapter = paymentAdapter
        v.expn_payment_spinner.onItemSelectedListener =
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

        v.floating_button_expense.setOnClickListener {
         setData()
        }

        v.income_add_btn.setOnClickListener {
            val fragment = IncomeFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.insert_fragment_container, fragment)
                ?.commit()
        }
        return v
    }

    private fun setData() {
        var edit1 = textExpense_money.text.toString()
        var payNcat = "$cattype ($payType)"

        if (edit1.isEmpty()) {
            textExpense_money!!.error = "Money!"
            textExpense_money!!.requestFocus()
        } else {
            val money = textExpense_money.text.toString().toInt()
            lifecycleScope.launch {
                val db = AppDatabase.getDatabase(requireContext()).userDao()
                val user = User(0, currentDate, money, cattype, payType, "expense",currentTime)
                db.insertAll(user)

                try {
                    val datekeyList = db.getUniqueDate(currentDate)

                    if (datekeyList.isEmpty()) {
                        val dis = DisplayItem(0, currentDate, 0, money, payNcat)
                        db.insertAllDisplay(dis)
                    } else {
                        db.updateDisplayExpense(money, currentDate)
                    }
                   } catch (e: Exception) { }
            }
            activity?.finish()
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }
}