package com.memeusix.budgetbuddy.ui.theme

import androidx.lifecycle.ViewModel
import com.memeusix.budgetbuddy.utils.spUtils.SpUtilsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

enum class Theme {
    LIGHT, DARK, SYSTEM;

    fun next(): Theme {
        return entries[(ordinal + 1) % entries.size]
    }
}

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val spUtilsManager: SpUtilsManager
) : ViewModel() {
    val themePreferences: StateFlow<String> = spUtilsManager.themePreference
    fun toggleTheme() {
        spUtilsManager.toggleTheme()
    }
}

