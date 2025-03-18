package com.orbi.myapplication.presentation.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import com.orbi.myapplication.presentation.ui.theme.MessagePushTestTheme
import com.orbi.myapplication.presentation.ui.components.Greeting
import com.orbi.myapplication.presentation.viewmodel.NotificationViewModel
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier

/**
 * MainActivity: Punto de entrada de la aplicación.
 *
 * Esta actividad configura el canal de notificaciones, gestiona permisos y
 * establece la UI usando Jetpack Compose.
 */
class MainActivity : ComponentActivity() {

    // En un proyecto real se usaría un ViewModelProvider o DI para obtener el ViewModel.
    private val notificationViewModel = NotificationViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Crear el canal de notificaciones para Android Oreo (API 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "1", // ID del canal (debe coincidir con el usado en MyFirebaseMessagingService)
                "Canal de Notificaciones",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal por defecto para notificaciones push"
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // 2. Solicitar el permiso POST_NOTIFICATIONS para Android 13+ (API 33)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }

        // 3. Cargar las notificaciones (si ya existen en el repositorio)
        notificationViewModel.loadNotifications()

        // 4. Configurar la UI usando Compose
        enableEdgeToEdge()
        setContent {
            MessagePushTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(name = "Android", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}