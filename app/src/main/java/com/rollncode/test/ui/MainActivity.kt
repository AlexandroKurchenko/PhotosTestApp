package com.rollncode.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rollncode.test.R
import com.rollncode.test.ui.full.FullSizeFragment
import com.rollncode.test.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            val tag = MainFragment::class.java.canonicalName
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(), tag)

                .commit()
        }
    }

    fun openFullSizeImage(imageUrl: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FullSizeFragment.newInstance(imageUrl))
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            val tag = MainFragment::class.java.canonicalName
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }
}