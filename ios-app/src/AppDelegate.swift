import UIKit
import KotlinModules

class AppDelegate: NSObject, UIApplicationDelegate {
    public lazy var root: RootPresenter = AppDelegateKt.createRootPresenter()
}
