package company.bestmovies.data.mappers

import company.bestmovies.data.local.entity.MovieReviewEntity
import company.bestmovies.data.remote.entity.MovieReviewApiEntity
import javax.inject.Inject

class MovieReviewApiEntityMapper @Inject constructor() {
    fun toMovieReviewEntity(review: MovieReviewApiEntity): MovieReviewEntity {
        return MovieReviewEntity(
            byline = review.byline,
            critics_pick = review.critics_pick,
            date_updated = review.date_updated,
            display_title = review.display_title,
            headline = review.headline,
            mpaa_rating = review.mpaa_rating,
            opening_date = review.opening_date,
            publication_date = review.publication_date,
            summary_short = review.summary_short,
            link_url = review.link.url,
            multimedia_src = review.multimedia.src,
            suggested_link_text = review.link.suggested_link_text
        )
    }
}