package com.memeusix.budgetbuddy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.BaseRepository
import com.memeusix.budgetbuddy.data.model.BudgetMeta
import com.memeusix.budgetbuddy.data.model.requestModel.BudgetQueryModel
import com.memeusix.budgetbuddy.data.model.requestModel.BudgetRequestModel
import com.memeusix.budgetbuddy.data.model.responseModel.BudgetReportResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.BudgetResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.TransactionResponseModel
import com.memeusix.budgetbuddy.data.pagingSource.BudgetPagingSource
import com.memeusix.budgetbuddy.data.pagingSource.BudgetReportPagingSource
import com.memeusix.budgetbuddy.data.pagingSource.BudgetTransactionPagingSource
import com.memeusix.budgetbuddy.data.services.BudgetApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class BudgetRepository @Inject constructor(
    private val budgetApi: BudgetApi
) : BaseRepository {

    suspend fun createBudget(budgetRequestModel: BudgetRequestModel): ApiResponse<BudgetResponseModel> {
        return handleResponse { budgetApi.createBudget(budgetRequestModel) }
    }

    suspend fun deleteBudget(budgetId: Int): ApiResponse<BudgetResponseModel> {
        return handleResponse { budgetApi.deleteBudget(budgetId) }
    }

    suspend fun updateBudget(budgetId: Int, status: String): ApiResponse<BudgetResponseModel> {
        return handleResponse { budgetApi.updateBudget(budgetId, status) }
    }

    suspend fun getBudgetById(budgetId: Int): ApiResponse<BudgetResponseModel> {
        return handleResponse { budgetApi.getBudgetById(budgetId) }
    }

    fun getBudgets(
        budgetQueryModel: BudgetQueryModel,
        getMetaData: (BudgetMeta?) -> Unit
    ): Flow<PagingData<BudgetResponseModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = budgetQueryModel.limit,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                BudgetPagingSource(
                    budgetApi,
                    budgetQueryModel,
                    getMetaData
                )
            }
        ).flow
            .catch { e ->
                emit(PagingData.empty())
            }
    }

    fun getBudgetReport(
        budgetId: Int,
        budgetQueryModel: BudgetQueryModel
    ): Flow<PagingData<BudgetReportResponseModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = budgetQueryModel.limit,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                BudgetReportPagingSource(
                    budgetId,
                    budgetApi,
                    budgetQueryModel
                )
            }
        ).flow
            .catch { e ->
                emit(PagingData.empty())
            }
    }

    fun getBudgetTransaction(
        budgetId: Int,
        reportId: Int,
        budgetQueryModel: BudgetQueryModel
    ): Flow<PagingData<TransactionResponseModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = budgetQueryModel.limit,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                BudgetTransactionPagingSource(
                    budgetId = budgetId,
                    reportId = reportId,
                    budgetQueryModel = budgetQueryModel,
                    budgetApi = budgetApi
                )
            }
        ).flow
            .catch { e ->
                emit(PagingData.empty())
            }
    }

}