package com.memeusix.budgetbuddy.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val amount: Double,
    val categoryId: Int,
    val type: String,
    val date: Long
)