package FormatInNotificationBar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.android.architecture.blueprints.todoapp.R

class NotificationBar:AppCompatActivity() {
    private val CHANNEL_ID = "NotificationBar"

    //通知チャンネルの作成
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "FormatNotification"
            val descriptionText = "description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        //通知を作成する前に必ず実行する
        createNotificationChannel()

        //通知を作成するボタン
        val buttonNotification: Button = findViewById(R.id.search_button)
        buttonNotification.setOnClickListener {
            // 通知タップ時の遷移先を設定
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1D2gx5p9OgrVIBCqug4Lcjqbw3NTLfDdyYyiErVU5I_U/edit#gid=0"))
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            //通知オブジェクトの作成
            var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.id.solid_notification)
                .setContentTitle("Format")
                .setContentText("適当に書いてこう")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            //通知の実施
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, builder.build())
        }
    }
}