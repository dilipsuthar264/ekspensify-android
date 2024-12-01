package com.memeusix.budgetbuddy.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.ui.theme.Typography
import com.memeusix.budgetbuddy.utils.singleClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    heading: String,
    navController: NavController,
    elevation: Boolean = false,
    isBackNavigation: Boolean = true,
    isLightColor: Boolean = false,
    bgColor: Color = MaterialTheme.colorScheme.background
) {
    val titleTextStyle = Typography.titleSmall.copy(
        fontWeight = FontWeight.SemiBold,
        color = if (isLightColor) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
    )

    val navigationIconTint =
        if (isLightColor) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground

    Surface(
        shadowElevation = if (elevation) 0.5.dp else 0.dp,
    ) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = bgColor
            ),
            title = { Text(heading, style = titleTextStyle) },
            navigationIcon = {
                if (isBackNavigation) {
                    IconButton(
                        onClick = singleClick(onClick = navController::popBackStack),
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                            contentDescription = "Back",
                            tint = navigationIconTint
                        )
                    }
                }
            },
            expandedHeight = 60.dp,
        )
    }
}