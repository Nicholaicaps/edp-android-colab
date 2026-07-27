package com.liceo.prelim.profilecard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun ProfileScreen() {
    // Task 1 — Center the layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Task 2 — Circular avatar
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "RJ",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Task 3 — Full name & subtitle
        Text(
            text = "Nicholai Patrick Capinpuyan",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "BSIT 3-A",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Task 4 — The Info Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Task 5 — Reusable InfoRow (×5)
                // Order: Full Name, Course, Section, Mobile Number, Email Address
                InfoRow(
                    icon = Icons.Default.Person,
                    label = "Full Name",
                    value = "Nicholai Patrick Capinpuyan"
                )
                InfoRow(
                    icon = Icons.Default.School,
                    label = "Course",
                    value = "Bachelor of Science in Information Technology"
                )
                InfoRow(
                    icon = Icons.Default.Class,
                    label = "Section",
                    value = "3-A"
                )
                InfoRow(
                    icon = Icons.Default.Phone,
                    label = "Mobile Number",
                    value = "+63 912 345 6789"
                )
                InfoRow(
                    icon = Icons.Default.Email,
                    label = "Email Address",
                    value = "ncapinpuyan@example.com"
                )
            }
        }
    }
}

// Task 5 — Reusable InfoRow
@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon, 
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = label, 
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value, 
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// Task 6 — Verify light AND dark
@Preview(showBackground = true, name = "Profile — Light")
@Composable
fun ProfilePreview() {
    MyApplicationTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            ProfileScreen()
        }
    }
}

@Preview(showBackground = true, name = "Profile — Dark", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfileDarkPreview() {
    MyApplicationTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            ProfileScreen()
        }
    }
}
