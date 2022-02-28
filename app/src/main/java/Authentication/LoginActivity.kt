package Authentication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import com.example.incomeexpensemanager.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.Toast

import com.google.firebase.auth.AuthResult

import androidx.annotation.NonNull
import com.example.incomeexpensemanager.MainActivity

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        auth = Firebase.auth

        button_Login.setOnClickListener {
            userLogin()
        }
        textView_createAcc_id.setOnClickListener {

            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        textView_Forgot_id.setOnClickListener {

        }
    }

    private fun userLogin() {
        val email: String = textLogin_email.text.toString().trim()
        val pass: String = textLogin_pass.getText().toString()
        if (email.isEmpty()) {
            textLogin_email.error = "Enter a valid email"
            textLogin_email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textLogin_email.setError("Enter a valid email address")
            textLogin_email.requestFocus()
            return
        }

        //checking the validity of the password
        if (pass.isEmpty()) {
            textLogin_pass.setError("Enter a password")
            textLogin_pass.requestFocus()
            return
        }
        if (pass.length < 6) {
            textLogin_pass.setError("Password Length Must be 8 Digits")
            textLogin_pass.requestFocus()
            return
        }
        //progressBar.setVisibility(View.VISIBLE)
        val authResultTask: Task<AuthResult> =
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                //progressBar.setVisibility(View.GONE)
                if (task.isSuccessful) {

                    val currentUser = auth.currentUser
                    val sharedpreferences = getSharedPreferences("MyPREFERENCES", 0)
                    val editor = sharedpreferences!!.edit()
                    editor.putString("UserToken", currentUser.toString())
                    editor.commit()
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }


}