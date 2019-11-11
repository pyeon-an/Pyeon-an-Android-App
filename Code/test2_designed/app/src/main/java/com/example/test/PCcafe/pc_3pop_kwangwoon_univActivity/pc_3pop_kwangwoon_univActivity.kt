package com.example.test.PCcafe.pc_3pop_kwangwoon_univActivity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.pc_3pop_kwangwoon_univ.*
import kotlinx.android.synthetic.main.pc_seat_info.*


class pc_3pop_kwangwoon_univActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.pc_3pop_kwangwoon_univ)

        val actionBar = supportActionBar

        actionBar!!.title = "3POP 광운대"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


        auth = FirebaseAuth.getInstance()
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef1 : DatabaseReference = database.getReference("PCcafe").child("3POP").child("광운대").child("1")
        val myRef2 : DatabaseReference = database.getReference("PCcafe").child("3POP").child("광운대").child("2")
        val myRef3 : DatabaseReference = database.getReference("PCcafe").child("3POP").child("광운대").child("3")
        val myRef4 : DatabaseReference = database.getReference("PCcafe").child("3POP").child("광운대").child("4")

        myRef1.child("using").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value

                if (value!!.equals("O")) {
                    seat1.setBackgroundResource(R.drawable.seat_rounded)
                    //reservation.setEnabled(false)
                }
            }
        })

        myRef2.child("using").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value

                if (value!!.equals("O")) {

                    seat2.setBackgroundResource(R.drawable.seat_rounded)
                    //seat1.setEnabled(false)
                }
            }
        })

        myRef3.child("using").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value

                if (value!!.equals("O")) {

                    seat3.setBackgroundResource(R.drawable.seat_rounded)
                    //seat1.setEnabled(false)
                }
            }
        })

        myRef4.child("using").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value

                if (value!!.equals("O")) {

                    seat4.setBackgroundResource(R.drawable.seat_rounded)
                    //seat1.setEnabled(false)
                }
            }
        })
        /* memberRef.child(auth.currentUser?.uid.toString()).child("seat_using")
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
 */
        seat1.setOnClickListener {
            val intent = Intent(this, seat1_3pop_kwangwoon_univActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        seat2.setOnClickListener {
            val intent = Intent(this, seat2_3pop_kwangwoon_univActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        seat3.setOnClickListener {
            val intent = Intent(this, seat3_3pop_kwangwoon_univActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        seat4.setOnClickListener {
            val intent = Intent(this, seat4_3pop_kwangwoon_univActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
