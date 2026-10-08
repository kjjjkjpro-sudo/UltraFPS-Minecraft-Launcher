package com.ultrafps.launcher.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ultrafps.launcher.data.ProfileRepository
import com.ultrafps.launcher.model.LauncherCatalog
import com.ultrafps.launcher.model.MinecraftProfile
import com.ultrafps.launcher.model.ModLoader

@Composable
fun LauncherApp() {
    val profiles = remember {
        mutableStateListOf(*ProfileRepository().getDefaultProfiles().toTypedArray())
    }

    LauncherScreen(profiles = profiles)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LauncherScreen(profiles: List<MinecraftProfile>) {
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
            HeaderCard()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickTag("FPS Boost", true)
                QuickTag("Auto Java", true)
                QuickTag("Low Latency", true)
            }

            Text(
                text = "Profiles",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(profiles) { profile ->
                    ProfileRow(profile = profile)
                }
            }
        }
    }
}

@Composable
private fun HeaderCard() {
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
                text = "Optimized for Fabric, Forge, Quilt, NeoForge, and vanilla builds",
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
private fun ProfileRow(profile: MinecraftProfile) {
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
                Button(
                    onClick = { },
                    modifier = Modifier.height(42.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null
                    )
                    Text("Launch")
                }
                Switch(
                    checked = profile.isEnabled,
                    onCheckedChange = null
                )
            }
        }
    }
}

@Composable
fun UltraFpsLauncherTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = androidx.compose.material3.darkColorScheme(
            primary = Color(0xFF60A5FA),
            secondary = Color(0xFF34D399),
            background = Color(0xFF0B1020),
            surface = Color(0xFF111827),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White
        ),
        typography = MaterialTheme.typography,
        content = content
    )
}
