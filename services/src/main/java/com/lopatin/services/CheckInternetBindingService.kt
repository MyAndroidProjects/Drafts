package com.lopatin.services

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel
import android.util.Log
import kotlinx.coroutines.*
import java.io.FileDescriptor
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class CheckInternetBindingService : Service() {

    private val defaultRepeatTime = 1000 * 1 * 10L

    private val serviceCoroutineContext = Dispatchers.Default + Job()
    private val serviceCoroutineScope = CoroutineScope(serviceCoroutineContext)

    private val instance = this

    override fun onBind(intent: Intent?): IBinder? {
        return CheckInternetBindingServiceBinder()
    }

    fun startChecking() {
        Log.d("logService", "onStartCommand")
        serviceCoroutineScope.launch {
            while (true) {
                if (checkInternetBySocket()) {
//                    sendGoogleNotification()
                    withContext(Dispatchers.Main) {
                        Log.d("logService", "checkInternetBySocket true")
                    }
                }
                delay(defaultRepeatTime)
                if (checkInternetByPing()) {
//                    sendSianieNotification()
                    withContext(Dispatchers.Main) {
                        Log.d("logService", "checkInternetByPin true")
                    }
                }
                delay(defaultRepeatTime)
            }

        }
    }

    fun stopChecking() {
        serviceCoroutineContext.cancelChildren()
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


    inner class CheckInternetBindingServiceBinder : Binder() {
        fun getService(): CheckInternetBindingService {
            return instance
        }
    }
}