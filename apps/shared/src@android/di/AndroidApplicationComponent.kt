package di

import base.utils.ApplicationReference
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

//TODO move to android-app package
@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class AndroidApplicationComponent(
//    @get:Provides val application: Application //TODO replace ApplicationReference with this
    @get:Provides val applicationReference: ApplicationReference
) : SharedApplicationComponent
