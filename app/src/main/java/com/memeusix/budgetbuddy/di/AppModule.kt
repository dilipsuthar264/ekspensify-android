package com.memeusix.budgetbuddy.di

import android.app.Application
import com.memeusix.budgetbuddy.BuildConfig
import com.memeusix.budgetbuddy.data.services.AuthApi
import com.memeusix.budgetbuddy.utils.SpUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        fun getRetrofit(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(20, TimeUnit.SECONDS)
            httpClient.connectTimeout(20, TimeUnit.SECONDS)
            httpClient.addInterceptor(getLoggingInterceptor())
            httpClient.addInterceptor(getAuthenticationInterceptor(""))
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }

        private fun getLoggingInterceptor(): HttpLoggingInterceptor {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

        private fun getAuthenticationInterceptor(token : String): Interceptor {
            return Interceptor { chain ->
                var request = chain.request()
                request =  if (token.isNotEmpty()){
                    request.newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                }else{
                    request
                }
                chain.proceed(request)
            }
        }
    }

    @Provides
    @Singleton
    fun providerSpUtils(application : Application) : SpUtils {
        return SpUtils(application.applicationContext)
    }

    @Provides
    @Singleton
    fun providerAuthApi() : AuthApi {
        return getRetrofit().create(AuthApi::class.java)
    }
}