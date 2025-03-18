package com.orbi.myapplication.domain.repository

/**
 * Interfaz del repositorio de notificaciones.
 *
 * Define los métodos para agregar y recuperar notificaciones.
 */
interface NotificationRepository {
    /**
     * Agrega una notificación con el título y cuerpo especificados.
     *
     * @param title Título de la notificación.
     * @param body  Cuerpo del mensaje de la notificación.
     */
    fun addNotification(title: String, body: String)

    /**
     * Recupera la lista de notificaciones.
     *
     * @return Lista de pares (título, cuerpo).
     */
    fun getNotifications(): List<Pair<String, String>>
}