package com.example.pomodoro.presentation.BottomSheet.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun TaskEdittext( title:String, hint:String,value:String,onValueChange: (String) -> Unit){
   Column(){
     Text(text = title, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
       Spacer(modifier = Modifier.height(5.dp))
       OutlinedTextField(value =value , onValueChange ={ onValueChange(it) } )
   }
}