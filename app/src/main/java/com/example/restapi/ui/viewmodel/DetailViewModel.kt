package com.example.restapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.restapi.model.Mahasiswa
import com.example.restapi.repository.MahasiswaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    object Error : DetailUiState()
}

class DetailViewModel(private val repository: MahasiswaRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun getMahasiswaByNim(nim: String) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val mahasiswa = repository.getMahasiswabyNim(nim)
                if (mahasiswa != null) {
                    _uiState.value = DetailUiState.Success(mahasiswa)
                } else {
                    _uiState.value = DetailUiState.Error
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error
            }
        }
    }

    companion object {
        fun provideFactory(
            repository: MahasiswaRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return DetailViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
