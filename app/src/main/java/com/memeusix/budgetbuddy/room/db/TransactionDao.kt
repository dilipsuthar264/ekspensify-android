package com.memeusix.budgetbuddy.room.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.memeusix.budgetbuddy.room.model.Transaction

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE categoryId = :categoryId")
    suspend fun getTransactionsByCategory(categoryId: Long): List<Transaction>

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<Transaction>
}