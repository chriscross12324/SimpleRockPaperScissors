package com.simplegames.chris.rockpaperscissors

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.simplegames.chris.rockpaperscissors.data.AppSettings
import com.simplegames.chris.rockpaperscissors.data.SettingsRepository
import com.simplegames.chris.rockpaperscissors.data.settingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Overlay { None, Settings, About }

class AppViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = SettingsRepository(app.settingsDataStore)

    val settings: StateFlow<AppSettings?> = repository.settings.stateIn(
        viewModelScope,
        SharingStarted.Eagerly, null
    )

    private val _overlay = MutableStateFlow(Overlay.None)
    val overlay: StateFlow<Overlay> = _overlay.asStateFlow()

    fun openSettings() {
        _overlay.value = Overlay.Settings
        Log.d("AppViewModel", "openSettings -> ${_overlay.value}")
    }

    fun openAbout() {
        _overlay.value = Overlay.About
    }

    fun closeOverlay() {
        Log.d("AppViewModel", "closeOverlay")
        _overlay.value = Overlay.None
    }

    fun back() {
        _overlay.value = when(_overlay.value) {
            Overlay.About -> Overlay.Settings
            Overlay.Settings -> Overlay.None
            Overlay.None -> Overlay.None
        }
    }

    fun toggleVibration() {
        viewModelScope.launch { repository.toggleVibration() }
    }

    fun toggleDarkMode() {
        viewModelScope.launch { repository.toggleDarkMode() }
    }

    fun setGradient(index: Int) {
        viewModelScope.launch { repository.setGradientIndex(index) }
    }
}