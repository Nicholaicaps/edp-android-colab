
package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as LiceoFieldKitApp

        setContent {


            val themePreferences = remember {
                ThemePreferences(applicationContext)
            }

            val darkMode by themePreferences.darkMode.collectAsState(
                initial = false
            )

            val coroutineScope = rememberCoroutineScope()


            val postViewModel: PostViewModel = viewModel(
                factory = PostViewModelFactory(app.repository)
            )

            var showProfile by remember {
                mutableStateOf(false)
            }

            val posts by postViewModel.posts.collectAsState()

            MyApplicationTheme(darkTheme = darkMode) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    if (showProfile) {

                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {

                            Button(
                                onClick = { showProfile = false }
                            ) {
                                Text("Back to Posts")
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text("Dark Mode")


                                Switch(
                                    checked = darkMode,
                                    onCheckedChange = { isChecked ->
                                        coroutineScope.launch {
                                            themePreferences.saveDarkMode(isChecked)
                                        }
                                    }
                                )

                            }

                            Text(
                                text = "My Profile",
                                style = MaterialTheme.typography.headlineMedium
                            )

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            Text(
                                text = "Name: Nicholai Patrick Capinpuyan"
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = "Total Posts: ${posts.size}"
                            )
                        }

                    } else {

                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {

                            Button(
                                onClick = { showProfile = true }
                            ) {
                                Text("Go to Profile")
                            }

                            PostScreen(
                                viewModel = postViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
