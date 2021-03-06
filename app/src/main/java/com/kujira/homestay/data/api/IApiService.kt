package com.kujira.homestay.data.api

interface IApiService {
    fun apiWithoutAuthenticator(): ApiCoroutines

    fun apiWithAuthenticator(): ApiCoroutines

    fun createApiCoroutines(token: String)
}