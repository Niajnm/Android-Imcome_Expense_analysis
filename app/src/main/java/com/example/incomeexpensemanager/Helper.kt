package com.example.incomeexpensemanager

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class Helper {


    fun LogOut(context : Context) {

        val builder1 = AlertDialog.Builder(context)
        builder1.setMessage("Do you want to Logout ?")
        builder1.setCancelable(true)

        builder1.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
                Firebase.auth.signOut()
                val navAct = NavMenuActivity()
                navAct.finish()

            })

        builder1.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val alert11: AlertDialog = builder1.create()
        alert11.show()
}
}