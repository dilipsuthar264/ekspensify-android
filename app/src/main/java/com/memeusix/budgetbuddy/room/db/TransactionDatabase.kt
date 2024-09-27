package com.memeusix.budgetbuddy.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.memeusix.budgetbuddy.room.model.Category
import com.memeusix.budgetbuddy.room.model.Transaction

@Database(
    entities = [Transaction::class, Category::class],
    version = 1
)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    abstract fun categoryDao(): CategoryDao
}