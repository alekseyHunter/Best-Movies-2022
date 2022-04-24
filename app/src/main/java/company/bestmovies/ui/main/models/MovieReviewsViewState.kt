package company.bestmovies.ui.main.models

import company.bestmovies.data.local.entity.MovieReviewEntity

sealed class MovieReviewsViewState {
    object Loading : MovieReviewsViewState()
    data class Display(val reviews: List<MovieReviewEntity>) : MovieReviewsViewState()
    data class Error(val error: String) : MovieReviewsViewState()
}
