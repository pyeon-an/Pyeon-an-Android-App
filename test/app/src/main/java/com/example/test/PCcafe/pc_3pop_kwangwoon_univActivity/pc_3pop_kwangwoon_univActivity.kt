package com.example.test.PCcafe.pc_3pop_kwangwoon_univActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.R
import kotlinx.android.synthetic.main.pc_3pop_kwangwoon_univ.*

class pc_3pop_kwangwoon_univActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.pc_3pop_kwangwoon_univ)

        seat1.setOnClickListener {
            val intent = Intent(this, seat1_3pop_kwangwoon_univActivity::class.java)
            startActivity(intent)
        }

        seat2.setOnClickListener {
            val intent = Intent(this, seat2_3pop_kwangwoon_univActivity::class.java)
            startActivity(intent)
        }

        back.setOnClickListener {
            System.runFinalization()
            finish()
        }
    }
}
