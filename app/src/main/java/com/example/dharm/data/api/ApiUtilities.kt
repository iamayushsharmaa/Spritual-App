package com.example.dharm.data.api


import com.example.dharm.repository.ChapterRepository
import com.example.dharm.repository.ChapterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiUtilities {

    val header = mapOf(
        "Accept" to " application/json",
        "x-rapidapi-key" to "0d0b640a07msh8ab9df507a6a5fcp1a286djsn00752772672e",
        "x-rapidapi-host" to "bhagavad-gita3.p.rapidapi.com"
    )

    val client = OkHttpClient.Builder().apply {
       addInterceptor { chain->
           val newReqeust = chain.request().newBuilder().apply {
               header.forEach { key , value ->  addHeader(key, value)}
           }.build()
           chain.proceed(newReqeust)
       }
    }.build()


    @Provides
    @Singleton
    fun providesApiService(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://bhagavad-gita3.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideChapterRepository(apiService: ApiInterface): ChapterRepository {
        return ChapterRepositoryImpl(apiService)
    }


}
