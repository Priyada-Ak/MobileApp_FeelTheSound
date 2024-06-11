package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    private val TAG: String = "Register Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth!!.currentUser != null) {
            startActivity(Intent(this@RegisterActivity, ResultActivity::class.java))
            finish()
        }
        register_registerBtn.setOnClickListener {
            val email = register_emailEditText.text.toString().trim { it <= ' ' }
            val password = register_passwordEditText.text.toString().trim { it <= ' ' }

            //ทําการตรวจสอบก่อนว่ามีข้อมูลหรือไม่
            if (email.isEmpty()) {
                Toast.makeText(this,"Please enter your email address.", Toast.LENGTH_LONG).show()
                Log.d(TAG, "Email was empty!")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this,"Please enter your password.",Toast.LENGTH_LONG).show()
                Log.d(TAG, "Password was empty!")
                return@setOnClickListener
            }
            val lowercase: Pattern = Pattern.compile("[a-z]")
            val uppercase: Pattern = Pattern.compile("[A-Z]")
            val digit: Pattern = Pattern.compile("[0-9]")
            if (email.contains(".ac.") == false) {
                Toast.makeText(
                    this,
                    "Wrong Email. Please enter email with '.ac'",
                    Toast.LENGTH_LONG
                ).show()
                Log.d(TAG, "Enter University Email with '.ac.'")
                return@setOnClickListener
            }
            if (password.length < 8) { // ตรวจสอบความยาวของ password
                Toast.makeText(
                    this,
                    "Password too short! Please enter minimum 8 characters.",
                    Toast.LENGTH_LONG
                ).show()
                Log.d(TAG, "Enter password less than 8 characters.")
                return@setOnClickListener
            }
            // if lowercase character is not show
            if (!lowercase.matcher(password).find()) {
                Toast.makeText(
                    this,
                    "Password is weak. Please enter at least 1 lower letter character.",
                    Toast.LENGTH_LONG
                ).show()
                Log.d(TAG, "Enter password with lower letter.")
                return@setOnClickListener
            }
            // if uppercase character is not show
            if (!uppercase.matcher(password).find()) {
                Toast.makeText(
                    this,
                    "Password is weak. Please enter at least 1 upper letter character.",
                    Toast.LENGTH_LONG
                ).show()
                Log.d(TAG, "Enter password with upper letter.")
                return@setOnClickListener
            }
            // if digit is not present
            if (!digit.matcher(password).find()) {
                Toast.makeText(
                    this,
                    "Password is weak. Please enter at least 1 digit character.",
                    Toast.LENGTH_LONG
                ).show()
                Log.d(TAG, "Enter password with digit.")
                return@setOnClickListener
            } else {
                mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                    Toast.makeText(this, "Create account successfully!", Toast.LENGTH_LONG)
                        .show()
                    Log.d(TAG, "Create account successfully!")
                    startActivity(Intent(this@RegisterActivity, ResultActivity::class.java))
                    finish()
                }
            }
        }
    }
}
