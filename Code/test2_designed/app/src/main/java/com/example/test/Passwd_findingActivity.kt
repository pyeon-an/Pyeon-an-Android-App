package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.passwd_finding.*

class Passwd_findingActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.passwd_finding)

        val actionBar = supportActionBar

        actionBar!!.title = "비밀번호 찾기"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val email = findViewById<EditText>(R.id.email)
        val memberRef = database.getReference("member")

        finding.setOnClickListener {
            memberRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                    var value = p0.children.asIterable()
                    var iter = p0.children.asIterable()
                    var count = 1
                    var childNum = p0.childrenCount.toInt()
                    var My = iter.iterator().next()
                    var myEmail = My.child("email").getValue().toString()
                    var myPasswd = My.child("pwd").getValue().toString()
                    while (email.text.toString().equals(myEmail) != true && count<=childNum) {
                        iter = value.asIterable()
                        My = iter.iterator().next()
                        myEmail = My.child("email").getValue().toString()
                        myPasswd = My.child("pwd").getValue().toString()
                        value = iter
                        count++
                    }
                    if(email.text.toString().equals(myEmail)){
                        passwd.setText(myPasswd)
                    } else if (count > childNum) {
                        passwd.setText("등록된 이메일이 아닙니다")
                    }
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // return super.onSupportNavigateUp()
        onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
        return true
    }

}
