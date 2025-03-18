package com.orbi.myapplication.presentation.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Composable que muestra un saludo.
 *
 * @param name Nombre a mostrar en el saludo.
 * @param modifier Modificador para personalizar el estilo.
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}