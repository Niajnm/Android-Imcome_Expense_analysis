package Fragment.NavDrawer

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.incomeexpensemanager.R
import kotlinx.android.synthetic.main.fragment_settings.view.*


class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =inflater.inflate(R.layout.fragment_settings, container, false)
        // Inflate the layout for this fragment

        v.card_id1.setOnClickListener {
            Toast.makeText(requireContext(),"clicked",Toast.LENGTH_SHORT).show()
            val builder1 = AlertDialog.Builder(context)
            val layoutInflater = LayoutInflater.from(context)
            val dialogView = layoutInflater.inflate(R.layout.dialogue_add_category, null)
            //  val date= ctx.rcvDate
            builder1.setView(dialogView)
            builder1.setCancelable(true)
            val alert11: AlertDialog = builder1.create()
            alert11.show()
        }
        return v
    }

}