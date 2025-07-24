package base.utils

import android.app.Application
import java.lang.ref.WeakReference

actual object ApplicationReference {
    private var value: WeakReference<Application?>? = null

    fun set(application: Application) {
        value = WeakReference(application)
    }

    fun get(): Application? {
        return value?.get()
    }
}
