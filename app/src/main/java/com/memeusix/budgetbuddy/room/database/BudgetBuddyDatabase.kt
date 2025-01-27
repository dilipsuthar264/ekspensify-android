package com.memeusix.budgetbuddy.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.memeusix.budgetbuddy.room.dao.PendingTransactionDao
import com.memeusix.budgetbuddy.room.model.PendingTransactionModel


@Database(
    entities = [PendingTransactionModel::class],
    version = 1,
    exportSchema = false
)
abstract class BudgetBuddyDatabase : RoomDatabase() {

    abstract fun pendingTransactionDao(): PendingTransactionDao

    companion object {

        @Volatile
        private var INSTANCE: BudgetBuddyDatabase? = null

        fun getDataBase(context: Context): BudgetBuddyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = BudgetBuddyDatabase::class.java,
                    name = "budget_buddy_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}