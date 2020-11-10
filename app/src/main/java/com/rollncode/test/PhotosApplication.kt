package com.rollncode.test

import android.app.Application
import com.rollncode.test.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PhotosApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}