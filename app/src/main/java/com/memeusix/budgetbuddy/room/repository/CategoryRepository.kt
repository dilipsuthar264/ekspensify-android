package com.memeusix.budgetbuddy.room.repository

import com.memeusix.budgetbuddy.room.db.CategoryDao
import com.memeusix.budgetbuddy.room.model.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    fun getAllCategories(): List<Category> {
        return categoryDao.getAllCategories()
    }

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }
}