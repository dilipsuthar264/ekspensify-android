package com.memeusix.budgetbuddy.di

import android.content.Context
import androidx.room.Room
import com.memeusix.budgetbuddy.room.db.CategoryDao
import com.memeusix.budgetbuddy.room.db.TransactionDao
import com.memeusix.budgetbuddy.room.db.TransactionDatabase
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
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TransactionDatabase {
        return Room.databaseBuilder(
            context,
            TransactionDatabase::class.java,
            "budget_buddy_db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideTransactionDao(database: TransactionDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: TransactionDatabase): CategoryDao {
        return database.categoryDao()
    }

}