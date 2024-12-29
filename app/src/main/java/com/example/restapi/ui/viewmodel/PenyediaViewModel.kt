package com.example.restapi.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.restapi.MahasiswaApplication

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiKontak().container.kontakRepository) }
        initializer { InsertViewModel(aplikasiKontak().container.kontakRepository) }
        initializer { DetailViewModel(aplikasiKontak().container.kontakRepository) }
        initializer { UpdateViewModel(aplikasiKontak().container.kontakRepository) }
    }
}

fun CreationExtras.aplikasiKontak():MahasiswaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as MahasiswaApplication)