package company.bestmovies.ui.main.models

sealed class MovieReviewsEvent {
    object EnterScreen : MovieReviewsEvent()
    object ReloadScreen : MovieReviewsEvent()
}
