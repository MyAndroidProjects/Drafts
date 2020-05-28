package com.lopatin.recycleradaptermaterialviews.ui.activities

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.lopatin.recycleradaptermaterialviews.R
import com.lopatin.recycleradaptermaterialviews.ui.fragments.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation.setOnNavigationItemSelectedListener {
            navigationItemSelectedListener(it)
        }
        bottomNavigation.selectedItemId = R.id.simpleDragDropItem
    }

    private fun navigationItemSelectedListener(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.simpleMoveSwipeItem -> {
                createFragment(MergeAdapterFragment.getInstance())
                Log.d("logItemSelected", "simpleMoveDeleteItem")
            }
            R.id.longClickMoveSwipeItem -> {
                createFragment(MoveLongClickAndSwipeFragment.getInstance())
                Log.d("logItemSelected", "longClickMoveSwipeItem")
            }
            R.id.simpleDragDropItem -> {
                createFragment(DifferentItemTypesFragment.getInstance())
                Log.d("logItemSelected", "simpleDragDropItem")
            }
            R.id.staggeredGridItem -> {
                Log.d("logItemSelected", "staggeredGridItem")
            }
            else -> {
                Log.d("logItemSelected", "else")
            }
        }

        return true
    }

    private fun createFragment(fragment: Fragment) {
        Log.d("logCreateFragment", " createFragment fragment:$fragment")
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        Log.d("logCreateFragment", " transaction.replace ")
        transaction.replace(fragmentContainerView.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onStart() {
        super.onStart()
        val rawPhone1 ="79139"
        val rawPhone2 ="791395"
        val rawPhone3 ="+791395"
        val rawPhone4 ="891395"
        val rawPhone5 ="8913953"
        val rawPhone6 ="+79139533750"
        val rawPhone7 ="+7-913-953-37-50"
        Log.d("logPhone","1 $rawPhone1 : ${formatNumberCompat(rawPhone1)}")
        Log.d("logPhone","2 $rawPhone2 : ${formatNumberCompat(rawPhone2)}")
        Log.d("logPhone","3 $rawPhone3 : ${formatNumberCompat(rawPhone3)}")
        Log.d("logPhone","4 $rawPhone4 : ${formatNumberCompat(rawPhone4)}")
        Log.d("logPhone","5 $rawPhone5 : ${formatNumberCompat(rawPhone5)}")
        Log.d("logPhone","6 $rawPhone6 : ${formatNumberCompat(rawPhone6)}")
        Log.d("logPhone","7 $rawPhone7 : ${formatNumberCompat(rawPhone7)}")

    }
    fun formatNumberCompat(rawPhone: String?, countryIso: String = ""): String {
        if (rawPhone == null) return ""

        var countryName = countryIso
        if (countryName.isBlank()) {
            countryName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Resources.getSystem().configuration.locales[0].country
            } else {
                Resources.getSystem().configuration.locale.country
            }
        }

        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            PhoneNumberUtils.formatNumber(rawPhone)
        } else {
            PhoneNumberUtils.formatNumber(rawPhone, countryName)
        }
    }
}
