package com.memeusix.budgetbuddy.room.repository

import com.memeusix.budgetbuddy.room.db.TransactionDao
import com.memeusix.budgetbuddy.room.model.Transaction
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {
    suspend fun createTransaction(transaction: Transaction) {
        transactionDao.createTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }

    suspend fun getTransactionsByCategory(categoryId: Long): List<Transaction> {
        return transactionDao.getAllTransactions()
    }

    suspend fun getAllTransactions(): List<Transaction> {
        return transactionDao.getAllTransactions()
    }
}