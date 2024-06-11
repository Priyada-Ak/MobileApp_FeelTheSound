package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    private val TAG: String = "Main Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// เป็นการดึงข้อมูลการ login จาก Firebase Authentication
        mAuth = FirebaseAuth.getInstance()
//กรณีที่มีการ login ค้างไว้ จะสามารถเข้าหน้า result ได้เลย
        if (mAuth!!.currentUser != null) {
            Log.d(TAG, "Continue with: " + mAuth!!.currentUser!!.email)
// เป็นการสั่งให้ทําการ start activity ส่วนของหน้า result
            startActivity(Intent(this@MainActivity, ResultActivity::class.java))
            finish()
        }
        main_emailBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
    }
}