package company.bestmovies.di

import company.bestmovies.data.dataStore.DataStoreManager
import company.bestmovies.data.remote.dao.NyTimesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.nytimes.com"

    @Provides
    @Singleton
    fun provideOkHttpClient(dataStoreManager: DataStoreManager): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor { chain ->

                //Not necessary because authorization via @Get
                val token = runBlocking { dataStoreManager.token.first() }

                if(token.isNotEmpty()){
                    val request = chain.request().newBuilder().addHeader("api-key", token).build()
                    return@addInterceptor chain.proceed(request)
                } else {
                    val request = chain.request().newBuilder().build()
                    return@addInterceptor chain.proceed(request)
                }
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(
               BASE_URL
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNyTimesService(retrofit: Retrofit): NyTimesService {
        return retrofit.create(NyTimesService::class.java)
    }
}