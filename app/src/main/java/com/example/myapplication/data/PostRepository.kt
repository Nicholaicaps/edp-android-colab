
package com.example.myapplication.data

class PostRepository(
    private val postDao: PostDao
) {
    suspend fun getAllPosts(): List<Post> {
        return postDao.getAllPosts()
    }

    suspend fun insertPost(post: Post) {
        postDao.insertPost(post)
    }


    suspend fun deleteAllPosts() {
        postDao.deleteAllPosts()
    }

    suspend fun updatePost(post: Post) {
        postDao.updatePost(post)
    }

    suspend fun deletePost(post: Post) {
        postDao.deletePost(post)
    }
}

