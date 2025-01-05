package com.memeusix.budgetbuddy.di

import android.app.Application
import com.memeusix.budgetbuddy.BuildConfig
import com.memeusix.budgetbuddy.data.services.AccountApi
import com.memeusix.budgetbuddy.data.services.AuthApi
import com.memeusix.budgetbuddy.data.services.BudgetApi
import com.memeusix.budgetbuddy.data.services.CategoryApi
import com.memeusix.budgetbuddy.data.services.ProfileApi
import com.memeusix.budgetbuddy.data.services.TransactionApi
import com.memeusix.budgetbuddy.utils.fileUtils.FileRepository
import com.memeusix.budgetbuddy.utils.fileUtils.FileRepositoryImpl
import com.memeusix.budgetbuddy.utils.spUtils.SpUtils
import com.memeusix.budgetbuddy.utils.spUtils.SpUtilsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        fun getRetrofit(app: Application): Retrofit {
            val spUtils = SpUtils(app.applicationContext)
            val httpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(getAuthenticationInterceptor(spUtils))
                .build()
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        }

        private fun getLoggingInterceptor(): HttpLoggingInterceptor {
            val httpLoggingInterceptor = HttpLoggingInterceptor() { message ->
                HttpLoggingInterceptor.Logger.DEFAULT.log(filterLogs(message))
            }
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

        private fun getAuthenticationInterceptor(spUtils: SpUtils): Interceptor {
            return Interceptor { chain ->
                val token = spUtils.accessToken
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

        fun getRetryInterceptor(): Interceptor {
            return Interceptor { chain ->
                var response: Response
                var tryCount = 0
                val maxRetry = 3

                do {
                    response = chain.proceed(chain.request())
                    tryCount++
                } while (!response.isSuccessful && tryCount < maxRetry)

                response
            }
        }


        private fun filterLogs(message: String): String {
            return when {
                message.contains("Content-Type:") && (message.contains("image/") || message.contains(
                    "application/octet-stream"
                )) -> {
                    "Body: [Binary data omitted]"
                }

                else -> message
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
    fun provideFileRepository(app: Application): FileRepository {
        return FileRepositoryImpl(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideSpUtilsManager(spUtils: SpUtils): SpUtilsManager {
        return SpUtilsManager(spUtils)
    }

    @Provides
    @Singleton
    fun provideAuthApi(app: Application): AuthApi {
        return getRetrofit(app).create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountApi(app: Application): AccountApi {
        return getRetrofit(app).create(AccountApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileApi(app: Application): ProfileApi {
        return getRetrofit(app).create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryApi(app: Application): CategoryApi {
        return getRetrofit(app).create(CategoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTransactionApi(app: Application): TransactionApi {
        return getRetrofit(app).create(TransactionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBudgetApi(app: Application): BudgetApi {
        return getRetrofit(app).create(BudgetApi::class.java)
    }
}
