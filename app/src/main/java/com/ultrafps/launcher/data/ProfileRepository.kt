package com.ultrafps.launcher.data

import com.ultrafps.launcher.model.JavaRuntime
import com.ultrafps.launcher.model.LauncherSettings
import com.ultrafps.launcher.model.MinecraftProfile
import com.ultrafps.launcher.model.ModLoader
import com.ultrafps.launcher.model.PerformanceMode

class ProfileRepository {
    fun getDefaultProfiles(): List<MinecraftProfile> = listOf(
        MinecraftProfile(
            id = "fabric-1201",
            name = "FPS Fabric",
            version = "1.20.1",
            loader = ModLoader.FABRIC,
            javaRuntime = JavaRuntime.AUTO,
            installPath = "/storage/emulated/0/games/minecraft/fabric",
            isFavorite = true,
            performanceMode = PerformanceMode.ULTRA,
            description = "Low-latency Fabric profile focused on smooth frame pacing"
        ),
        MinecraftProfile(
            id = "quilt-1215",
            name = "Quilt Ultra",
            version = "1.21.5",
            loader = ModLoader.QUILT,
            javaRuntime = JavaRuntime.JRE_21,
            installPath = "/storage/emulated/0/games/minecraft/quilt",
            performanceMode = PerformanceMode.HIGH,
            description = "Modern Quilt setup for high-performance builds"
        ),
        MinecraftProfile(
            id = "forge-1122",
            name = "Forge Legacy",
            version = "1.12.2",
            loader = ModLoader.FORGE,
            javaRuntime = JavaRuntime.JRE_17,
            installPath = "/storage/emulated/0/games/minecraft/forge",
            performanceMode = PerformanceMode.BALANCED,
            description = "Classic Forge support for older modpacks"
        ),
        MinecraftProfile(
            id = "vanilla-1214",
            name = "Vanilla Stable",
            version = "1.21.4",
            loader = ModLoader.VANILLA,
            installPath = "/storage/emulated/0/games/minecraft/vanilla",
            isEnabled = true,
            performanceMode = PerformanceMode.ULTRA,
            description = "Stock experience tuned for clean rendering and fast startup"
        )
    )

    fun getDefaultSettings(): LauncherSettings = LauncherSettings(
        performanceMode = PerformanceMode.ULTRA,
        memoryMb = 4096,
        useAutoJava = true,
        enableVsync = false,
        enableLowLatency = true,
        enableOptimizedRendering = true,
        maxFps = 240
    )
}
