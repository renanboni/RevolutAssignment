package com.example.revolutassingment

import com.example.revolutassingment.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class RevolutApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .factory()
            .create(applicationContext)
    }
}