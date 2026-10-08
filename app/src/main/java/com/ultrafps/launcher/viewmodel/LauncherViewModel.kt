package com.ultrafps.launcher.viewmodel

import androidx.lifecycle.ViewModel
import com.ultrafps.launcher.data.ProfileRepository
import com.ultrafps.launcher.model.LauncherSettings
import com.ultrafps.launcher.model.MinecraftProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LauncherViewModel : ViewModel() {
    private val repository = ProfileRepository()

    private val _profiles = MutableStateFlow(repository.getDefaultProfiles())
    val profiles: StateFlow<List<MinecraftProfile>> = _profiles.asStateFlow()

    private val _settings = MutableStateFlow(repository.getDefaultSettings())
    val settings: StateFlow<LauncherSettings> = _settings.asStateFlow()

    fun toggleProfileEnabled(profileId: String) {
        _profiles.value = _profiles.value.map { profile ->
            if (profile.id == profileId) {
                profile.copy(isEnabled = !profile.isEnabled)
            } else {
                profile
            }
        }
    }

    fun toggleProfileFavorite(profileId: String) {
        _profiles.value = _profiles.value.map { profile ->
            if (profile.id == profileId) {
                profile.copy(isFavorite = !profile.isFavorite)
            } else {
                profile
            }
        }
    }

    fun updateSettings(newSettings: LauncherSettings) {
        _settings.value = newSettings
    }

    fun launchProfile(profile: MinecraftProfile) {
        // Placeholder for real launch pipeline.
        // Real implementation would validate Java version, parse modloader metadata,
        // and launch the correct game distribution or modloader runtime.
    }
}
