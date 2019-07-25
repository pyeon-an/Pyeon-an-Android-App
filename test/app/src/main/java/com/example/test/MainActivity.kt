package com.example.test

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG : String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.passwd)

        //로그인
        login.setOnClickListener {

            if (email.text.toString().length == 0 || password.text.toString().length == 0){
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            Toast.makeText(baseContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, menual::class.java)
                            startActivity(intent)
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()

                            updateUI(null)
                        }

                        // ...
                    }

            }

        }

        //xml에 적힌 이름 바로 적어 줘도 되네... 굿
        join.setOnClickListener {
            val intent = Intent(this, join_new::class.java)
            startActivity(intent)
         //   overridePendingTransition(R.anim.slide_in, R.anim.slide_out) //이건 안 먹힘???
        }

        /*로그아웃
        bt_logout.setOnClickListener {
            auth.signOut()
            //로그인 활성화 - 이걸 더 효율적으로 하는 방법이 있을것 같은데 일일히 적어 줘야 해?
            tv_message.setText("로그인이 필요합니다..")
            bt_logout.isEnabled = false
            bt_login.isEnabled = true
            bt_create.isEnabled = true
        }*/

    }

    override fun onResume() {
        super.onResume()
        val currentUser = auth?.currentUser
        updateUI(currentUser)

    }

    override fun onStart() {
        super.onStart()
        //앱 시작 단계에서 사용자가 현재 로그인 되어 있는지 확인하고 처리 해 준다.
        val currentUser = auth?.currentUser
        updateUI(currentUser) //이건 원하는대로 사용자 설정해 주는 부분인듯 하다.
    }
/*
    fun updateUI(cUser : FirebaseUser? = null){
        if(cUser != null) {
            tv_message.setText("로그인 되었습니다.")
            //로그인 버튼과 기타 등등을 사용할 수 없게 함(일괄 묶어서 처리 하는 방법?)
            bt_login.isEnabled = false
            bt_create.isEnabled = false
            bt_logout.isEnabled = true
        } else {
            tv_message.setText("로그인이 필요합니다..")
            bt_logout.isEnabled = false
        }
        et_email0.setText("")
        et_password0.setText("")
        hideKeyboard(et_email0)
        //Toast.makeText(this, "유저: "+cUser.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboard(view: View) {
        view?.apply {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }*/
    fun updateUI(cUser : FirebaseUser? = null){
        if(cUser != null) {
     //       tv_message.setText("로그인 되었습니다.")
            //로그인 버튼과 기타 등등을 사용할 수 없게 함(일괄 묶어서 처리 하는 방법?)
            login.isEnabled = true
            join.isEnabled = true
          //  bt_logout.isEnabled = true
        } else {
       //     tv_message.setText("로그인이 필요합니다..")
        //    bt_logout.isEnabled = false
        }
        email.setText("")
        passwd.setText("")
        hideKeyboard(email)
         //Toast.makeText(this, "유저: "+cUser.toString(), Toast.LENGTH_SHORT).show()
    }
    private fun hideKeyboard(view: View) {
        view?.apply {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}

