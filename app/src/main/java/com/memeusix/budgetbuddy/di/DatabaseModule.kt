package com.memeusix.budgetbuddy.di

import android.content.Context
import androidx.room.Room
import com.memeusix.budgetbuddy.room.dao.PendingTransactionDao
import com.memeusix.budgetbuddy.room.database.BudgetBuddyDatabase
import com.memeusix.budgetbuddy.room.repository.PendingTransactionRepo
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
    ): BudgetBuddyDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = BudgetBuddyDatabase::class.java,
            name = "budget_buddy_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePendingTransactionDao(database: BudgetBuddyDatabase): PendingTransactionDao {
        return database.pendingTransactionDao()
    }


    @Singleton
    @Provides
    fun providePendingTransactionRepo(pendingTransactionDao: PendingTransactionDao): PendingTransactionRepo {
        return PendingTransactionRepo(pendingTransactionDao)
    }


}