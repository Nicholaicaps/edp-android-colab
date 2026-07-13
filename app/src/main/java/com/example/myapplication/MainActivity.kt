package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode = isSystemInDarkTheme()
            val colorScheme = if (isDarkMode) {
                darkColorScheme(
                    primary = Color(0xFFD0BCFF),
                    onPrimary = Color(0xFF381E72),
                    primaryContainer = Color(0xFF4F378B),
                    onPrimaryContainer = Color(0xFFEADDFF),
                    background = Color(0xFF1C1B1F),
                    onBackground = Color(0xFFE6E1E5),
                    surface = Color(0xFF1C1B1F),
                    onSurface = Color(0xFFE6E1E5),
                )
            } else {
                lightColorScheme(
                    primary = Color(0xFF6750A4),
                    onPrimary = Color.White,
                    primaryContainer = Color(0xFFEADDFF),
                    onPrimaryContainer = Color(0xFF21005D),
                    background = Color(0xFFFFFBFE),
                    onBackground = Color(0xFF1C1B1F),
                    surface = Color(0xFFFFFBFE),
                    onSurface = Color(0xFF1C1B1F),
                )
            }

            MaterialTheme(colorScheme = colorScheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        // Original Card (matching Large Card height)
                        BusinessCard(
                            useLargeFont = false,
                            modifier = Modifier.height(530.dp)
                        )
                        
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        
                        // Large Card (Natural height)
                        BusinessCard(useLargeFont = true)
                    }
                }
            }
        }
    }
}

@Composable
fun BusinessCard(useLargeFont: Boolean, modifier: Modifier = Modifier) {
    val nameSize = if (useLargeFont) 32.sp else 24.sp
    val titleSize = if (useLargeFont) 24.sp else 18.sp
    val contactSize = if (useLargeFont) 18.sp else 16.sp
    val photoSize = if (useLargeFont) 180.dp else 140.dp
    val spacing = if (useLargeFont) 32.dp else 20.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_photo),
            contentDescription = "Profile Photo",
            modifier = Modifier
                .size(photoSize)
                .clip(CircleShape)
                .border(6.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(spacing))

        Text(
            text = "Nicholai Patrick Capinpuyan",
            fontSize = nameSize,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground,
            lineHeight = (nameSize.value * 1.2).sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp),
        )

        Text(
            text = "Android Developer",
            fontSize = titleSize,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
        )

        Spacer(modifier = Modifier.height(spacing * 1.5f))

        ContactRow(
            icon = Icons.Default.Phone,
            text = "09456845773",
            fontSize = contactSize,
        )

        Spacer(modifier = Modifier.height(16.dp))

        ContactRow(
            icon = Icons.Default.Email,
            text = "ncapinpuyan46671@liceo.edu.ph",
            fontSize = contactSize,
        )

        Spacer(modifier = Modifier.height(16.dp))

        ContactRow(
            icon = Icons.Default.Person,
            text = "Nicholaicapsgithub",
            fontSize = contactSize,
        )
    }
}

@Composable
fun ContactRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    fontSize: androidx.compose.ui.unit.TextUnit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(if (fontSize > 16.sp) 32.dp else 24.dp),
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = text,
            fontSize = fontSize,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Preview(showBackground = true, name = "Original Card")
@Composable
fun BusinessCardOriginalPreview() {
    MaterialTheme {
        Surface {
            BusinessCard(
                useLargeFont = false,
                modifier = Modifier.height(530.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "Large Card")
@Composable
fun BusinessCardLargePreview() {
    MaterialTheme {
        Surface {
            BusinessCard(useLargeFont = true)
        }
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BusinessCardDarkPreview() {
    val colorScheme = darkColorScheme(
        primary = Color(0xFFD0BCFF),
        onPrimary = Color(0xFF381E72),
        background = Color(0xFF1C1B1F),
        onBackground = Color(0xFFE6E1E5),
        surface = Color(0xFF1C1B1F),
        onSurface = Color(0xFFE6E1E5),
    )
    MaterialTheme(colorScheme = colorScheme) {
        Surface {
            BusinessCard(
                useLargeFont = false,
                modifier = Modifier.height(530.dp)
            )
        }
    }
}
