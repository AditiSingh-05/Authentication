package com.example.authentication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.utils.ThemeManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
        viewModelScope.launch {
            ThemeManager.getTheme(application).collect { savedTheme ->
                _isDarkMode.emit(savedTheme)
            }
        }
    }

    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            ThemeManager.saveTheme(getApplication(), isDark)
            _isDarkMode.emit(isDark)
        }
    }
}
