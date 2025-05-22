package com.naturlecso.mtgbooster

import Screen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext

class MainActivity : ComponentActivity() {
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
