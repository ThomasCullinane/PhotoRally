package org.wit.photorally.main

import android.app.Application
import org.wit.photorally.models.PhotoRallyJSONStore
import org.wit.photorally.models.PhotoRallyStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var placemarks: PhotoRallyStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        placemarks = PhotoRallyJSONStore(applicationContext)
        i("PhotoRally started")
    }
}