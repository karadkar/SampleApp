package io.github.karadkar.sample.db

sealed class LCE<T> {
    data class Loading<T>(var loading: Boolean) : LCE<T>()
    data class Content<T>(var data: T) : LCE<T>()
    data class Error<T>(var error: Throwable) : LCE<T>()

    companion object {
        fun <T> loading(isLoading: Boolean): LCE<T> = Loading(isLoading)
        fun <T> content(content: T): LCE<T> = Content(content)
        fun <T> error(error: Throwable): LCE<T> = Error(error)
    }

    fun isLoading() = this is Loading && this.loading
    fun isError() = this is Error
    fun isSuccess() = this is Content
}