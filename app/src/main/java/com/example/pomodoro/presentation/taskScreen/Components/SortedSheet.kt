package com.example.pomodoro.presentation.taskScreen.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.pomodoro.presentation.HomeScreen.SortedOrder

@Composable
fun SortedSheet(sortedOrder: SortedOrder, onDismiss: (selectedoption: SortedOrder) -> Unit) {
    val sortOptionList = SortedOrder.entries
    val (selectedOption, onOptionSelected) = remember { (mutableStateOf(sortedOrder)) }
    Dialog(onDismissRequest = { onDismiss(selectedOption) }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
        ) {
            Column(
                modifier = Modifier
                    .selectableGroup()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    "Sort by",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                sortOptionList.forEach {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = it == selectedOption,
                                onClick = {
                                    onOptionSelected(it)
                                },

                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        RadioButton(
                            selected = (it == selectedOption),
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.tertiary
                            )
                        )
                        Text(text = it.name, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

    }
}