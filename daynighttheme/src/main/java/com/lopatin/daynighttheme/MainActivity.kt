package com.lopatin.daynighttheme

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import com.google.android.material.button.MaterialButtonToggleGroup
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*


/** вызываем AppCompatDelegate свойство delegate (getDelegate() ) у AppCompatActivity,
 * если activity наследуется от AppCompatActivity, иначе надо создавать
 * appCompatDelegate = AppCompatDelegate.create(this, null)
 * в нем выставляем режим локального режима ночи/дня
 * delegate.localNightMode = MODE_NIGHT_NO
 * delegate.localNightMode = MODE_NIGHT_YES
 * delegate.localNightMode = MODE_NIGHT_FOLLOW_SYSTEM
 * delegate.localNightMode = MODE_NIGHT_AUTO_BATTERY
 * делается в каждой activity
 * (метод AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO), ставящий режим для всего приложения ставим в классе App)
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        when (checkCurrentModeNight()) {
            DayNightMode.DAY -> toggleGroup.check(buttonDay.id)
            DayNightMode.NIGHT -> toggleGroup.check(buttonNight.id)
            DayNightMode.UNDEFINED -> toggleGroup.check(buttonDay.id)
        }

        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            Log.d("logTheme", "checkedId $checkedId")
            Log.d("logTheme", "isChecked $isChecked")
            toggleGroupChecked(group, checkedId, isChecked)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    private fun toggleGroupChecked(
        group: MaterialButtonToggleGroup?,
        checkedId: Int,
        isChecked: Boolean
    ) {
        when (checkedId) {
            buttonDay.id -> {
                if (isChecked) {
                    // светлая тема
                    Log.d("logTheme", "buttonDay $isChecked")
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                }
            }
            buttonNight.id -> {
                if (isChecked) {
                    // темная тема
                    Log.d("logTheme", "buttonNight $isChecked")
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                }
            }
            else -> {
                return
            }
        }
        if (!isChecked) {
            if (!buttonDay.isChecked && !buttonNight.isChecked) {
                Log.d("logTheme", "неопределенность")
                // неопределенность тема
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_UNSPECIFIED)
            }
        }
    }

    /**
     * Проверяет какая тема установлена в данный момент
     */
    private fun checkCurrentModeNight(): DayNightMode {
        return when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_NO -> {
                Log.d(
                    "logTheme",
                    "checkCurrentModeNight UI_MODE_NIGHT_NO"
                )
                DayNightMode.DAY
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                Log.d(
                    "logTheme",
                    "checkCurrentModeNight UI_MODE_NIGHT_YES"
                )
                DayNightMode.NIGHT
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                Log.d(
                    "logTheme",
                    "checkCurrentModeNight UI_MODE_NIGHT_UNDEFINED"
                )
                DayNightMode.UNDEFINED
            }
            else -> {
                DayNightMode.UNDEFINED
            }
        }
    }
}
