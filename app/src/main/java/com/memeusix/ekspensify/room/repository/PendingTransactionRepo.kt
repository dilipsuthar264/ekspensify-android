package com.memeusix.ekspensify.room.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.memeusix.ekspensify.room.dao.PendingTransactionDao
import com.memeusix.ekspensify.room.model.PendingTransactionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class PendingTransactionRepo @Inject constructor(
    private val pendingTransactionDao: PendingTransactionDao
) {

    //  * create

    suspend fun createPendingTransaction(pendingTransactionModel: PendingTransactionModel): Result<PendingTransactionModel> {
        return try {
            pendingTransactionDao.createPendingTransaction(pendingTransactionModel)
            Result.success(pendingTransactionModel)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    // * delete

    suspend fun deletePendingTransaction(id: Int): Result<PendingTransactionModel> {
        return try {
            pendingTransactionDao.deletePendingTransaction(id)
            Result.success(PendingTransactionModel(id = id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    // * get pending transaction

    fun getPendingTransaction(): Flow<PagingData<PendingTransactionModel>> {
        return try {
            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = false,
                ),
                pagingSourceFactory = { pendingTransactionDao.getPendingTransactions() }
            ).flow
        } catch (e: Exception) {
            emptyFlow()
        }
    }
}