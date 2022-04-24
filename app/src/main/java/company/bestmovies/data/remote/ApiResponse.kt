package company.bestmovies.data.remote

sealed class ApiResponse<out T> {
    data class Success<out T>(val value: T) : ApiResponse<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: String?
    ) : ApiResponse<Nothing>()

    object Loading : ApiResponse<Nothing>()
}
