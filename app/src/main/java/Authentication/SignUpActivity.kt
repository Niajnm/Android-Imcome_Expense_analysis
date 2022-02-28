package Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.example.incomeexpensemanager.R
import android.widget.Toast

import com.google.firebase.auth.AuthResult

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        auth = Firebase.auth
        textView_loginMember.setOnClickListener {

            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        buttonSign_id.setOnClickListener {
            UserRegister()
        }
    }


    private fun UserRegister() {
        val email: String = textSign_email.getText().toString().trim()
        val pass: String = textSign_pass.getText().toString()
        val cpass: String = textSign_Cpass.getText().toString()
        if (email.isEmpty()) {
            textSign_email.setError("Enter an email address")
            textSign_email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textSign_email.setError("Enter a valid email address")
            textSign_email.requestFocus()
            return
        }

        //checking the validity of the password
        if (pass.isEmpty()) {
            textSign_pass.setError("Enter a password")
            textSign_pass.requestFocus()
            return
        }
        if (pass.length < 6) {
            textSign_pass.setError("Password Length Must be 8 Digits")
            textSign_pass.requestFocus()
            return
        }
        if (!cpass.equals(cpass)) {
            textSign_Cpass!!.error = "Password Length Must be 4 Digits"
            textSign_Cpass!!.requestFocus()
            return
        }

        //progressBar.setVisibility(View.VISIBLE)
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                //progressBar.setVisibility(View.GONE)
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Register is successfull",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Sign in success, update UI with the signed-in user's information
                    /*Log.d(TAG, "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);*/
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Mail Already Registered ",
                        Toast.LENGTH_SHORT
                    ).show()


                   // Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();

                }
            })
    }
}