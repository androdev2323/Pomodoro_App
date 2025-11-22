package com.example.pomodoro.presentation.AppBlock.Components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pomodoro.R

@Composable
fun AppBlockSearchBar(
    searchQuery: String,
    onQueryChanged: (String) -> Unit,
    onClearClicked: () -> Unit
) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            value = searchQuery,
            placeholder = { Text(text = "Search for Apps") },
            onValueChange = { onQueryChanged(it) },
            colors = TextFieldDefaults.colors(

                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search"
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) IconButton(onClick = onClearClicked) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "search"
                    )
                }
            }


        )
    }

@Composable
fun EmptySearchContent(){
    Column(modifier = Modifier.fillMaxSize().padding(10.dp), verticalArrangement = Arrangement.Center){
        Image(painter = painterResource(id = R.drawable.ic_noresult), contentDescription = "", )
        Text("No Results Found", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(9.dp))
        Text("Try checking for spelling errors or try a different search query", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.fillMaxWidth().padding(start = 40.dp), textAlign = TextAlign.Justify)

    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EmptySearchContentPreview(){
    EmptySearchContent()
}

@Preview(showBackground = true)
@Composable
private fun AppBlockSearchBarPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        AppBlockSearchBar("", {}, {})
    }
}