package com.example.restapi.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.restapi.model.Mahasiswa
import com.example.restapi.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeUiState{
    data class Succes(val mahasiswa: List<Mahasiswa>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeViewModel(private val mhs: MahasiswaRepository): ViewModel(){
    var mhsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    fun getMhs(){
        viewModelScope.launch {
            mhsUIState= HomeUiState.Loading
            mhsUIState=try {
                HomeUiState.Succes(mhs.getMahasiswa())
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteMhs(nim: String){
        viewModelScope.launch {
            try {
                mhs.deleteMahasiswa(nim)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}