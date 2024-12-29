package com.example.restapi.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restapi.navigation.DestinasiNavigasi
import com.example.restapi.ui.costumewidget.CostumeTopAppBar
import com.example.restapi.ui.viewmodel.PenyediaViewModel
import com.example.restapi.ui.viewmodel.UpdateViewModel
import com.example.restapi.ui.viewmodel.UpdateUiEvent
import com.example.restapi.ui.viewmodel.UpdateUiState

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update/{nim}"
    override val titleRes = "Update Mahasiswa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(
    nim: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()

    // Load data once when the screen opens
    LaunchedEffect(nim) {
        viewModel.loadMahasiswaData(nim)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (uiState) {
                is UpdateUiState.Loading -> CircularProgressIndicator()
                is UpdateUiState.Success -> {
                    val mahasiswa = (uiState as UpdateUiState.Success).mahasiswa
                    UpdateForm(
                        nimMhs = mahasiswa.nim,
                        namaMhs = mahasiswa.nama,
                        alamatMhs = mahasiswa.alamat,
                        jenisKelaminMhs = mahasiswa.jenisKelamin,
                        kelasMhs = mahasiswa.kelas,
                        angkatanMhs = mahasiswa.angkatan,
                        onUpdateClick = {
                            viewModel.updateUiState(it)
                            viewModel.updateMahasiswa()
                            onNavigateBack()
                        }
                    )
                }
                is UpdateUiState.Error -> {
                    Text("Error: ${(uiState as UpdateUiState.Error).message}")
                }
                else -> {}
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateForm(
    nimMhs: String,
    namaMhs: String,
    alamatMhs: String,
    jenisKelaminMhs: String,
    kelasMhs: String,
    angkatanMhs: String,
    onUpdateClick: (UpdateUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var nama by remember { mutableStateOf(namaMhs) }
    var alamat by remember { mutableStateOf(alamatMhs) }
    var jenisKelamin by remember { mutableStateOf(jenisKelaminMhs) }
    var kelas by remember { mutableStateOf(kelasMhs) }
    var angkatan by remember { mutableStateOf(angkatanMhs) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = alamat,
            onValueChange = { alamat = it },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = jenisKelamin,
            onValueChange = { jenisKelamin = it },
            label = { Text("Jenis Kelamin") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = kelas,
            onValueChange = { kelas = it },
            label = { Text("Kelas") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = angkatan,
            onValueChange = { angkatan = it },
            label = { Text("Angkatan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(
            onClick = {
                onUpdateClick(
                    UpdateUiEvent(
                        nim = nimMhs,
                        nama = nama,
                        alamat = alamat,
                        jenisKelamin = jenisKelamin,
                        kelas = kelas,
                        angkatan = angkatan
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}