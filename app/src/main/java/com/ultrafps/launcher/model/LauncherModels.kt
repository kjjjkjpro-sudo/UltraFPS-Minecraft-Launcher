package com.ultrafps.launcher.model

enum class ModLoader {
    VANILLA,
    FABRIC,
    FORGE,
    QUILT,
    NEO_FORGE,
    OPTIFINE,
    CUSTOM
}

enum class JavaRuntime {
    AUTO,
    JRE_17,
    JRE_21
}

data class MinecraftProfile(
    val id: String,
    val name: String,
    val version: String,
    val loader: ModLoader,
    val javaRuntime: JavaRuntime = JavaRuntime.AUTO,
    val installPath: String,
    val isFavorite: Boolean = false,
    val isEnabled: Boolean = true,
    val description: String = "Optimized profile for smooth gameplay"
)

object LauncherCatalog {
    fun supportedVersions(): List<String> = listOf(
        "1.8.9",
        "1.12.2",
        "1.16.5",
        "1.17.1",
        "1.18.2",
        "1.19.2",
        "1.20.1",
        "1.20.4",
        "1.20.6",
        "1.21.1",
        "1.21.4",
        "1.21.5"
    )

    fun supportedLoaders(): List<ModLoader> = listOf(
        ModLoader.VANILLA,
        ModLoader.FABRIC,
        ModLoader.FORGE,
        ModLoader.QUILT,
        ModLoader.NEO_FORGE,
        ModLoader.OPTIFINE,
        ModLoader.CUSTOM
    )
}
