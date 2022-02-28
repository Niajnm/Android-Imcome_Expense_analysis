package Fragment.NavDrawer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.incomeexpensemanager.MainActivity
import com.example.incomeexpensemanager.R
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment

        v.imageButton_out.setOnClickListener {
            val mainActivity: MainActivity = activity as MainActivity
            mainActivity.LogOut(requireContext())

        }
        return v
    }
}