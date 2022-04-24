package company.bestmovies.data.repository

import androidx.annotation.WorkerThread
import company.bestmovies.data.local.dao.MovieReviewDao
import company.bestmovies.data.mappers.MovieReviewApiEntityMapper
import company.bestmovies.data.remote.ApiResponse
import company.bestmovies.data.remote.BaseRepository
import company.bestmovies.data.remote.dao.NyTimesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieReviewRepository @Inject constructor(
    private val localDataSource: MovieReviewDao,
    private val remoteDataStore: NyTimesService,
    private val movieReviewApiEntityMapper: MovieReviewApiEntityMapper
) : BaseRepository() {

    @WorkerThread
    fun loadMovieReviews(
        offset: Int, order: String,
        onError: (String) -> Unit
    ) = flow {
        val reviews = localDataSource.getReviews()
        if (reviews.isEmpty()) {
            val result = safeApiCall { remoteDataStore.getReviews(offset, order) }

            when (result) {
                is ApiResponse.Failure -> {
                    result.errorBody?.let {
                        onError(it)
                    }
                }
                is ApiResponse.Success -> {
                    result.value.results.forEach {
                        localDataSource.insertReview(
                            movieReviewApiEntityMapper.toMovieReviewEntity(
                                it
                            )
                        )
                    }

                    emit(result.value.results.map {
                        movieReviewApiEntityMapper.toMovieReviewEntity(
                            it
                        )
                    })
                }
            }
        } else {
            emit(reviews)
        }
    }.flowOn(Dispatchers.IO)
}