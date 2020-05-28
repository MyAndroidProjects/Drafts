package com.lopatin.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast


class SecondBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        StringBuilder().apply {
            append("SecondBroadcastReceiver   ")
            append("Action: ${intent?.action}\n")
            append("URI: ${intent?.toUri(Intent.URI_INTENT_SCHEME)}\n")
            toString().also { log ->
                Log.d("logBroadcast", log)
                Toast.makeText(context, log, Toast.LENGTH_LONG).show()
            }
        }
        val intentToMainActivity = Intent(context, MainActivity::class.java)
        intentToMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context ?: return
        context.startActivity(intentToMainActivity)
    }
}