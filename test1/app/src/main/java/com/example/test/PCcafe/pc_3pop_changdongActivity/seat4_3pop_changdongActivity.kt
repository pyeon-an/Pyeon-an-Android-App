package com.example.test.PCcafe.pc_3pop_changdongActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.test.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.pc_seat_info.*
import java.time.LocalDateTime
import kotlin.io.use

class seat4_3pop_changdongActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.pc_seat_info)

        val actionBar = supportActionBar

        actionBar!!.title = "3POP 창동 4번"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


        auth = FirebaseAuth.getInstance()
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("PCcafe").child("3POP").child("창동").child("4")
        var check=0

        val memberRef: DatabaseReference = database.getReference("member")
        var user = 0

        myRef.child("using").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value

                if (value!!.equals("O")) {
                    reservation.setEnabled(false)
                }
            }
        })

        memberRef.child(auth.currentUser?.uid.toString()).child("seat_using")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    val value = p0?.value
                    if ("$value".equals("") == true) {
                        user = 1
                    } else {
                        user = 0
                    }
                }
            })

        myRef.child("cpu").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                cpu.setText("$value")
            }
        })

        myRef.child("ram").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                ram.setText("$value")
            }
        })

        myRef.child("using").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                use.setText("$value")
            }
        })
/*
        close.setOnClickListener {
            finish()
        }
*/
        myRef.child("gpu").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                gpu.setText("$value")
            }
        })

        myRef.child("monitor").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                monitor.setText("$value")
            }
        })
        reservation.setOnClickListener {
            val dateAndtime: LocalDateTime = LocalDateTime.now()
            myRef.child("time_start").setValue(dateAndtime.toString())
            if (user == 1) {
                myRef.child("using").addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                        val value = p0?.value

                        if (value!!.equals("X") && check == 0 && user == 1) {
                            myRef.child("uid").setValue(auth.currentUser?.uid.toString())
                            myRef.child("using").setValue("O")
                            memberRef.child(auth.currentUser?.uid.toString()).child("seat_using").setValue("4")
                            Toast.makeText(baseContext, "예약되었습니다.", Toast.LENGTH_SHORT).show()
                            check = 1
                            finish()
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                        } else if (check == 0) {
                            Toast.makeText(baseContext, "사용 중인 자리입니다.", Toast.LENGTH_SHORT).show()
                            finish()
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                        }
                    }
                })
            } else {
                Toast.makeText(baseContext, "사용중인 자리가 있습니다.", Toast.LENGTH_SHORT).show()
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
