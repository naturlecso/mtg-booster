package com.naturlecso.mtgbooster

import android.app.Application
import di.AndroidApplicationComponent
import di.create
import utils.ApplicationReference

class MtgBoosterApplication : Application() {
    private val applicationReference = ApplicationReference.apply {
        set(this@MtgBoosterApplication)
    }
    val component: AndroidApplicationComponent by lazy {
        AndroidApplicationComponent::class.create(
            applicationReference
        )
    }
}
