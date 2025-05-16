package com.naturlecso.mtgbooster

import android.app.Application
import di.AndroidApplicationComponent
import di.create

class MtgBoosterApplication : Application() {
    val component: AndroidApplicationComponent by lazy {
        AndroidApplicationComponent::class.create()
    }
}
