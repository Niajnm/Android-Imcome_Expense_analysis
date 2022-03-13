package Fragment.NavDrawer

import RoomDatabase.AppDatabase
import RoomDatabase.Category
import RoomDatabase.PaymentType
import RoomDatabase.User
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.incomeexpensemanager.R
import kotlinx.android.synthetic.main.activity_insert_data.*
import kotlinx.android.synthetic.main.dialogue_add_category.view.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingsFragment : Fragment() {
    var flag = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_settings, container, false)
        // Inflate the layout for this fragmentwe
        v.card_id1.setOnClickListener {
            addMethod()
            flag = 1
        }

        v.card_id2.setOnClickListener {
            addMethod()
            flag = 2
        }
        return v
    }

    fun addMethod() {

        Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
        val builder1 = AlertDialog.Builder(context)
        val layoutInflater = LayoutInflater.from(context)
        val dialogView = layoutInflater.inflate(R.layout.dialogue_add_category, null)

        builder1.setView(dialogView)
        builder1.setCancelable(true)
        val alert11: AlertDialog = builder1.create()
        alert11.show()

        dialogView.cat_save_button.setOnClickListener {

            val cat = dialogView.category_add_id.text.toString()
            Log.d(TAG, "Ctegory: $cat")
            if (cat.isEmpty()) {
                text_money!!.error = "Money!"
                text_money!!.requestFocus()
            } else {
                if (flag == 1) {

                    try {
                        val db = AppDatabase.getDatabase(requireContext()).userDao()
                        val categoryItem = Category(0, cat)
                        db.insertCategory(categoryItem)
                    } catch (e: Exception) {
                    }

                } else if (flag == 2) {
                    try {
                        val db = AppDatabase.getDatabase(requireContext()).userDao()
                        val paymentItem = PaymentType(0, cat)
                        db.insertPayMethod(paymentItem)
                    } catch (e: Exception) {
                    }
                }
            }
            alert11.dismiss()
        }
    }
}