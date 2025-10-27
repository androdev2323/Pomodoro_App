package com.example.pomodoro.presentation.AppBlock.Components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppBlockSearchBar(searchQuery: String,onQueryChanged:(String) -> Unit) {
    TextField(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        value = searchQuery,
        placeholder = { Text(text = "Search for Apps" )},
        onValueChange = {},
        colors = TextFieldDefaults.colors(

            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(10.dp),
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "search") }

        
    )
}


@Preview(showBackground = true)
@Composable
private fun AppBlockSearchBarPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        AppBlockSearchBar("",{})
    }
}