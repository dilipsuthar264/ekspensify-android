package com.memeusix.budgetbuddy.ui.categories.data

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.ui.theme.Blue100
import com.memeusix.budgetbuddy.ui.theme.Dark40
import com.memeusix.budgetbuddy.ui.theme.Green100
import com.memeusix.budgetbuddy.ui.theme.Red100
import com.memeusix.budgetbuddy.ui.theme.Violet100
import com.memeusix.budgetbuddy.ui.theme.Yellow100
import com.memeusix.budgetbuddy.utils.generateIconSlug

@Stable
data class CategoryIcons(
    @DrawableRes val icon: Int,
    val iconSlug: String,
    val iconColor: Color,
) {
    companion object {
        private val categoryData = listOf(
            Triple(R.drawable.ic_electricity, "Electricity", Red100),
            Triple(R.drawable.ic_fast_food, "Food", Yellow100),
            Triple(R.drawable.ic_study, "Study", Color(0xFFFF51C8)),
            Triple(R.drawable.ic_health, "Health", Blue100),
            Triple(R.drawable.ic_investment, "Investment", Color(0xFF398542)),
            Triple(R.drawable.ic_office, "Office", Color(0xFFAA3AFF)),
            Triple(R.drawable.ic_water, "Water", Blue100),
            Triple(R.drawable.ic_accessories, "Accessories", Color(0xFFEA6E27)),
            Triple(R.drawable.ic_bike, "Bike", Color(0xFF1590CE)),
            Triple(R.drawable.ic_shopping, "Shopping", Color(0xFF626CFE)),
            Triple(R.drawable.ic_house, "House", Green100),
            Triple(R.drawable.ic_travel, "Travel", Color(0xFF42BDFF)),
            Triple(R.drawable.ic_cloths, "Cloths", Color(0xFFF4705B)),
            Triple(R.drawable.ic_fruit, "Fruit", Violet100),
            Triple(R.drawable.ic_car, "Car", Violet100),
            Triple(R.drawable.ic_other_category, "Other", Dark40),
        )

        fun getCategoryIcons(): List<CategoryIcons> =
            categoryData.map { (icon, name, color) ->
                CategoryIcons(
                    icon = icon,
                    iconSlug = name.generateIconSlug(),
                    iconColor = color
                )
            }
    }
}
