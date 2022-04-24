package company.bestmovies.data.remote

import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.EOFException
import java.net.SocketTimeoutException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): ApiResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                ApiResponse.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        ApiResponse.Failure(
                            isNetworkError = false,
                            errorCode = throwable.code(),
                            errorBody = throwable.message()
                        )
                    }
                    is SocketTimeoutException -> {
                        ApiResponse.Failure(
                            isNetworkError = false,
                            errorCode = null,
                            errorBody = throwable.message
                        )
                    }
                    is EOFException -> {
                        ApiResponse.Failure(
                            isNetworkError = false,
                            errorCode = null,
                            errorBody = throwable.message
                        )
                    }
                    is JsonSyntaxException -> {
                        ApiResponse.Failure(
                            isNetworkError = false,
                            errorCode = null,
                            errorBody = throwable.message
                        )
                    }
                    else -> {
                        ApiResponse.Failure(isNetworkError = true, errorCode = null, errorBody = null)
                    }
                }
            }
        }
    }
}