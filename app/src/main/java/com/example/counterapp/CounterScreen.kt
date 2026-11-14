package com.example.counterapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.counterapp.ui.theme.CounterAppTheme

// Values and the callback are parameters so the caller owns the state.
// This stateless screen only displays values and reports button clicks (state hoisting).
@Composable
fun CounterScreen(
    temporaryCount: Int,
    savedCount: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Counters",
            style = MaterialTheme.typography.titleLarge
        )
        Text(text = "Temporary counter: $temporaryCount")
        Text("Uses remember: resets to zero after rotation.", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Saved counter: $savedCount")
        Text("Uses rememberSaveable: restored after rotation.", style = MaterialTheme.typography.bodyMedium)
        Button(onClick = onIncrement, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Increase both counters")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterScreenPreview() {
    CounterAppTheme {
        CounterScreen(
            temporaryCount = 2,
            savedCount = 5,
            onIncrement = {}
        )
    }
}
