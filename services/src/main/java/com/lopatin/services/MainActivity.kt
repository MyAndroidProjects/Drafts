package com.lopatin.services

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomappbar.BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
import com.google.android.material.bottomappbar.BottomAppBar.FAB_ALIGNMENT_MODE_END
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import android.app.PendingIntent
import android.content.pm.PackageManager
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import androidx.annotation.Keep
import com.lopatin.model.eventbus.EventConnectionWithServer
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {
    val CHECK_CONNECTION_CODE = 101
    val serviceCoroutineContext = Dispatchers.Default + Job()
    val serviceCoroutineScope = CoroutineScope(serviceCoroutineContext)
    var serviceIsConnect = false
    lateinit var serviceConnection: ServiceConnection
    lateinit var intentCheckInternetBindingService: Intent
    lateinit var checkInternetBindingService: CheckInternetBindingService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intentCheckInternetBindingService = Intent(this, CheckInternetBindingService::class.java)
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, binder: IBinder) {
                Log.d("logService", "MainActivity onServiceConnected")
                checkInternetBindingService =
                    (binder as CheckInternetBindingService.CheckInternetBindingServiceBinder).getService()
                serviceIsConnect = true
            }

            override fun onServiceDisconnected(name: ComponentName) {
                Log.d("logService", "MainActivity onServiceDisconnected")
                serviceIsConnect = false
            }
        }
    }

    private val coroutineContextFab = Dispatchers.Default + Job()
    private val coroutineScopeFab = CoroutineScope(coroutineContextFab)

    override fun onStart() {
        super.onStart()
        Log.d("logService", "onStart")
//        checkConnectServicePermission()
        EventBus.getDefault().register(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("logService", "Build.VERSION.SDK_INT")
            if (checkSelfPermission(Manifest.permission.CHECK_CONNECTION) == PackageManager.PERMISSION_GRANTED) {
                Log.d("logService", "CHECK_CONNECTION) == PackageManager.PERMISSION_GRANTED")
                startSendDataToActivityByIntentService()
            } else {
                Log.d("logService", "CHECK_CONNECTION  else")
                checkConnectServicePermission()
            }
        } else {
            Log.d("logService", "Build.VERSION.SDK_INT else")
            startSendDataToActivityByIntentService()
        }
//        startSendDataToActivityByIntentService()
        bottomAppBar.fabAlignmentMode = FAB_ALIGNMENT_MODE_CENTER
        bottomAppBar.fabAnimationMode = BottomAppBar.FAB_ANIMATION_MODE_SLIDE
//        startFabMoving()

        buttonStartService.setOnClickListener {
            Log.d("logService", "buttonStartService")
            startService(Intent(this, CheckInternetService::class.java))

        }
        buttonStopService.setOnClickListener {
            Log.d("logService", "buttonStopService")
            stopService(Intent(this, CheckInternetService::class.java))
        }

        buttonBindService.setOnClickListener {
            Log.d("logService", "buttonBindService")
            bindService(intentCheckInternetBindingService, serviceConnection, BIND_AUTO_CREATE)
        }

        buttonUnbindService.setOnClickListener {
            Log.d("logService", "buttonUnbindService")
            unbindService(serviceConnection)
        }

        buttonBindServiceStartCheck.setOnClickListener {
            Log.d("logService", "buttonBindServiceStartCheck")
            checkInternetBindingService.startChecking()
        }

        buttonBindServiceStopCheck.setOnClickListener {
            Log.d("logService", "buttonBindServiceStopCheck")
            checkInternetBindingService.stopChecking()
        }
    }

    private fun startFabMoving() {
        coroutineScopeFab.launch {
            while (true) {
                delay(1000)
                withContext(Dispatchers.Main) {
                    bottomAppBar.fabAlignmentMode = FAB_ALIGNMENT_MODE_END
                }

                delay(1000)
                withContext(Dispatchers.Main) {
                    bottomAppBar.fabAlignmentMode = FAB_ALIGNMENT_MODE_CENTER
                    bottomAppBar.fabAnimationMode = BottomAppBar.FAB_ANIMATION_MODE_SCALE
                }

                delay(1000)
                withContext(Dispatchers.Main) {
                    bottomAppBar.fabAlignmentMode = FAB_ALIGNMENT_MODE_END
                }

                delay(1000)
                withContext(Dispatchers.Main) {
                    bottomAppBar.fabAlignmentMode = FAB_ALIGNMENT_MODE_CENTER
                    bottomAppBar.fabAnimationMode = BottomAppBar.FAB_ANIMATION_MODE_SLIDE
                }
            }
        }
    }

    fun startSendDataToActivityByIntentService() {
        startService(Intent(this, SendDataToActivityByIntentService::class.java))
    }

    @Keep
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onEvent(event: EventConnectionWithServer) {
        Log.d("logService", "onEvent get event")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStop() {
        coroutineContextFab.cancelChildren()
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onDestroy() {
//        stopService(Intent(this, CheckInternetService::class.java))
        super.onDestroy()
    }

    private fun checkConnectServicePermission() {
        Log.d("logService", "checkConnectServicePermission")
        val permissionList = ArrayList<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CHECK_CONNECTION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.CHECK_CONNECTION)
                requestPermissions(permissionList.toTypedArray(), CHECK_CONNECTION_CODE)
            }else{

            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d("logService", "onRequestPermissionsResult requestCode $requestCode")
        when (requestCode) {
            CHECK_CONNECTION_CODE -> {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Log.d("logService", "onRequestPermissionsResult PERMISSION_GRANTED CHECK_CONNECTION_CODE $requestCode")
                    startSendDataToActivityByIntentService()
                }else{
                    Log.d("logService", "onRequestPermissionsResult PERMISSION_DENIED CHECK_CONNECTION_CODE $requestCode")
                    checkConnectServicePermission()
                }


            }
        }
    }
}