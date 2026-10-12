
package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Post
import com.example.myapplication.data.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val repository: PostRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    fun loadPosts() {
        viewModelScope.launch {
            _posts.value = repository.getAllPosts()
        }
    }

    fun insertPost(post: Post) {
        viewModelScope.launch {
            repository.insertPost(post)
            loadPosts()
        }
    }

    fun deleteAllPosts() {
        viewModelScope.launch {
            repository.deleteAllPosts()
            loadPosts()
        }
    }
    fun updatePost(post: Post) {
        viewModelScope.launch {
            repository.updatePost(post)
            loadPosts()
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            repository.deletePost(post)
            loadPosts()
        }
    }

}

