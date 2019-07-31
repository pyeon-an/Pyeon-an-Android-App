package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.menual_page.*
import kotlinx.android.synthetic.main.my_info.*

class menual : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val TAG : String = "menual"
    val myUid=mAuth.currentUser?.uid.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(myUid.equals("lDUxKZcOvaZGydeUXl4EPN4hrVV2")){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        setContentView(R.layout.menual_page)

        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("member")

        myRef.child(myUid).child("PCcafe").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                if(value.toString().equals("null")) {
                    pc_cafe.text = "설정 없음"
                } else {
                    pc_cafe.text = "$value"
                }
            }
        })
        myRef.child(myUid).child("지점").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                if(value.toString().equals("null")) {
                    local.setText("")
                } else
                     local.setText("$value")
            }
        })

        info.setOnClickListener {
            val intent = Intent(this, My_infoActivity::class.java)
            startActivity(intent)
        }

        PC_cafe.setOnClickListener {
            val intent = Intent(this, Search_pc_cafeActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            mAuth.signInWithEmailAndPassword("logout@log.out", "111111").addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(baseContext, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, menual::class.java)
                    startActivity(intent)
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

