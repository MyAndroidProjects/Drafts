package com.lopatin.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import com.lopatin.model.eventbus.EventConnectionWithServer
import org.greenrobot.eventbus.EventBus


class SendDataToActivityByIntentService: Service() {
     var activityIntent : Intent? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("logService", "SendDataToActivityByIntentService onStartCommand")
        activityIntent = intent

        val handler = Handler()

        handler.postDelayed({
          EventBus.getDefault().post(EventConnectionWithServer())
        },2000)
        handler.postDelayed({
          EventBus.getDefault().post(EventConnectionWithServer())
        },4000)
        handler.postDelayed({
          EventBus.getDefault().post(EventConnectionWithServer())
        },8000)
        handler.postDelayed({
          EventBus.getDefault().post(EventConnectionWithServer())
        },10000)
        handler.postDelayed({
          EventBus.getDefault().post(EventConnectionWithServer())
        },12000)

        return super.onStartCommand(intent, flags, startId)

    }
}