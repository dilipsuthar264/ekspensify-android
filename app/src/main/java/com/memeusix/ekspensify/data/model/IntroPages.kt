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
                    description = "Become your own money manager and make every Rupee count"
                )
            )

            pages.add(
                IntroPages(
                    image = R.drawable.ic_onboarding_2,
                    title = "Track Expenses",
                    description = "Track your transaction easily, with categories and financial report"
                )
            )

            pages.add(
                IntroPages(
                    image = R.drawable.ic_onboarding_3,
                    title = "Budget Planning",
                    description = "Become your own money manager and make every Rupee count"
                )
            )

            return pages
        }
    }
}