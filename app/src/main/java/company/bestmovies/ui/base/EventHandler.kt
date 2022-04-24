package company.bestmovies.ui.base

interface EventHandler<T> {
    fun obtainEvent(event: T)
}