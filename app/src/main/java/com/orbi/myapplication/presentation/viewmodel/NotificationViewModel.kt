package com.orbi.myapplication.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.orbi.myapplication.domain.repository.NotificationRepository
import com.orbi.myapplication.data.repository.NotificationRepositoryImpl

/**
 * ViewModel para gestionar las notificaciones.
 *
 * Expone un estado observable (lista de notificaciones) a la capa de presentación.
 */
class NotificationViewModel(
    // En un proyecto real se utilizaría DI para inyectar el repositorio.
    private val notificationRepository: NotificationRepository = NotificationRepositoryImpl()
) : ViewModel() {

    // Estado observable que contiene la lista de notificaciones (título y cuerpo)
    val notifications = mutableStateListOf<Pair<String, String>>()

    /**
     * Carga las notificaciones del repositorio y actualiza el estado.
     */
    fun loadNotifications() {
        notifications.clear()
        notifications.addAll(notificationRepository.getNotifications())
    }
}