package com.orbi.myapplication.data.repository

import com.orbi.myapplication.domain.repository.NotificationRepository

/**
 * Implementación en memoria del NotificationRepository.
 *
 * En una aplicación real se podría usar una base de datos local (Room, etc.).
 */
class NotificationRepositoryImpl : NotificationRepository {

    // Almacenamiento en memoria de las notificaciones.
    private val notifications = mutableListOf<Pair<String, String>>()

    override fun addNotification(title: String, body: String) {
        notifications.add(Pair(title, body))
    }

    override fun getNotifications(): List<Pair<String, String>> {
        return notifications.toList()
    }
}