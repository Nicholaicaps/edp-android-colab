
package com.example.myapplication

import android.app.Application
import androidx.room.Room
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.PostRepository

class LiceoFieldKitApp : Application() {

    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "liceo_fieldkit_database"
        ).build()
    }

    val repository by lazy {
        PostRepository(database.postDao())
    }
}
