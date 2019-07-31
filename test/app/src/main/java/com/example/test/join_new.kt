package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.join
import kotlinx.android.synthetic.main.making_new.*

class join_new : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val TAG : String = "CreateAccount"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.making_new)

        auth = FirebaseAuth.getInstance()

        //사용자가 입력한 값들
//        var email: String = et_email.text.toString()
//        var password: String = et_password.text.toString()
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.passwd)
        val password_check = findViewById<EditText>(R.id.passwd_check)
        val age = findViewById<EditText>(R.id.age)
        val nickname=findViewById<EditText>(R.id.nickname)
        var email_checking=1

        check_email.setOnClickListener {
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        Toast.makeText(baseContext, "사용가능한 이메일 입니다.", Toast.LENGTH_SHORT).show()
                        //    val user = auth.currentUser
                        //updateUI(user)
                        // 아니면 액티비티를 닫아 버린다.
                        email_checking=0

                        //     overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit)
                    }  else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        if(password.text.toString().length<6) {
                            Toast.makeText(baseContext, "비밀번호는 6자리 이상 입력해야합니다.", Toast.LENGTH_SHORT).show()
                            password?.setText("")
                            password_check?.setText("")
                            email.requestFocus()
                        }else {
                            Toast.makeText(baseContext, "이미 가입된 이메일입니다.", Toast.LENGTH_SHORT).show()
                            //updateUI(null)
                            //입력필드 초기화
                            email?.setText("")
                            password?.setText("")
                            password_check?.setText("")
                            email.requestFocus()
                        }
                    }
                }
        }
        //새로운 계정을 생성한다.
        join.setOnClickListener {
            //Log.d(TAG, "Data: " + email.text + password.text)

            if (email.text.toString().length == 0 || password.text.toString().length == 0){
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if(password.text.toString().equals(password_check.text.toString())==false){
                Toast.makeText(baseContext, "비밀번호가 다릅니다.\n다시 입력해주세요.", Toast.LENGTH_SHORT).show()
                //updateUI(null)
                //입력필드 초기화
                //   email?.setText("")
                password?.setText("")
                password_check?.setText("")
                email.requestFocus()

            }else if(email_checking==1){
                Toast.makeText(baseContext, "이메일 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show()

            }else {
             /*   val database : FirebaseDatabase = FirebaseDatabase.getInstance()
                val myRef : DatabaseReference = database.getReference(email.text.toString())
                myRef.setValue(age.text.toString())*/

                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("member").child(auth.currentUser?.uid.toString())
                myRef.child("나이").setValue(age.text.toString())
                myRef.child("이메일").setValue(email.text.toString())
                myRef.child("닉네임").setValue(nickname.text.toString())
                myRef.child("비밀번호").setValue(password.text.toString())
                Toast.makeText(baseContext, "가입 완료", Toast.LENGTH_SHORT).show()
                email_checking=1
                finish()
            }
        }

        /*
        bt_cancel.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit)
        }*/
    }
}
