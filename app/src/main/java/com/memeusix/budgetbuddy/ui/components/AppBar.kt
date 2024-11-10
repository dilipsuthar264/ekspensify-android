package com.memeusix.budgetbuddy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    elevation: Boolean,
    isBackNavigation: Boolean = true
) {
    Surface(
        shadowElevation = if (elevation) 0.5.dp else 0.dp,
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = heading,
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
            },
            navigationIcon = {
                if (isBackNavigation) {
                    IconButton(
                        onClick = singleClick { navController.popBackStack() },
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                            contentDescription = "Back",
                        )
                    }
                }
            },
            expandedHeight = 50.dp,
        )
    }
}