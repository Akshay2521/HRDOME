package com.app.hrdrec.network


import com.app.hrdrec.network.ApiInterface
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiInterface) {

   /* suspend fun getAlbumList()  = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.getAlbum()
        emit(NetworkResult.Success(response.body()!!))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }*/
}