package di

import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.reflect.KClass

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class IosApplicationComponent : SharedApplicationComponent {
    companion object {
        fun create() = IosApplicationComponent::class.createComponent()
    }
}

/**
 * The `actual fun` will be generated for each iOS specific target. See [MergeComponent] for
 * more details.
 */
@MergeComponent.CreateComponent
expect fun KClass<IosApplicationComponent>.createComponent(): IosApplicationComponent
