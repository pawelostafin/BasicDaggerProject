package me.ostafin.basicdaggerproject.util

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

data class AsyncApiRequest<D>(val status: AsyncApiRequestStatus, val data: D? = null) {
    val isLoading: Boolean get() = (status == Loading)
    val isError: Boolean get() = (status is ApiError)
    val isSuccess: Boolean get() = (status == Success)
}

sealed class AsyncApiRequestStatus
object Loading : AsyncApiRequestStatus()
object Success : AsyncApiRequestStatus()
data class ApiError(val throwable: Throwable) : AsyncApiRequestStatus()

fun <D> Single<D>.asApiAsyncRequest(): Observable<AsyncApiRequest<D>> =
    map { AsyncApiRequest(Success, it) }.addDefaultAsyncRequestFlowStates().ioToMainScheduler()

fun <D> Single<AsyncApiRequest<D>>.addDefaultAsyncRequestFlowStates(): Observable<AsyncApiRequest<D>> =
    toObservable()
        .startWithItem(AsyncApiRequest(Loading))
        .onErrorReturn { AsyncApiRequest(ApiError(it)) }

fun <D> Single<D>.ioToMainScheduler() = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
fun <D> Observable<D>.ioToMainScheduler() = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


fun <D> Observable<AsyncApiRequest<D>>.subscribeApiAsyncRequest(
    onSuccess: (D) -> Unit = {},
    onError: (Throwable) -> Unit = {},
    onProgressChanged: (Boolean) -> Unit = {}
): Disposable {
    return this.subscribe {

        onProgressChanged(it.isLoading)
        when (it.status) {
            is Success -> {
                if (it.data != null) {
                    onSuccess(it.data)
                } else {
                    throw NullPointerException("Data should not be null")
                }
            }
            is ApiError -> onError(it.status.throwable)
        }
    }
}

/**
 * Flatmaps upstream items into [source] items.
 * Ignores upstream items if there is any [source] instance currently running.
 *
 * ```
 * upstream ----u-----u---u-------u---------------|-->
 *              ↓                 ↓               ↓
 * source       ---s-------|->    ---s-------|->  ↓
 *                 ↓                 ↓            ↓
 * result   -------s-----------------s------------|-->
 * ```
 */
fun <T, R> Observable<T>.flatMapFirst(transform: (T) -> Observable<R>) =
    toFlowable(BackpressureStrategy.DROP)
        .flatMap({ transform(it).toFlowable(BackpressureStrategy.BUFFER) }, 1)
        .toObservable()