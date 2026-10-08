package com.ultrafps.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ultrafps.launcher.ui.UltraFpsLauncherTheme
import com.ultrafps.launcher.ui.LauncherApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            UltraFpsLauncherTheme {
                LauncherApp()
            }
        }
    }
}
