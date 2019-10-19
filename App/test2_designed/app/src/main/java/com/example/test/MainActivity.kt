package com.example.test

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.email

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG : String = "MainActivity"
    var time3: Long = 0

    override fun onBackPressed() {
        val time1 = System.currentTimeMillis()
        val time2 = time1 - time3
        if (time2 in 0..2000) {
            finishAffinity()
            System.runFinalization()
            System.exit(0)
        }
        else {
            time3 = time1
            Toast.makeText(applicationContext, "한번 더 누르시면 종료됩니다",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 툴바 추가
        val actionBar = supportActionBar

        actionBar!!.title = "로그인"

        //actionBar.setDisplayHomeAsUpEnabled(true)
        //actionBar.setDisplayHomeAsUpEnabled(true)
        // 툴바 추가 완료

        auth = FirebaseAuth.getInstance()

          val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.passwd)
        use.setText(auth.currentUser?.email.toString())
        //로그인

        // 페이지 애니매이션 추가
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
        email.startAnimation(ttb)
        password.startAnimation(ttb)

        logo.startAnimation(stb)
        login.startAnimation(btt)
        // 페이지 애니매이션 추가 끝

        find_pw.setOnClickListener {
            val intent = Intent(this, Passwd_findingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        login.setOnClickListener {

            if (email.text.toString().length == 0 || password.text.toString().length == 0){
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            Toast.makeText(baseContext, "로그인 성공", Toast.LENGTH_SHORT).show()

                            val user = auth.currentUser
                            val intent = Intent(this, MenubarActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()

                        }

                        // ...
                    }

            }

        }

        //xml에 적힌 이름 바로 적어 줘도 되네... 굿
        join.setOnClickListener {
            val intent = Intent(this, join_new::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
         //   overridePendingTransition(R.anim.slide_in, R.anim.slide_out) //이건 안 먹힘???
        }
    }
}

