package com.memeusix.ekspensify.ui.dashboard.profile.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.memeusix.ekspensify.R
import com.memeusix.ekspensify.ui.theme.Blue20
import com.memeusix.ekspensify.ui.theme.Green20
import com.memeusix.ekspensify.ui.theme.Red20
import com.memeusix.ekspensify.ui.theme.Violet20
import com.memeusix.ekspensify.ui.theme.Yellow20


enum class ProfileOptions(
    val title: String,
    @DrawableRes val icon: Int,
    val iconColor: Color = Violet20
) {
    ACCOUNT("Accounts", R.drawable.ic_wallet, Violet20),
    CATEGORY("Categories", R.drawable.ic_category, Yellow20),
    AUTO_TRACKING("Auto Tracking", R.drawable.ic_autotracking),
    EXPORT("Export", R.drawable.ic_exoprt, Green20),
    ABOUT("About", R.drawable.ic_about, Blue20),
    LOGOUT("Logout", R.drawable.ic_logout, Red20);
}

enum class AboutOptions(
    val title: String,
) {
    WEBSITE("Website"),
    PRIVACY_POLICY("Privacy Policy"),
    TERMS_AND_CONDITION("Terms and Condition"),
    SEND_FEEDBACK("Send Feedback"),
    CONTACT_US("Contact Us")
}


object ProfileOptionProvider {
    fun getGeneralOptions(): List<ProfileOptions> {
        return listOf(
            ProfileOptions.ACCOUNT,
            ProfileOptions.CATEGORY,
            ProfileOptions.EXPORT,
            ProfileOptions.AUTO_TRACKING,
            ProfileOptions.ABOUT
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