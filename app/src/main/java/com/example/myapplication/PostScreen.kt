package com.example.myapplication


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.Post

@Composable
fun PostScreen(viewModel: PostViewModel) {
    val posts by viewModel.posts.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var newPostText by remember { mutableStateOf("") }
    var showEditDialog by remember { mutableStateOf(false) }
    var editingPost by remember { mutableStateOf<Post?>(null) }
    var editPostText by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        viewModel.loadPosts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "LiceoFieldKit Posts",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = { viewModel.loadPosts() }) {
            Text("Refresh Posts")
        }

        Button(onClick = { showAddDialog = true }) {
            Text("+")
        }

        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("New Post") },
                text = {
                    OutlinedTextField(
                        value = newPostText,
                        onValueChange = { newPostText = it },
                        label = { Text("Write your post") }
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (newPostText.isNotBlank()) {
                                viewModel.insertPost(
                                    Post(
                                        id = 0,
                                        userId = 1,
                                        title = "My Post",
                                        body = newPostText
                                    )
                                )
                                newPostText = ""
                                showAddDialog = false
                            }
                        }
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showAddDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (showEditDialog && editingPost != null) {
            AlertDialog(
                onDismissRequest = {
                    showEditDialog = false
                },
                title = { Text("Edit Post") },
                text = {
                    OutlinedTextField(
                        value = editPostText,
                        onValueChange = { editPostText = it },
                        label = { Text("Edit your post") }
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        val post = editingPost
                        if (post != null && editPostText.isNotBlank()) {
                            viewModel.updatePost(
                                post.copy(body = editPostText)
                            )
                            showEditDialog = false
                            editingPost = null
                        }
                    }) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showEditDialog = false
                        editingPost = null
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }


        if (posts.isEmpty()) {
            Text("No posts available.")
        } else {
            LazyColumn {
                items(posts) { post ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = post.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(text = post.body)

                            Row {
                                TextButton(onClick = {
                                    editingPost = post
                                    editPostText = post.body
                                    showEditDialog = true
                                }) {
                                    Text("Edit")
                                }

                                TextButton(onClick = {
                                    viewModel.deletePost(post)
                                }) {
                                    Text("Delete")
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
