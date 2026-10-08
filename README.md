# Ultra FPS Launcher

This repository is a starter Android launcher project for a Minecraft launcher designed around FPS-friendly tuning, profile management, and modloader support.

Included features:
- Modern Android launcher UI built with Jetpack Compose
- Minecraft profile cards for Vanilla, Fabric, Forge, Quilt, and NeoForge style support
- Advanced launcher home screen with performance stats and launch actions
- Profile management controls and settings panel
- Flexible architecture using a ViewModel and repository pattern
- Compatibility-ready foundation for versions such as 1.8.9, 1.12.2, 1.16.5, 1.20.1, 1.21.x

Project overview:
- `app/src/main/java/com/ultrafps/launcher` — launcher UI, models, and logic
- `app/src/main/res` — Android resources and manifest
- Gradle config — Android build setup ready for Android Studio

Key files:
- `MainActivity.kt` — app entry point
- `LauncherScreen.kt` — home, profiles, and settings UI
- `LauncherViewModel.kt` — state management for profiles and settings
- `LauncherModels.kt` — core launcher models and data classes
- `ProfileRepository.kt` — starter profile and settings data

How to run:
1. Open the repository in Android Studio
2. Sync Gradle
3. Build and run on an Android emulator or physical device

Important note:
This is a launcher app skeleton, not a fully licensed or production-ready Minecraft game client. It provides the project layout and UI foundation for further gameplay and modloader integration.
