package company.bestmovies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import company.bestmovies.data.local.dao.MovieReviewDao
import company.bestmovies.data.local.entity.MovieReviewEntity

@Database(
    entities = [MovieReviewEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieReviewDao(): MovieReviewDao
}