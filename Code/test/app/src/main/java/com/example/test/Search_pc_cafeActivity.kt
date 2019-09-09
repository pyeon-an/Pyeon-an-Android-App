package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.my_info.*
import kotlinx.android.synthetic.main.search_pc_cafe.*

class Search_pc_cafeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.search_pc_cafe)

        // 툴바 추가
        val actionBar = supportActionBar

        actionBar!!.title = "PC방 검색"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        // 툴바 추가 완료

        auth = FirebaseAuth.getInstance()
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("PCcafe")

        val pc_cafe = findViewById<EditText>(R.id.pc_cafe)
        val local = findViewById<EditText>(R.id.local)
        var check=0

        searching.setOnClickListener {
       //     Toast.makeText(this, "aaaaaaa", Toast.LENGTH_SHORT).show()
            if(pc_cafe.text.toString().equals("") || local.text.toString().equals("")){
                Toast.makeText(this, "PC카페와 지점을 입력해주세요.", Toast.LENGTH_SHORT).show()

            } else{
                myRef.child(pc_cafe.text.toString()).addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                        val value = p0?.value
                        val pc_info="$value"
                        if(pc_info.toString().equals("null")) {
                            pc_cafe_sel.setText("등록된 PC카페가 아닙니다.")
                        } else{
                            myRef.child(pc_cafe.text.toString()).child(local.text.toString()).addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(p0: DatabaseError) {
                                }
                                override fun onDataChange(p0: DataSnapshot) {
                                    val value = p0?.value
                                    val pc_info="$value"
                                    if(pc_info.toString().equals("null")) {
                                        pc_cafe_sel.setText("등록된 지점이 아닙니다.")
                                    }else{
                                        check=1
                                        pc_cafe_sel.setText(pc_cafe.text.toString() + "(" + local.text.toString() + ")")
                                    }
                                    //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                                }
                            })
                        }


                    }
                })
            }
        }

        select.setOnClickListener {
            if(pc_cafe.text.toString().equals("") || local.text.toString().equals("")){
                Toast.makeText(this, "PC카페를 찾아주세요.", Toast.LENGTH_SHORT).show()

            } else if(check==0){
                Toast.makeText(this, "PC카페를 먼저 찾아주세요.", Toast.LENGTH_SHORT).show()

            } else {
                val memberRef = database.getReference("member").child(auth.currentUser?.uid.toString())
                memberRef.child("PCcafe").setValue(pc_cafe.text.toString())
                memberRef.child("지점").setValue(local.text.toString())
                Toast.makeText(this, "PC카페가 설정되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        }

        list.setOnClickListener {
            if(check!=1){
                Toast.makeText(this, "PC카페를 먼저 설정해주세요.", Toast.LENGTH_SHORT).show()
            } else{
                val memberRef = database.getReference("member").child(auth.currentUser?.uid.toString())
                memberRef.child("즐겨찾기").child(pc_cafe.text.toString()+" "+local.text.toString()).setValue(pc_cafe.text.toString()+" "+local.text.toString())
                Toast.makeText(this, "PC카페가 즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show()
            }
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
