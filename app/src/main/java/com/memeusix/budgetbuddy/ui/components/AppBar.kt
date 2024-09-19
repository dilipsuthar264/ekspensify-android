package com.memeusix.budgetbuddy.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.ui.theme.Dark100
import com.memeusix.budgetbuddy.ui.theme.Light100
import com.memeusix.budgetbuddy.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(heading: String, navController: NavController, elevation: Boolean) {
    Surface(
        shadowElevation = if (elevation) 0.5.dp else 0.dp,
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = heading,
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = TextUnit(3f, TextUnitType.Sp)
                )
            },
            colors = TopAppBarColors(
                containerColor = Light100,
                titleContentColor = Dark100,
                scrolledContainerColor = Light100,
                navigationIconContentColor = Dark100,
                actionIconContentColor = Dark100
            ),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back"
                    )
                }
            }
        )
    }
}