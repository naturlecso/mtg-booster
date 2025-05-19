import SwiftUI
import KotlinModules

struct RootView: UIViewControllerRepresentable {
    let root: RootPresenter

    func makeUIViewController(context: Context) -> some UIViewController {
        ViewControllerKt.rootViewController(root: root)
    }
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}

@main
struct iosApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

    var body: some Scene {
        WindowGroup {
            RootView(root: appDelegate.root).ignoresSafeArea(.all)
        }
    }
}
