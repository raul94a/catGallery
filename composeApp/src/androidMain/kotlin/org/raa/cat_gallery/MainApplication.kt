package org.raa.cat_gallery

import android.app.Application
import di.networkModule

import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{

            modules(networkModule)

        }
    }
}