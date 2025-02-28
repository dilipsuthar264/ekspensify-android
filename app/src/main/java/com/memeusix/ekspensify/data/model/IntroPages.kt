package com.memeusix.ekspensify.data.model


import com.memeusix.ekspensify.R

data class IntroPages(
    val image: Int,
    val title: String,
    val description: String,
) {
    companion object {
        fun getPages(): List<IntroPages> {
            val pages = mutableListOf<IntroPages>()
            pages.add(
                IntroPages(
                    image = R.drawable.ic_onboarding_1,
                    title = "Ekspensify",
                    description = "Tracking money made easy, so you can stress less and enjoy more!"
                )
            )

            pages.add(
                IntroPages(
                    image = R.drawable.ic_onboarding_2,
                    title = "Auto Tracking",
                    description = "Automatically track and categorize your expenses in real-time."
                )
            )

            pages.add(
                IntroPages(
                    image = R.drawable.ic_onboarding_3,
                    title = "Set Budgets",
                    description = "Set budgets, control expenses, and save smarter."
                )
            )

            return pages
        }
    }
}