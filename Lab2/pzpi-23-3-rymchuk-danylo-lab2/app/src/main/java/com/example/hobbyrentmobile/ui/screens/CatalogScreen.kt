package com.example.hobbyrentmobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hobbyrentmobile.data.models.Equipment
import com.example.hobbyrentmobile.viewmodel.EquipmentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(viewModel: EquipmentViewModel) {
    val equipmentList by viewModel.equipmentList
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // Стан для діалогового вікна оренди
    var selectedEquipment by remember { mutableStateOf<Equipment?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HobbyRent Каталог", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage != null -> {
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp)
                    )
                }
                equipmentList.isEmpty() -> {
                    Text(
                        text = "Список спорядження порожній",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(equipmentList) { equipment ->
                            EquipmentCard(
                                equipment = equipment,
                                onRentClick = { selectedEquipment = equipment }
                            )
                        }
                    }
                }
            }
        }

        // Діалог оренди
        selectedEquipment?.let { equipment ->
            RentalDialog(
                equipment = equipment,
                onDismiss = { selectedEquipment = null },
                onConfirm = { hours ->
                    viewModel.rentEquipment(
                        equipmentId = equipment.id,
                        hours = hours,
                        onSuccess = { selectedEquipment = null },
                        onError = { /* Можна додати Toast з помилкою */ }
                    )
                }
            )
        }
    }
}

@Composable
fun EquipmentCard(equipment: Equipment, onRentClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = equipment.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Категорія: ${equipment.category}", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = "Ціна: ${equipment.hourlyRate} ₴/год", color = MaterialTheme.colorScheme.primary)

            Spacer(modifier = Modifier.height(8.dp))

            // Бейдж статусу
            val statusColor = if (equipment.status == "AVAILABLE")
                MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

            Text(
                text = equipment.status,
                color = statusColor,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onRentClick,
                enabled = equipment.status == "AVAILABLE",
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (equipment.status == "AVAILABLE") "Орендувати" else "Зайнято")
            }
        }
    }
}

@Composable
fun RentalDialog(equipment: Equipment, onDismiss: () -> Unit, onConfirm: (Int) -> Unit) {
    var hours by remember { mutableStateOf(1) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Оренда: ${equipment.title}") },
        text = {
            Column {
                Text("Оберіть кількість годин:")
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { if (hours > 1) hours-- }) { Text("-") }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("$hours год", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = { hours++ }) { Text("+") }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("До сплати: ${equipment.hourlyRate * hours} ₴", fontWeight = FontWeight.Bold)
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(hours) }) {
                Text("Підтвердити")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Скасувати")
            }
        }
    )
}