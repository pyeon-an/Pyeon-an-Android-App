package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.charge.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChargeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.charge)

        //툴바 추가
        val actionBar = supportActionBar

        actionBar!!.title = "포인트 충전"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val point = findViewById<EditText>(R.id.point)
        var sum = 0
        auth = FirebaseAuth.getInstance()

        charge.setOnClickListener {
            if (point.text.toString().isNullOrBlank() == true) {
                Toast.makeText(this, "포인트를 올바르게 입력해주세요!", Toast.LENGTH_SHORT).show()

            } else if (point.text.toString().toInt() < 500) {
                Toast.makeText(this, "최소 포인트 이상 충전해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                val myRef: DatabaseReference =
                    database.getReference("member").child(auth.currentUser?.uid.toString()).child("point")
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                        val value = p0?.value

                        sum = value.toString().toInt() + point.text.toString().toInt()
                        myRef.setValue(sum.toString())
                        point.setText("0")
                    }
                })

                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val formatted = current.format(formatter)
                val logRef: DatabaseReference =
                    database.getReference("log").child(formatted.toString()).child(auth.currentUser?.uid.toString())

                logRef.setValue(point.text.toString().toInt().toString() + "  point")

                Toast.makeText(this, "포인트가 충전되었습니다", Toast.LENGTH_SHORT).show()
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
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
