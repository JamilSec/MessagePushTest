package com.orbi.myapplication.data.remote.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.orbi.myapplication.domain.repository.NotificationRepository
import com.orbi.myapplication.data.repository.NotificationRepositoryImpl

/**
 * Servicio que extiende FirebaseMessagingService para manejar la generación del token
 * y la recepción de mensajes FCM.
 *
 * Este servicio es parte de la capa de datos (infrastructure) y se encarga de:
 * - Recibir y gestionar el token FCM (onNewToken).
 * - Procesar mensajes entrantes (onMessageReceived) y delegar la actualización
 *   en el repositorio de notificaciones.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    // En un proyecto real se usaría inyección de dependencias.
    // Aquí instanciamos directamente la implementación del repositorio.
    private val notificationRepository: NotificationRepository = NotificationRepositoryImpl()

    /**
     * Se invoca cuando se genera un nuevo token FCM.
     *
     * @param token El nuevo token generado.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Nuevo token generado: $token")
        // Aquí podrías enviar el token a tu backend para asociarlo al usuario.
        // Ejemplo: sendTokenToServer(token)
    }

    /**
     * Se invoca cuando se recibe un mensaje FCM.
     *
     * Procesa el payload de "notification" y actualiza el repositorio de notificaciones.
     *
     * @param message El mensaje recibido.
     */
    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let { notification ->
            val title = notification.title ?: "Notificación"
            val body = notification.body ?: ""
            Log.d("FCM", "Notificación recibida: Título = $title, Cuerpo = $body")
            // Actualiza el repositorio (en este ejemplo, en memoria)
            notificationRepository.addNotification(title, body)
            // Opcional: Puedes mostrar una notificación local usando NotificationCompat aquí.
        }
    }
}