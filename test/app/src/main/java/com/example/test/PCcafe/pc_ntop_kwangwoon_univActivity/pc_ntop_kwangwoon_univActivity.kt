package com.example.test.PCcafe.pc_ntop_kwangwoon_univActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.PCcafe.pc_3pop_kwangwoon_univActivity.seat1_3pop_kwangwoon_univActivity
import com.example.test.PCcafe.pc_3pop_kwangwoon_univActivity.seat2_3pop_kwangwoon_univActivity
import com.example.test.PCcafe.pc_3pop_kwangwoon_univActivity.seat3_3pop_kwangwoon_univActivity
import com.example.test.PCcafe.pc_3pop_kwangwoon_univActivity.seat4_3pop_kwangwoon_univActivity
import com.example.test.R
import kotlinx.android.synthetic.main.pc_3pop_kwangwoon_univ.*

class pc_ntop_kwangwoon_univActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.pc_ntop_kwangwoon_univ)

        val actionBar = supportActionBar

        actionBar!!.title = "NTop 광운대"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


        seat1.setOnClickListener {
            val intent = Intent(this, seat1_ntop_kwangwoon_univActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        seat2.setOnClickListener {
            val intent = Intent(this, seat2_ntop_kwangwoon_univActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        seat3.setOnClickListener {
            val intent = Intent(this, seat3_ntop_kwangwoon_univActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        seat4.setOnClickListener {
            val intent = Intent(this, seat4_ntop_kwangwoon_univActivity::class.java)
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
