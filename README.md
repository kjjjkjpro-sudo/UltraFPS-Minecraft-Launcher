# Ultra FPS Launcher

This repository contains a starter Android launcher app for a Minecraft launcher focused on FPS-friendly settings, profile management, and modloader support.

What is included:
- Modern Android UI built with Jetpack Compose
- Profile cards for Fabric, Forge, Quilt, NeoForge, and vanilla installs
- Version catalog covering multiple Minecraft releases
- Basic launcher screen with launch controls and performance tags
- Clean project structure for expanding into a real Android launcher integration

Current status:
- This is a foundation project and not a complete Minecraft runtime launcher.
- It is designed as a usable UI skeleton and architecture starting point.
- Full runtime install, Java management, game launch, and modloader-specific packaging still need real integrations for production use.

Project layout:
- `app/src/main/java/com/ultrafps/launcher` — app source
- `app/src/main/res` — resources, XML, and manifest
- `build.gradle.kts` / `settings.gradle.kts` — Gradle setup

How to open:
1. Install Android Studio Ladybug or newer
2. Open the repository folder
3. Let Gradle sync
4. Build and run on a physical Android device or emulator

Recommended next steps:
- Add a real Java runtime selector and local install detection
- Implement modloader-specific installation logic for Fabric/Forge/Quilt/NeoForge
- Add profile management, version JSON parsing, and the actual game launch pipeline
- Add safe APK packaging rules and app permissions for local game data access

This repository intentionally provides a strong starting point without claiming to ship a licensed Minecraft launcher or game client.
