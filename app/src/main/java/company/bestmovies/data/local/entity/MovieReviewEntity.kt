package company.bestmovies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.concurrent.Immutable

@Entity(tableName = MovieReviewEntity.TABLE_NAME)
data class MovieReviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val byline: String,
    val critics_pick: Int,
    val date_updated: String,
    val display_title: String,
    val headline: String,
    val mpaa_rating: String,
    val opening_date: String,
    val publication_date: String,
    val summary_short: String,
    val link_url: String,
    val suggested_link_text: String,
    val multimedia_src: String
) {
    companion object {
        const val TABLE_NAME = "table_movie"
    }
}
