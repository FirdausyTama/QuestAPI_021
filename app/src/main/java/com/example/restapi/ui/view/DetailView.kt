package com.example.restapi.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restapi.model.Mahasiswa
import com.example.restapi.navigation.DestinasiNavigasi
import com.example.restapi.repository.MahasiswaRepository
import com.example.restapi.ui.costumewidget.CostumeTopAppBar
import com.example.restapi.ui.viewmodel.DetailUiState
import com.example.restapi.ui.viewmodel.DetailViewModel
import com.example.restapi.ui.viewmodel.PenyediaViewModel

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Mahasiswa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    nim: String,
    onNavigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(nim) {
        viewModel.getMahasiswaByNim(nim)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateBack,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Add Kontak")
            }
        },
    ) { innerPadding ->
        when (val state = uiState.value) {
            is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
            is DetailUiState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DetailCard(mahasiswa = state.mahasiswa)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            is DetailUiState.Error -> OnError(retryAction = { viewModel.getMahasiswaByNim(nim) })
        }
    }
}



@Composable
fun DetailCard(mahasiswa: Mahasiswa) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Nama: ${mahasiswa.nama}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "NIM: ${mahasiswa.nim}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Kelas: ${mahasiswa.kelas}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Alamat: ${mahasiswa.alamat}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Angkatan: ${mahasiswa.angkatan}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
