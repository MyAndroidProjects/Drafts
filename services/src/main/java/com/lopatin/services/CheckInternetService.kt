package com.lopatin.services

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket


class CheckInternetService : Service() {

    /**
     *  для неявного вызова службы нужно прописать SERVICE_ACTION, идентифицирующую службу
     */
    val SERVICE_ACTION = "com.lopatin.services.CheckInternetService"

    companion object{
       const val ACTION_CHECK_INTERNET_SERVICE = "com.lopatin.services.CheckInternetService"
    }

    private val defaultRepeatTime = 1000 * 1 * 10L
    private var notificationManager: NotificationManager? = null

    private val serviceCoroutineContext = Dispatchers.Default + Job()
    private val serviceCoroutineScope = CoroutineScope(serviceCoroutineContext)

    private val notificationPingGoogleChannelId = "GoogleChannelId"
    private val notificationPingSianieChannelId = "SianieChannelId"

    private var notificationPingGoogleId = 0
    private var notificationPingSianieId = 0

    private val notificationPingGoogleTag = "notificationPingGoogle"
    private val notificationPingSianieTag = "notificationPingSianie"
    private val notificationPingGoogleDescription = "Ping Google"
    private val notificationPingSianieDescription = "Ping Sianie"
    private val notificationPingGoogleName = "Ping Google channel"
    private val notificationPingSianieName = "Ping Sianie channel"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val googleChannel =
                notificationManager?.getNotificationChannel(notificationPingGoogleChannelId)
            if (googleChannel == null) {
                val channel = NotificationChannel(
                    notificationPingGoogleChannelId, notificationPingGoogleName,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.description = notificationPingGoogleDescription
                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.enableVibration(false)
                notificationManager?.createNotificationChannel(channel)
            }
            val sianieChannel =
                notificationManager?.getNotificationChannel(notificationPingGoogleChannelId)
            if (sianieChannel == null) {
                val channel = NotificationChannel(
                    notificationPingSianieChannelId, notificationPingSianieName,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.description = notificationPingSianieDescription
                channel.enableLights(true)
                channel.lightColor = Color.GREEN
                channel.enableVibration(true)
                notificationManager?.createNotificationChannel(channel)
            }
        }

        Log.d("logService", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val repeatTime = intent?.getLongExtra("repeatTime", defaultRepeatTime) ?: defaultRepeatTime
        Log.d("logService", "onStartCommand")
        serviceCoroutineScope.launch {
            while (true) {
                if (checkInternetBySocket()) {
                    sendGoogleNotification()
                    withContext(Dispatchers.Main) {
                        Log.d("logService", "checkInternetBySocket true")
                    }
                }
                delay(repeatTime)
                if (checkInternetByPing()) {
                    sendSianieNotification()
                    withContext(Dispatchers.Main) {
                        Log.d("logService", "checkInternetByPin true")
                    }
                }
                delay(repeatTime)
            }

        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d("logService", "onDestroy")
        serviceCoroutineContext.cancel()
        super.onDestroy()
    }

    private suspend fun checkInternetBySocket(): Boolean {
        Log.d("logService", "checkInternet start")

        return withContext(Dispatchers.Main) {
            var isConnect = false
            try {
                withContext(Dispatchers.Default) {
                    // Connect to Google DNS to check for connection
                    val timeoutMs = 15000
                    val socket = Socket()
                    // создаем сокет. 8.8.8.8 - гугловский адрес
                    val socketAddress = InetSocketAddress("8.8.8.8", 53)
                    socket.connect(socketAddress, timeoutMs)
                    socket.close()
                }
                isConnect = true
            } catch (e: IOException) {
                Log.d("logService", "IOException $e")
            } catch (e: Exception) {
                Log.d("logService", "Exception $e")
            }

            Log.d("logService", "checkInternet isConnect $isConnect")
            return@withContext isConnect
        }
    }

    private suspend fun checkInternetByPing(): Boolean {
        return withContext(Dispatchers.Main) {
            var isConnect = false
            try {
                withContext(Dispatchers.Default) {
                    val runtime = Runtime.getRuntime()
                    // пингуем гугл
                    //                val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
                    //   пингуем сияние
                    val ipProcess =
//                        runtime.exec("/system/bin/ping -c 1 176.53.160.78")
//
                        runtime.exec("/system/bin/ping -c 1 sianie.webtm.ru")
                    Log.d("logService", "!!!!!!!!!!!!!!!!!!  ping sianie.webtm.ru")
                    val exitValue = ipProcess.waitFor()
                    isConnect = exitValue == 0
                }
                Log.d("logService", "!!!!!!!!!!!!!!!!!!  isConnect = $isConnect")
            } catch (e: IOException) {
                Log.d("logService", "!!!!!!!!!!!!!!!!!!  exitValue IOException $e")
                e.printStackTrace()
            } catch (e: InterruptedException) {
                Log.d("logService", "!!!!!!!!!!!!!!!!!!  exitValue InterruptedException $e")
                e.printStackTrace()
            } catch (e: Exception) {
                Log.d("logService", "!!!!!!!!!!!!!!!!!!  exitValue Exception $e")
            }
            return@withContext isConnect
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    private fun sendGoogleNotification() {
        val intent = Intent(this, MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notification =
            NotificationCompat.Builder(this, notificationPingGoogleChannelId)
                .setSmallIcon(R.drawable.ic_menu_mylocation)
                .setContentTitle("google ping")
                .setContentText("google success")
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .build()

        // отправляем
        notificationManager?.notify(notificationPingGoogleId, notification)
        notificationPingGoogleId++
    }

    private fun sendSianieNotification() {
        val intent = Intent(this, MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notification =
            NotificationCompat.Builder(this, notificationPingGoogleChannelId)
                .setSmallIcon(R.drawable.ic_menu_mylocation)
                .setContentTitle("Sianie ping")
                .setContentText("sianie success")
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .build()

        // отправляем
        notificationManager?.notify(notificationPingSianieId, notification)
    }

}