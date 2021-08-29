package com.kujira.homestay.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kujira.homestay.data.DataManager
import com.kujira.homestay.data.api.ApiCoroutines
import com.kujira.homestay.data.api.IApiService
import com.kujira.homestay.data.model.response.BaseResponse
import com.kujira.homestay.data.model.response.ErrorResponse
import com.kujira.homestay.data.scheduler.ISchedulerProvider
import com.kujira.homestay.utils.printLog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

/**
 * Created by OpenYourEyes on 11/26/2019
 */
open abstract class BaseViewModel : ViewModel() {
    lateinit var navigation: Navigators

    lateinit var dataManager: DataManager
    lateinit var apiService: IApiService
    lateinit var scheduler: ISchedulerProvider

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    val showLoading = PublishSubject.create<Boolean>()
    val apiWithAuthenticator: ApiCoroutines by lazy {
        apiService.apiWithAuthenticator()
    }
    val trackingError: PublishSubject<ErrorResponse> by lazy {
        PublishSubject.create()
    }

    fun initData(dataManager: DataManager, apiService: IApiService, scheduler: ISchedulerProvider) {
        this.dataManager = dataManager
        this.apiService = apiService
        this.scheduler = scheduler
    }

    /**
     * this is sample test
     */
//    fun <T> executeRequestTest(
//        request: suspend (ApiCoroutines.() -> T),
//        response: (T) -> Unit
//    ): Job {
//        return viewModelScope.launch {
//            flow {
//                printLog("make request")
//                val result = request(apiService.apiWithoutAuthenticator())
//                emit(result)
//            }.trackingProgress(progressBar = showLoading)
//                .catch { error ->
//                    //TODO handle error here
//                    val messageError = error.message ?: "error"
//                    trackingError.onNext(ErrorResponse.defaultError(messageError))
//                }.flowOn(Dispatchers.IO)
//                .collect {
//                    response(it)
//                }
//        }
//    }

    /**
     * Execute api inside scope Coroutine
     * data is not allowed to be null, if null should be used
     * @see executeRequestFlow
     */
//    fun <T> executeRequest(
//        request: suspend (ApiCoroutines.() -> BaseResponse<T>),
//        response: (T) -> Unit
//    ): Job {
//        return viewModelScope.launch {
//            flow {
//                val result = request(apiWithAuthenticator)
//                if (!result.isSuccess) {
//                    throw result.error ?: ErrorResponse("Unknow")
//                }
//                val data = result.data
//                    ?: throw NullPointerException("Data must be not null, please using executeRequestFlow")
//                showLoading.onNext(true)
//                emit(data)
//            }.trackingProgress(progressBar = showLoading)
//                .catch { error ->
//                    handleErrorResponse(error)
//                }.flowOn(Dispatchers.IO)
//                .collect {
//                    response(it)
//                }
//        }
//    }

    /**
     * Using when data can be null or transform data before update ui
     */
//    suspend fun <T> executeRequestFlow(
//        request: suspend (ApiCoroutines.() -> BaseResponse<T>),
//    ): Flow<T?> {
//        return flow {
//            val result = request(apiWithAuthenticator)
//            if (!result.isSuccess) {
//                throw result.error ?: ErrorResponse("UnKnow")
//            }
//            val data = result.data
//            emit(data)
//        }.trackingProgress(progressBar = showLoading)
//            .catch { error ->
//                handleErrorResponse(error)
//            }.flowOn(Dispatchers.IO)
//
//    }

    private fun handleErrorResponse(error: Throwable) {
        //TODO handle error here
        val messageError = error.message ?: "error"
        trackingError.onNext(ErrorResponse.defaultError(messageError))
    }

    fun Disposable.addDisposable() {
        disposable.add(this)
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun onDestroyView() {
        disposable.clear()
    }
}