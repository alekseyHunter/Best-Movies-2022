package company.bestmovies.data.remote.dao

import com.skydoves.sandwich.ApiResponse
import company.bestmovies.data.remote.entity.ReviewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NyTimesService {

    @GET("/svc/movies/v2/reviews/all.json?api-key=tAbGsJH3EWR50qsTGf026O8fnXmvvP2W")
    suspend fun getReviews(
        @Query("offset") offset: Int,
        @Query("order") order: String
    ): ReviewsResponse
}