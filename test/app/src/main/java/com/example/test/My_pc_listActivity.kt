package com.example.test

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Transformations.map
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.my_info.*
import kotlinx.android.synthetic.main.my_pc_list.*
import java.time.temporal.TemporalAdjusters.next

class My_pc_listActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    // nullsafe하게 lazy 사용
    val dataList by lazy{
        mutableListOf<String>();
    }

    var adapter : ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_pc_list)
        auth = FirebaseAuth.getInstance()
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()

        val memberRef = database.getReference("member").child(auth.currentUser?.uid.toString()).child("즐겨찾기")
        // Data 추가
        memberRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                var value = p0.children.asIterable()
                var iter = p0.children.asIterable()
                for(i in 0..p0.childrenCount.toInt()-1) {
                    dataList.add(iter.iterator().next().key.toString())
                    adapter?.notifyDataSetChanged()
                    value = iter
                    iter = value.asIterable()
                }

            }
        })

        // 간단한 adpater생성
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList)
        // adpater 설정
        lstMain.adapter = adapter
        // Item 클릭 이벤트핸들러 설정
        lstMain.setOnItemClickListener { parent, view, position, id ->
            val myRef = database.getReference("member").child(auth.currentUser?.uid.toString())
            // 삭제하기
            myRef.child("지점").setValue(dataList.get(position).toString().substringAfter(" "))
            myRef.child("PCcafe").setValue(dataList.get(position).toString().substringBefore(" "))
            adapter?.notifyDataSetChanged()
            Toast.makeText(applicationContext, "PC cafe가 설정되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

}
