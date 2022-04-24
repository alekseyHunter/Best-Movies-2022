package company.bestmovies.data.local.dao

import androidx.room.*
import company.bestmovies.data.local.entity.MovieReviewEntity

@Dao
interface MovieReviewDao {

    @Query("Select * From ${MovieReviewEntity.TABLE_NAME}")
    suspend fun getReviews(): List<MovieReviewEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(reviews: MovieReviewEntity)

    @Query("DELETE FROM ${MovieReviewEntity.TABLE_NAME}")
    suspend fun removeAll()
}