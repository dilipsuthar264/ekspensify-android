package com.memeusix.budgetbuddy.di

import android.app.Application
import com.memeusix.budgetbuddy.BuildConfig
import com.memeusix.budgetbuddy.data.services.AccountApi
import com.memeusix.budgetbuddy.data.services.AuthApi
import com.memeusix.budgetbuddy.data.services.CategoryApi
import com.memeusix.budgetbuddy.data.services.ProfileApi
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
        fun getRetrofit(spUtils: SpUtils): Retrofit {
            val httpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(getAuthenticationInterceptor(spUtils.accessToken))
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

        private fun getAuthenticationInterceptor(token: String): Interceptor {
            return Interceptor { chain ->
                var request = chain.request()
                request = if (token.isNotEmpty()) {
                    request.newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                } else {
                    request
                }
                chain.proceed(request)
            }
        }

    }


    @Provides
    @Singleton
    fun provideSpUtils(application: Application): SpUtils {
        return SpUtils(application.applicationContext)
    }


    @Provides
    @Singleton
    fun provideAuthApi(spUtils: SpUtils): AuthApi {
        return getRetrofit(spUtils).create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountApi(spUtils: SpUtils): AccountApi {
        return getRetrofit(spUtils).create(AccountApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileApi(spUtils: SpUtils): ProfileApi {
        return getRetrofit(spUtils).create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryApi(spUtils: SpUtils): CategoryApi {
        return getRetrofit(spUtils).create(CategoryApi::class.java)
    }

}