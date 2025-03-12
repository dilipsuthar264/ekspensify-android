package com.ekspensify.app.di

import android.content.Context
import androidx.room.Room
import com.ekspensify.app.room.dao.PendingTransactionDao
import com.ekspensify.app.room.database.EkspensifyDatabase
import com.ekspensify.app.room.repository.PendingTransactionRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ): EkspensifyDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = EkspensifyDatabase::class.java,
            name = "ekspensify_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePendingTransactionDao(database: EkspensifyDatabase): PendingTransactionDao {
        return database.pendingTransactionDao()
    }


    @Singleton
    @Provides
    fun providePendingTransactionRepo(pendingTransactionDao: PendingTransactionDao): PendingTransactionRepo {
        return PendingTransactionRepo(pendingTransactionDao)
    }


}