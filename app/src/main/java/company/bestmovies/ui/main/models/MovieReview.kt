package company.bestmovies.ui.main.models

data class MovieReview(
    val id: Int,
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
)
