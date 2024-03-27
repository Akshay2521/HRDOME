package com.app.hrdrec.di

import android.content.Context
import android.util.Log
import com.app.hrdrec.network.ApiInterface
import com.app.hrdrec.BuildConfig
import com.app.hrdrec.Url.ISLOGINAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    //testing url
//   fun provideBaseURL() = "http://106.51.52.207:8095/hrdome/"
    //development url
    //fun provideBaseURL() = "http://106.51.52.207:8092/hrdome/"
    // http://106.51.52.207.8092/hrdome/
    //web url
//  fun provideBaseURL() = "http://192.168.88.104:8095/hrdome/"

    // fun provideBaseURL() = "http://106.51.52.207:8095/hrdome/"
    fun provideBaseURL() = "http://106.51.52.207:8009/hrdome/"


    @Provides
    @Singleton
    fun provideHeaderInterceptor(@ApplicationContext context: Context): Interceptor {
        return Interceptor { chain ->
            //  val token = SharedPrefUtils(context).getSavedString(AUTH_TOKEN)
            var token = ""
            if (SharedPrefUtils(context).getUserInfo() != null) token =
                SharedPrefUtils(context).getUserInfo()!!.token.toString()
            Log.e("Token", "ssd " + SharedPrefUtils(context).getSavedBoolean(ISLOGINAPI))
            val request: Request =
                if (token.isNotEmpty() && !SharedPrefUtils(context).getSavedBoolean(ISLOGINAPI)) {
                    Log.d("TOKEN--------------------->", "Barrier $token")
                    chain.request().newBuilder().header("Authorization", "Bearer $token")
                        .header("Content-Type", "application/json").build()
                } else chain.request().newBuilder().build()
            chain.proceed(request)
        }
    }

    /* @Provides
     @Singleton
     fun provideHeaderInterceptor(): Interceptor {
         return Interceptor { chain ->
             val request: Request = chain.request().newBuilder().build()
             chain.proceed(request)
         }
     }
 */
    @Provides
    @Singleton
    fun provideOkHttpClient(headerInterceptor: Interceptor) = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(headerInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS).build()
    } else OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl)
            .client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)
}


