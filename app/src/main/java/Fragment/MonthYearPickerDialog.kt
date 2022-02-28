package Fragment

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.util.*
import android.content.DialogInterface

import android.app.DatePickerDialog.OnDateSetListener
import android.view.View

import android.widget.NumberPicker
import com.example.incomeexpensemanager.MainActivity
import com.example.incomeexpensemanager.R


class MonthYearPickerDialog(val date: Date = Date()) : DialogFragment() {
    private val MAX_YEAR = 2099
    private var listener: OnDateSetListener? = null

//    fun setListener(listener: MainActivity) {
//        this.listener = listener
//    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(
            requireActivity()
        )
        // Get the layout inflater
        val inflater = requireActivity().layoutInflater
        val cal = Calendar.getInstance()
        val dialog: View = inflater.inflate(R.layout.dialog_month_year_picker, null)
        val monthPicker = dialog.findViewById(R.id.pickerMonth) as NumberPicker
        val yearPicker = dialog.findViewById(R.id.pickerYear) as NumberPicker
     val month = cal[Calendar.MONTH]
        monthPicker.minValue = 0
        monthPicker.maxValue = 11
        monthPicker.value = month+1
        val year = cal[Calendar.YEAR]
        yearPicker.minValue = year
        yearPicker.maxValue = MAX_YEAR
        yearPicker.value = year
        builder.setView(dialog) // Add action buttons
            .setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, id ->
                    listener!!.onDateSet(
                        null,
                        yearPicker.value,
                        monthPicker.value,
                        0)
                })
            .setNegativeButton(
                R.string.cancel,
                DialogInterface.OnClickListener { dialog, id -> this@MonthYearPickerDialog.dialog!!.cancel() })
        return builder.create()
    }
}