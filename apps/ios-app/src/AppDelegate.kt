// wrapper for building components for AppDelegate.swift as
// kotlin classes can't be exported to swift in amper yet

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import di.IosApplicationComponent

fun createRootPresenter() = IosApplicationComponent.Companion
    .create()
    .rootPresenterFactory
    .create(
        DefaultComponentContext(ApplicationLifecycle()
        )
    )
