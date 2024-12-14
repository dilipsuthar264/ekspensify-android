package com.memeusix.budgetbuddy.ui.theme

import androidx.lifecycle.ViewModel
import com.memeusix.budgetbuddy.utils.SpUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

enum class Theme {
    LIGHT, DARK, SYSTEM;

    fun next(): Theme {
        return entries[(ordinal + 1) % entries.size]
    }
}

@Singleton
class ThemeManager @Inject constructor(private val spUtils: SpUtils) {
    private val _themePreference = MutableStateFlow(spUtils.themePreference)
    val themePreference: StateFlow<String> get() = _themePreference
    fun toggleTheme() {
        val currentTheme = Theme.valueOf(themePreference.value)
        val newTheme = currentTheme.next()
        _themePreference.value = newTheme.name
        spUtils.themePreference = newTheme.name
    }
}

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeManager: ThemeManager
) : ViewModel() {
    val themePreferences: StateFlow<String> = themeManager.themePreference
    fun toggleTheme() {
        themeManager.toggleTheme()
    }
}

