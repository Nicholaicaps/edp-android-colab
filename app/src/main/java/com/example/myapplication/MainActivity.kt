package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val cleanPalette = lightColorScheme(
            primary = Color(0xFF3F51B5),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFE8EAF6),
            onPrimaryContainer = Color(0xFF1A237E),
            secondary = Color(0xFF546E7A),
            surface = Color(0xFFFAFAFA),
            background = Color.White
        )

        setContent {
            MaterialTheme(colorScheme = cleanPalette) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeApp()
                }
            }
        }
    }
}
