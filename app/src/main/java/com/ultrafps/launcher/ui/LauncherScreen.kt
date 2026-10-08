package com.ultrafps.launcher.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ultrafps.launcher.model.LauncherSettings
import com.ultrafps.launcher.model.LauncherCatalog
import com.ultrafps.launcher.model.MinecraftProfile
import com.ultrafps.launcher.model.PerformanceMode
import com.ultrafps.launcher.viewmodel.LauncherViewModel

@Composable
fun LauncherApp(viewModel: LauncherViewModel = viewModel()) {
    val profiles by viewModel.profiles.collectAsState()
    val settings by viewModel.settings.collectAsState()

    LauncherScreen(
        profiles = profiles,
        settings = settings,
        onToggleProfile = viewModel::toggleProfileEnabled,
        onToggleFavorite = viewModel::toggleProfileFavorite,
        onLaunch = viewModel::launchProfile,
        onUpdateSettings = viewModel::updateSettings
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LauncherScreen(
    profiles: List<MinecraftProfile>,
    settings: LauncherSettings,
    onToggleProfile: (String) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onLaunch: (MinecraftProfile) -> Unit,
    onUpdateSettings: (LauncherSettings) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ultra FPS Launcher",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF08111F),
                            Color(0xFF0E1C2F),
                            Color(0xFF111827)
                        )
                    )
                )
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) { Text("Home") }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) { Text("Profiles") }
                Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }) { Text("Settings") }
            }

            when (selectedTab) {
                0 -> HomeScreenContent(settings = settings, profiles = profiles, onLaunch = onLaunch)
                1 -> ProfilesScreenContent(
                    profiles = profiles,
                    onToggleProfile = onToggleProfile,
                    onToggleFavorite = onToggleFavorite,
                    onLaunch = onLaunch
                )
                2 -> SettingsScreenContent(settings, onUpdateSettings)
            }
        }
    }
}

@Composable
private fun HomeScreenContent(
    settings: LauncherSettings,
    profiles: List<MinecraftProfile>,
    onLaunch: (MinecraftProfile) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        HeroPanel(settings = settings)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuickTag("FPS Boost", true)
            QuickTag("Auto Java", settings.useAutoJava)
            QuickTag("Low Latency", settings.enableLowLatency)
        }

        Text(
            text = "Ready profiles",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        val readyProfiles = profiles.filter { it.isEnabled }
        if (readyProfiles.isEmpty()) {
            Text("No active profile selected.", color = Color(0xFFCBD5E1))
        } else {
            readyProfiles.take(2).forEach { profile ->
                ProfileRow(
                    profile = profile,
                    onToggleFavorite = {},
                    onToggleProfile = {},
                    onLaunch = { onLaunch(profile) }
                )
            }
        }
    }
}

@Composable
private fun ProfilesScreenContent(
    profiles: List<MinecraftProfile>,
    onToggleProfile: (String) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onLaunch: (MinecraftProfile) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(profiles) { profile ->
            ProfileRow(
                profile = profile,
                onToggleFavorite = { onToggleFavorite(profile.id) },
                onToggleProfile = { onToggleProfile(profile.id) },
                onLaunch = { onLaunch(profile) }
            )
        }
    }
}

@Composable
private fun SettingsScreenContent(
    settings: LauncherSettings,
    onUpdateSettings: (LauncherSettings) -> Unit
) {
    var memoryValue by remember { mutableIntStateOf(settings.memoryMb) }
    var fpsValue by remember { mutableIntStateOf(settings.maxFps) }
    var vSync by remember { mutableStateOf(settings.enableVsync) }
    var lowLatency by remember { mutableStateOf(settings.enableLowLatency) }
    var optimizedRendering by remember { mutableStateOf(settings.enableOptimizedRendering) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF101A2A)),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Performance settings", color = Color.White, fontWeight = FontWeight.Bold)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("VSync", color = Color(0xFFCBD5E1))
                    Switch(checked = vSync, onCheckedChange = {
                        vSync = it
                        onUpdateSettings(settings.copy(enableVsync = it))
                    })
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Low latency", color = Color(0xFFCBD5E1))
                    Switch(checked = lowLatency, onCheckedChange = {
                        lowLatency = it
                        onUpdateSettings(settings.copy(enableLowLatency = it))
                    })
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Optimized rendering", color = Color(0xFFCBD5E1))
                    Switch(checked = optimizedRendering, onCheckedChange = {
                        optimizedRendering = it
                        onUpdateSettings(settings.copy(enableOptimizedRendering = it))
                    })
                }

                Text("Memory: ${memoryValue}MB", color = Color(0xFFCBD5E1))
                Slider(value = memoryValue.toFloat(), onValueChange = {
                    memoryValue = it.toInt()
                    onUpdateSettings(settings.copy(memoryMb = memoryValue))
                }, valueRange = 1024f..8192f, steps = 15)

                Text("Max FPS: ${fpsValue}", color = Color(0xFFCBD5E1))
                Slider(value = fpsValue.toFloat(), onValueChange = {
                    fpsValue = it.toInt()
                    onUpdateSettings(settings.copy(maxFps = fpsValue))
                }, valueRange = 30f..360f, steps = 33)
            }
        }
    }
}

@Composable
private fun HeroPanel(settings: LauncherSettings) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF101A2A)),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Performance Engine",
                color = Color(0xFF7DD3FC),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = "${settings.performanceMode.name} mode • ${settings.maxFps} FPS cap",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Supported versions: ${LauncherCatalog.supportedVersions().joinToString(", ")}",
                color = Color(0xFFCBD5E1),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun QuickTag(label: String, active: Boolean) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = if (active) Color(0xFF34D399) else Color(0xFF475569),
                shape = RoundedCornerShape(999.dp)
            )
            .background(
                color = if (active) Color(0xFF062D1F) else Color(0xFF1F2937),
                shape = RoundedCornerShape(999.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (active) Color(0xFFA7F3D0) else Color(0xFFD1D5DB),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ProfileRow(
    profile: MinecraftProfile,
    onToggleFavorite: () -> Unit,
    onToggleProfile: () -> Unit,
    onLaunch: () -> Unit
) {
    val loaderLabel = profile.loader.name.lowercase().replaceFirstChar { it.uppercase() }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (profile.isFavorite) Icons.Default.Star else Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = if (profile.isFavorite) Color(0xFFFBBF24) else Color(0xFF7DD3FC)
                    )
                    Text(
                        text = profile.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Text(
                    text = "${profile.version} • $loaderLabel • ${profile.javaRuntime.name}",
                    color = Color(0xFFCBD5E1),
                    fontSize = 13.sp
                )
                Text(
                    text = profile.description,
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Button(onClick = onLaunch, modifier = Modifier.height(42.dp)) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                    Text("Launch")
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = onToggleFavorite, modifier = Modifier.height(38.dp)) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                    }
                    Button(onClick = onToggleProfile, modifier = Modifier.height(38.dp)) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                }

                Switch(
                    checked = profile.isEnabled,
                    onCheckedChange = { _ -> onToggleProfile() },
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
