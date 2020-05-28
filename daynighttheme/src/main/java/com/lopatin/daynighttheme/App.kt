package com.lopatin.daynighttheme

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*

/**
 * Включаем тему Day/Night методом AppCompatDelegate.setDefaultNightMode
 * в который передаем соответствующий параметр
 * MODE_NIGHT_NO - только день
 * MODE_NIGHT_YES - только ночь
 * MODE_NIGHT_AUTO_BATTERY - при разрядке батареи включается темная
 * MODE_NIGHT_FOLLOW_SYSTEM - определяется системой
 * MODE_NIGHT_UNSPECIFIED - неопределенный (не понятно для чего, но вроде как стоит по умолчанию)
 * MODE_NIGHT_AUTO - автоматическое переключение !! deprecate
 * MODE_NIGHT_AUTO_TIME - автоматическое переключение !! deprecate
 */

class App : Application() {
    companion object {
        var appContext: Context? = null
    }

    override fun onCreate() {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        Log.d("logTheme","getDefaultNightMode ${AppCompatDelegate.getDefaultNightMode()}")
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
//        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        Log.d("logTheme","getDefaultNightMode ${AppCompatDelegate.getDefaultNightMode()}")
        super.onCreate()
        appContext = applicationContext
    }
}