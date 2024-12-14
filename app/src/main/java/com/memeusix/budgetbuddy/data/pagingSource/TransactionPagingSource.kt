package com.memeusix.budgetbuddy.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.memeusix.budgetbuddy.data.BaseRepository
import com.memeusix.budgetbuddy.data.model.requestModel.TransactionPaginationRequestModel
import com.memeusix.budgetbuddy.data.model.responseModel.TransactionResponseModel
import com.memeusix.budgetbuddy.data.services.TransactionApi
import okio.IOException

class TransactionPagingSource(
    private val transactionApi: TransactionApi,
    private val transactionPaginationRequestModel: TransactionPaginationRequestModel
) : PagingSource<Int, TransactionResponseModel>(), BaseRepository {

    override fun getRefreshKey(state: PagingState<Int, TransactionResponseModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionResponseModel> {
        val page = params.key ?: transactionPaginationRequestModel.page
        val limit = transactionPaginationRequestModel.limit

        return try {
            val response = handleResponse {
                transactionApi.getTransactions(
                    limit = limit,
                    page = page,
                    type = transactionPaginationRequestModel.type,
                    accountIds = transactionPaginationRequestModel.accountIds?.joinToString(","),
                    categoryIds = transactionPaginationRequestModel.categoryIds?.joinToString(","),
                    minAmount = transactionPaginationRequestModel.minAmount,
                    maxAmount = transactionPaginationRequestModel.maxAmount,
                    startDate = transactionPaginationRequestModel.startDate,
                    endDate = transactionPaginationRequestModel.endDate,
                    sort = transactionPaginationRequestModel.sort,
                    q = transactionPaginationRequestModel.q
                )
            }
            val isListEnd = response.data?.items?.isEmpty()
            LoadResult.Page(
                data = response.data?.items ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (isListEnd == true || isListEnd == null) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

}