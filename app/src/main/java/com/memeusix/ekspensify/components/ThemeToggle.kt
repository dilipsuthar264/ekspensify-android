package com.memeusix.ekspensify.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.memeusix.ekspensify.R
import com.memeusix.ekspensify.ui.theme.Theme
import com.memeusix.ekspensify.ui.theme.ThemeViewModel
import com.memeusix.ekspensify.ui.theme.extendedColors

@Composable
fun ThemeToggle(viewModel: ThemeViewModel = hiltViewModel()) {
    val theme = viewModel.themePreferences.collectAsState()
    val icon = when (theme.value) {
        Theme.LIGHT.name -> R.drawable.ic_sun_fill
        Theme.DARK.name -> R.drawable.ic_moon_fill
        else -> R.drawable.ic_system_theme
    }
    Icon(painter = rememberAsyncImagePainter(icon),
        contentDescription = "Settings",
        tint = MaterialTheme.extendedColors.iconColor,
        modifier = Modifier
            .padding(end = 20.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.extendedColors.imageBg)
            .clickable {
                Log.e("", "ThemeToggle:")
                viewModel.toggleTheme()
            }
            .padding(7.dp)

    )
}