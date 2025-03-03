package com.memeusix.ekspensify.ui.dashboard.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.memeusix.ekspensify.BuildConfig
import com.memeusix.ekspensify.R
import com.memeusix.ekspensify.components.AppBar
import com.memeusix.ekspensify.components.CustomListItem
import com.memeusix.ekspensify.ui.dashboard.budget.components.CreateBudgetSectionCard
import com.memeusix.ekspensify.ui.dashboard.profile.data.AboutOptions
import com.memeusix.ekspensify.ui.theme.extendedColors
import com.memeusix.ekspensify.utils.CommonData
import com.memeusix.ekspensify.utils.dynamicImePadding
import com.memeusix.ekspensify.utils.openWebLink
import com.memeusix.ekspensify.utils.sendEmail

@Composable
fun AboutScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                heading = "About", navController
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .dynamicImePadding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            CreateBudgetSectionCard("") {
                AboutOptions.entries.forEach {
                    CustomListItem(title = it.title,
                        modifier = Modifier.padding(vertical = 9.dp),
                        trailingContent = {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_right),
                                tint = MaterialTheme.extendedColors.iconColor,
                                contentDescription = null,
                            )
                        },
                        onClick = { handleAboutOptionClick(it, navController) }
                    )
                }
            }

            Text(
                "Version : ${BuildConfig.VERSION_NAME}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            )
        }
    }
}

private fun handleAboutOptionClick(clickedOption: AboutOptions, navController: NavHostController) {
    when (clickedOption) {
        AboutOptions.PRIVACY_POLICY -> {
            navController.context.openWebLink(
                CommonData.PRIVACY_POLICY
            )
        }

        AboutOptions.TERMS_AND_CONDITION -> {
            navController.context.openWebLink(
                CommonData.TERMS_AND_CONDITION
            )
        }

        AboutOptions.WEBSITE -> {
            navController.context.openWebLink(
                CommonData.WEBSITE
            )
        }

        AboutOptions.SEND_FEEDBACK -> {
            navController.context.sendEmail(
                to = CommonData.TEAM_MAIL,
                subject = navController.context.getString(
                    R.string.ekspensify_feedback,
                    BuildConfig.VERSION_NAME
                )
            )
        }

        AboutOptions.CONTACT_US -> {
            navController.context.sendEmail(
                to = CommonData.SUPPORT_MAIL,
                subject = navController.context.getString(R.string.support_request_ekspensify)
            )
        }
    }
}
