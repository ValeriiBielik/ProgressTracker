package com.bielik.progresstracker.app

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import com.bielik.progresstracker.receiver.TemplatesCheckTaskReceiver
import dagger.hilt.android.HiltAndroidApp
import java.util.Calendar

@HiltAndroidApp
class ProgressTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initTemplatesCheckAlarmManager()
    }

    private fun initTemplatesCheckAlarmManager() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 5)
            set(Calendar.SECOND, 0)
            add(Calendar.DAY_OF_MONTH, 1)
        }
        val intent = Intent(applicationContext, TemplatesCheckTaskReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext, 100, intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        (getSystemService(ALARM_SERVICE) as? AlarmManager)?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

}