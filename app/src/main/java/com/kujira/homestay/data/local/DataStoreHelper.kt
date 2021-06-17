package com.kujira.homestay.data.local

interface DataStoreHelper {
    suspend fun saveUserName(name: String)

    suspend fun getUserName(): String
}