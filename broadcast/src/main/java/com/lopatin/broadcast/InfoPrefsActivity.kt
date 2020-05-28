package com.lopatin.broadcast

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_info_prefs.*

class InfoPrefsActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private val PREF_FILE_NAME = "broadSharePref"
    private val PREF_STRING_KEY = "infoString"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_prefs)

        prefs = getSharedPreferences(
            PREF_FILE_NAME,
            Context.MODE_PRIVATE
        )
    }

    override fun onStart() {
        super.onStart()
        val str = prefs.getString(PREF_STRING_KEY, "")
        val strNotNull = str ?: ""
        Log.d("logBroadcast", "history \n$strNotNull")
        prefInfo.text = strNotNull
    }
}