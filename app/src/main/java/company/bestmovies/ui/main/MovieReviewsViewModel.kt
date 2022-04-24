package company.bestmovies.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import company.bestmovies.data.repository.MovieReviewRepository
import company.bestmovies.ui.base.EventHandler
import company.bestmovies.ui.main.models.MovieReviewsEvent
import company.bestmovies.ui.main.models.MovieReviewsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieReviewsViewModel @Inject constructor(
    private val movieReviewRepository: MovieReviewRepository
) : ViewModel(), EventHandler<MovieReviewsEvent> {

    private val _viewState: MutableStateFlow<MovieReviewsViewState> =
        MutableStateFlow(MovieReviewsViewState.Loading)
    val viewState: StateFlow<MovieReviewsViewState>
        get() = _viewState

    override fun obtainEvent(event: MovieReviewsEvent) {
        when (val state = _viewState.value) {
            is MovieReviewsViewState.Loading -> reduce(event, state)
            is MovieReviewsViewState.Display -> reduce(event, state)
            is MovieReviewsViewState.Error -> reduce(event, state)
        }
    }

    private fun reduce(event: MovieReviewsEvent, state: MovieReviewsViewState.Loading) {
        when (event) {
            is MovieReviewsEvent.EnterScreen -> {
                fetchData()
            }
        }
    }

    private fun reduce(event: MovieReviewsEvent, state: MovieReviewsViewState.Display) {
        when (event) {
            is MovieReviewsEvent.EnterScreen -> {
                fetchData()
            }
        }
    }

    private fun reduce(event: MovieReviewsEvent, state: MovieReviewsViewState.Error) {
        when (event) {
            is MovieReviewsEvent.ReloadScreen -> {
                fetchData(isRefresh = true)
            }
        }
    }

    private fun fetchData(isRefresh: Boolean = false) {
        if (isRefresh) {
            _viewState.update {
                MovieReviewsViewState.Loading
            }
        }

        viewModelScope.launch {
            val result = movieReviewRepository.loadMovieReviews(
                offset = 0,
                order = "by-opening-date",
                onError = { _viewState.value = MovieReviewsViewState.Error(it) })

            result.distinctUntilChanged().collect {
                _viewState.value = MovieReviewsViewState.Display(it)
            }
        }
    }
}