package com.memeusix.budgetbuddy.ui.dashboard.profile.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.ui.theme.Blue20
import com.memeusix.budgetbuddy.ui.theme.Green20
import com.memeusix.budgetbuddy.ui.theme.Red20
import com.memeusix.budgetbuddy.ui.theme.Violet20
import com.memeusix.budgetbuddy.ui.theme.Yellow20


enum class ProfileOptions(
    val title: String,
    @DrawableRes val icon: Int,
    val iconColor: Color = Violet20
) {
    ACCOUNT("Accounts", R.drawable.ic_wallet, Violet20),
    CATEGORY("Categories", R.drawable.ic_category, Yellow20),
    EXPORT("Export", R.drawable.ic_exoprt, Green20),
    SETTING("Settings", R.drawable.ic_setting, Blue20),
    LOGOUT("Logout", R.drawable.ic_logout, Red20);
}

object ProfileOptionProvider {
    fun getGeneralOptions(): List<ProfileOptions> {
        return listOf(
            ProfileOptions.ACCOUNT,
            ProfileOptions.CATEGORY,
            ProfileOptions.EXPORT,
            ProfileOptions.SETTING
        )
    }

    fun getDangerOptions(): List<ProfileOptions> {
        return listOf(ProfileOptions.LOGOUT)
    }
}

enum class AvatarOptions(
    val avatarSlug: String,
    @DrawableRes val icon: Int,
) {
    DEFAULT("ic_avatar_default", R.drawable.ic_user_new_fill),
    MALE("ic_avatar_male", R.drawable.ic_avatar_male),
    FEMALE("ic_avatar_female", R.drawable.ic_avatar_female),
}