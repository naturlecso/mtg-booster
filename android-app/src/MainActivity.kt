package com.naturlecso.mtgbooster

import Screen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.defaultComponentContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val applicationComponent = (applicationContext as MtgBoosterApplication).component
        val root = applicationComponent.rootPresenterFactory.create(defaultComponentContext())

        setContent {
            Screen(presenter = root)
        }
    }
}
