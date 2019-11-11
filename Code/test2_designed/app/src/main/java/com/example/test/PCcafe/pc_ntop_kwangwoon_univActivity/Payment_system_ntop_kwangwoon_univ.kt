package com.example.test.PCcafe.pc_ntop_kwangwoon_univActivity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.test.MenubarActivity
import com.example.test.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.concurrent.timer

class Payment_system_ntop_kwangwoon_univ : Service() {

    private lateinit var auth: FirebaseAuth

    private var timerTask: Timer?=  null
    var check=0
    var point=0
    var cur_point=0

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")

        // Toast.makeText(baseContext, time1.toString(), Toast.LENGTH_SHORT).show()

    }

    override fun onCreate() {
        super.onCreate()
        auth = FirebaseAuth.getInstance()
        var myUid=auth.currentUser?.uid.toString()
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("PCcafe").child("3POP").child("광운대")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //TODO : 서비스 처음 시작시 할 동작 정의.
        createNotification()

        auth = FirebaseAuth.getInstance()
        var myUid=auth.currentUser?.uid.toString()
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("PCcafe").child("NTop").child("광운대").child("요금제").child("6")
        val memberRef : DatabaseReference = database.getReference("member").child(myUid).child("point")
        if(point==0) {
            memberRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                    val value = p0?.value

                    if ((value.toString().toInt() > 0 && point == 0)) {
                        point = value.toString().toInt()
                        memberRef.setValue((point + 1).toString())
                        check = 1
                    } else if((value.toString().toInt()>point+3)){
                        //    timerTask!!.cancel()
                        point = value.toString().toInt()
                        cur_point = value.toString().toInt()
                        //        memberRef.setValue((point).toString())
                        //        check = 1
                    } else if(check==1) {
                        timerTask = timer(period = 1000) {
                            if (point <= 0) {
                                stopSelf()
                                timerTask!!.cancel()
                            } else {
                                cur_point = point
                                point = cur_point - 1
                                memberRef.setValue(point.toString())
                            }
                        }
                        check = 0
                    }
                }
            })
        }


        // Toast.makeText(baseContext, time1.toString(), Toast.LENGTH_SHORT).show()
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        //TODO : 서비스 종료시 할 것들
        super.onDestroy()

        /*     val intent = Intent(this, Payment_system::class.java)
             stopService(intent)
             auth = FirebaseAuth.getInstance()
             var myUid=auth.currentUser?.uid.toString()
             val database: FirebaseDatabase = FirebaseDatabase.getInstance()
             val memberRef : DatabaseReference = database.getReference("member").child(myUid).child("point")


                 timerTask = timer(period = 2000) {
                     if (point <= 0) {
                         stopSelf()
                         timerTask!!.cancel()
                     } else {
                         cur_point = point
                         point = cur_point - 1
                         memberRef.setValue(point.toString())
                     }
                 }*/
        //   removeNotification()
        timerTask!!.cancel()
    }
    private fun createNotification() {

        val builder = NotificationCompat.Builder(this, "default")
        val pendingIntent: PendingIntent =
            Intent(this, MenubarActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }
        //builder.setSmallIcon(android.R.mipmap.ic_launcher)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle("P--Y")
        builder.setContentText("point")
        builder.setContentIntent(pendingIntent)
        builder.color = Color.RED
        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true)
        startForeground(2,builder.build())


        // 알림 표시
        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "default",
                    "기본 채널",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값

        //notificationManager.notify(1, builder.build())

    }

    private fun removeNotification() {


        // Notification 제거
        NotificationManagerCompat.from(this).cancel(2)
    }
}
