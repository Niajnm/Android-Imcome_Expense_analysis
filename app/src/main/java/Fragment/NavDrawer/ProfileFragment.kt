package Fragment.NavDrawer

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.incomeexpensemanager.Helper
import com.example.incomeexpensemanager.MainActivity
import com.example.incomeexpensemanager.R
import com.example.incomeexpensemanager.StartActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment
        v.imageButton_out.setOnClickListener {
           // val mainActivity: MainActivity = activity as MainActivity
                LogOut(requireContext())
        }
        return v
    }

    fun LogOut(context :Context) {
        val builder1 = AlertDialog.Builder(context)
        builder1.setMessage("Do you want to Logout ?")
        builder1.setCancelable(true)

        builder1.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, id ->
                Firebase.auth.signOut()
                dialog.cancel()
                activity?.finish()
              startActivity(Intent(requireContext(),StartActivity::class.java))
            })

        builder1.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }
}