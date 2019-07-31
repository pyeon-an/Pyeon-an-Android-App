package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.my_info.*

class My_infoActivity : AppCompatActivity() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    val myUid=auth.currentUser?.uid.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.my_info)

        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("member")
        myRef.child(myUid).child("나이").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                age.text = "$value"
            }
        })
        myRef.child(myUid).child("이메일").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                email.text = "$value"
            }
        })
        myRef.child(myUid).child("닉네임").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                nickname.text = "$value"
            }
        })

        back.setOnClickListener {
         //   val intent = Intent(this, menual::class.java)
         //   startActivity(intent)
            finish()
        }
    }
}
