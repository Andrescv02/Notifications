package com.example.notifications


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    private val CHANNELID="channel id example"
    private val notificationid= 101
    lateinit var titlemessage: String
    lateinit var button: Button
    lateinit var text: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()
        text=findViewById<EditText>(R.id.text_input)
        button=findViewById<Button>(R.id.button)

        text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                titlemessage=text.text.toString().trim()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        button.setOnClickListener{
            sendNotification()
        }



    }
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val name = "Notification title"
            val descriptionText= "Notification Description"
            val importance= NotificationManager.IMPORTANCE_DEFAULT
            val channel= NotificationChannel(CHANNELID,name,importance).apply {
                description=descriptionText
            }
            val notificationManager: NotificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun sendNotification(){
        val intent= Intent(this,MainActivity::class.java).apply{
            flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent=PendingIntent.getActivity(this,0,intent,0)


        val bitmapLargeIcon= BitmapFactory.decodeResource(applicationContext.resources,R.drawable.ic_launcher_background)

        val builder= NotificationCompat.Builder(this, CHANNELID)
            .setSmallIcon(R.drawable.ic_feedback)
            .setContentTitle(titlemessage)
            .setContentText("Example description")
            .setLargeIcon(bitmapLargeIcon)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_snooze, getString(R.string.snooze),
                pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)){
            notify(notificationid,builder.build())
        }
    }


}