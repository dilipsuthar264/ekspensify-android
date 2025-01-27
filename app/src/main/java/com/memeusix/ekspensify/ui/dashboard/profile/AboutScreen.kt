package com.memeusix.ekspensify.ui.dashboard.profile

import androidx.compose.foundation.layout.Column
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
import com.memeusix.ekspensify.utils.dynamicImePadding

@Composable
fun AboutScreen(
    navController: NavHostController
) {
    Scaffold(topBar = {
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
                        onClick = {}
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