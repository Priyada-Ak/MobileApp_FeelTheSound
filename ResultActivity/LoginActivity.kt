package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    private val TAG: String = "Login Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth!!.currentUser != null) {
            startActivity(Intent(this@LoginActivity, ResultActivity::class.java))
            finish()
        }
        login_loginBtn.setOnClickListener {
            val email = login_emailEditText.text.toString().trim { it <= ' ' }
            val password = login_passwordEditText.text.toString().trim { it <= ' ' }

// ทําการตรวจสอบก่อนว่ามีข้อมูลหรือไม่ ก่อนที่จะไปตรวจสอบค่า
            if (email.isEmpty()) {
                Toast.makeText(this,"Please enter your email address.",Toast.LENGTH_LONG).show()
                Log.d(TAG, "Email was empty!")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this,"Please enter your password.",Toast.LENGTH_LONG).show()
                Log.d(TAG, "Password was empty!")
                return@setOnClickListener
            }
//ทําการตรวจสอบค่าที่กรอกกับค่าจาก Firebase Authentication
            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (!task.isSuccessful) { //กรณีที่ไม่ผ่านการตรวจสอบ
                    if (password.length < 6) { //ตรวจสอบความยาวของ password ว่าน้อยกว่า 6 ไหม
                        login_passwordEditText.error = "Please check your password. Password must have minimum 6 characters."

                        Log.d(TAG, "Enter password less than 6 characters.")
                    } else {
                        Toast.makeText(this,"Authentication Failed: " +
                                task.exception!!.message,Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Authentication Failed: " + task.exception!!.message)
                    }
                } else { //กรณีที่อีเมลและรหัสถูกต้อง
                    Toast.makeText(this,"Sign in successfully!",Toast.LENGTH_LONG).show()
                    Log.d(TAG, "Sign in successfully!")
                    startActivity(Intent(this@LoginActivity, ResultActivity::class.java))
                    finish()
                }
            }
        }
//กรณีกดปุ่ม create account
        login_createBtn.setOnClickListener { startActivity(Intent(this@LoginActivity, RegisterActivity::class.java)) }
//กรณีกดปุ่ม Back
        login_backBtn.setOnClickListener { onBackPressed() }
    }
}