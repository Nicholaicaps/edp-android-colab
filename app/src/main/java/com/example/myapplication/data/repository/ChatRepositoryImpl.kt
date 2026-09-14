package com.example.myapplication.data.repository

import com.example.myapplication.core.AppResult
import com.example.myapplication.data.local.MessageDao
import com.example.myapplication.data.network.ChatApiService
import com.example.myapplication.data.network.dto.NewMessageDto
import com.example.myapplication.data.network.dto.toDomain
import com.example.myapplication.data.network.dto.toEntity
import com.example.myapplication.domain.ChatRepository
import com.example.myapplication.domain.Message
import kotlinx.serialization.SerializationException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ChatRepositoryImpl(
    private val api: ChatApiService,
    private val dao: MessageDao
) : ChatRepository {

    override suspend fun getMessages(): AppResult<List<Message>> {
        val result = safeCall { 
            val messages = api.getMessages().toDomain()
            dao.insertAll(messages.map { it.toEntity() })
            messages
        }
        
        if (result is AppResult.Success) return result

        val saved = safeCall { dao.getAll().map { it.toDomain() } }
        return if (saved is AppResult.Success && saved.data.isNotEmpty()) saved else result
    }

    override suspend fun sendMessage(sender: String, text: String): AppResult<Unit> =
        safeCall {
            val dto = NewMessageDto(sender, text, System.currentTimeMillis())
            api.sendMessage(dto)
            Unit
        }

    private inline fun <T> safeCall(block: () -> T): AppResult<T> =
        try {
            AppResult.Success(block())
        } catch (e: UnknownHostException) {
            AppResult.Failure.NoInternet
        } catch (e: SocketTimeoutException) {
            AppResult.Failure.Timeout
        } catch (e: SerializationException) {
            android.util.Log.e("ChatRepository", "Parsing error", e)
            AppResult.Failure.Unknown("Data format error: ${e.message}")
        } catch (e: IOException) {
            AppResult.Failure.NoInternet
        } catch (e: Exception) {
            android.util.Log.e("ChatRepository", "Unknown error", e)
            AppResult.Failure.Unknown(e.message)
        }
}
