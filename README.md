# MessagePushTest

Este proyecto es un ejemplo práctico que muestra cómo implementar notificaciones push en Android mediante Firebase Cloud Messaging (FCM). Se ha desarrollado aplicando el patrón MVVM (Modelo - Vista - VistaModelo) y los principios de Clean Architecture, separando las responsabilidades en capas (Data, Domain y Presentation). Además, se utiliza un catálogo de versiones (libsVersion) para administrar las dependencias del proyecto.

## Tabla de Contenidos

- [Descripción](#descripción)
- [Requisitos](#requisitos)
- [Proceso de Creación en Firebase](#proceso-de-creación-en-firebase)
- [Configuración del Proyecto en Android Studio](#configuración-del-proyecto-en-android-studio)
- [Arquitectura del Proyecto](#arquitectura-del-proyecto)
- [Implementación](#implementación)
- [Gestión de Dependencias (libsVersion)](#gestión-de-dependencias-libsversion)
- [Integración con Git](#integración-con-git)
- [Instrucciones de Ejecución](#instrucciones-de-ejecución)
- [Notas Adicionales](#notas-adicionales)

## Descripción

**MessagePushTest** es una aplicación Android que demuestra cómo:
- Configurar un proyecto en Firebase para recibir notificaciones push.
- Aplicar el patrón MVVC (MVVM) para una separación limpia de responsabilidades.
- Estructurar la aplicación siguiendo los principios de Clean Architecture, dividiendo el proyecto en:
  - **Capa de Datos:** Encargada de la comunicación con Firebase y otras fuentes de datos.
  - **Capa de Dominio:** Contiene la lógica de negocio y los casos de uso.
  - **Capa de Presentación:** Implementa la interfaz de usuario y el ViewModel que gestiona la interacción con la vista.
- Utilizar un catálogo de versiones (libsVersion) para gestionar las dependencias de forma centralizada.

## Requisitos

- [Android Studio](https://developer.android.com/studio)
- Cuenta en [Firebase](https://console.firebase.google.com/)
- Git para control de versiones
- Conexión a Internet

## Proceso de Creación en Firebase

1. **Acceso a Firebase Console:**
   - Visita [Firebase Console](https://console.firebase.google.com/) y crea un nuevo proyecto.
   - Asigna un nombre al proyecto y configura Google Analytics si lo requieres.

2. **Registro de la Aplicación Android:**
   - Haz clic en el ícono de Android para agregar tu aplicación.
   - Ingresa el nombre del paquete (por ejemplo, `com.orbi.myapplication`).
   - Descarga el archivo `google-services.json` y colócalo en el directorio `app/` del proyecto.

3. **Configuración de Firebase Cloud Messaging (FCM):**
   - Dentro de la consola, accede a la sección **Cloud Messaging** para obtener las credenciales y configurar el envío de notificaciones.

## Configuración del Proyecto en Android Studio

1. **Importación del Proyecto:**
   - Abre Android Studio y carga el proyecto **MessagePushTest**.

2. **Integración del Archivo de Configuración:**
   - Asegúrate de que el archivo `google-services.json` se encuentre en el directorio `app/`.

3. **Actualización de los Archivos Gradle:**
   - En el archivo `build.gradle` a nivel de proyecto, añade el classpath para Google Services:
     ```gradle
     buildscript {
         dependencies {
             classpath 'com.google.gms:google-services:4.3.10' // Verifica la versión actualizada
         }
     }
     ```
   - En el archivo `build.gradle` del módulo `app`, aplica el plugin de Google Services y añade las dependencias de Firebase:
     ```gradle
     plugins {
         id 'com.android.application'
         id 'com.google.gms.google-services'
     }

     dependencies {
         implementation 'com.google.firebase:firebase-messaging:23.0.5' // Verifica la versión actual
         // Otras dependencias definidas en el catálogo de versiones (libsVersion)
     }
     ```

## Arquitectura del Proyecto

El proyecto sigue dos enfoques complementarios para mantener un código modular y escalable:

- **MVVC (MVVM):**  
  Se implementa el patrón Modelo - Vista - VistaModelo para separar la lógica de presentación de la interfaz de usuario.  
  - **Modelo:** Representa los datos y entidades del negocio.
  - **VistaModelo:** Gestiona la lógica de la interfaz, interactúa con la capa de dominio y actualiza la vista mediante observables.
  - **Vista:** Actividades o fragmentos que muestran la información al usuario.

- **Clean Architecture:**  
  La aplicación está dividida en tres capas principales:
  - **Capa de Datos:** Encargada de la comunicación con servicios externos (por ejemplo, Firebase) y la persistencia de datos.
  - **Capa de Dominio:** Contiene los casos de uso y la lógica de negocio, manteniendo las reglas de la aplicación independientes de frameworks externos.
  - **Capa de Presentación:** Responsable de la interacción con el usuario a través de la UI y la coordinación mediante ViewModels.

## Implementación

1. **Inicialización de Firebase y Suscripción a Temas:**
   - En el ViewModel o en la clase de inicialización de la aplicación, se configura Firebase para suscribirse a un tópico (por ejemplo, "all"):
     ```kotlin
     Firebase.messaging.subscribeToTopic("all")
         .addOnCompleteListener { task ->
             if (task.isSuccessful) {
                 // Suscripción exitosa
             } else {
                 // Manejo de error
             }
         }
     ```

2. **Servicio de Mensajería:**
   - Se ha creado una clase que extiende `FirebaseMessagingService` para gestionar la recepción de notificaciones:
     ```kotlin
     class MyFirebaseMessagingService : FirebaseMessagingService() {
         override fun onMessageReceived(remoteMessage: RemoteMessage) {
             // Procesar el mensaje y generar una notificación en la UI
         }
     }
     ```
   - No olvides declarar este servicio en el `AndroidManifest.xml`:
     ```xml
     <service
         android:name=".MyFirebaseMessagingService"
         android:exported="false">
         <intent-filter>
             <action android:name="com.google.firebase.MESSAGING_EVENT" />
         </intent-filter>
     </service>
     ```

3. **Integración de la Lógica de Negocio:**
   - Los casos de uso y la lógica de negocio se encuentran en la capa de dominio, permitiendo reutilizar el código y facilitar las pruebas unitarias.

4. **Implementación de la Interfaz de Usuario:**
   - La UI se conecta con el ViewModel para observar cambios en los datos y actualizar la vista en consecuencia, siguiendo las prácticas recomendadas del patrón MVVC.

## Gestión de Dependencias (libsVersion)

El proyecto utiliza un catálogo de versiones (ubicado en `gradle/libs.versions.toml`) para centralizar la administración de las dependencias y sus versiones. Esto permite:
- Actualizar de forma sencilla las librerías del proyecto.
- Mantener la coherencia en las versiones utilizadas a lo largo del código.
- Mejorar la legibilidad y el mantenimiento del archivo de configuración de dependencias.

Ejemplo de entrada en el archivo `libs.versions.toml`:
```toml
[versions]
firebase-messaging = "23.0.5"
google-services = "4.3.10"

[libraries]
firebase-messaging = { module = "com.google.firebase:firebase-messaging", version.ref = "firebase-messaging" }
google-services = { module = "com.google.gms:google-services", version.ref = "google-services" }

```

## Integración con Git

1. **Inicializar Git:**
   - Abre la terminal (o PowerShell) en el directorio del proyecto y ejecuta:
     ```bash
     git init
     ```

2. **Agregar Archivos y Realizar Commit:**
   - Agrega todos los archivos:
     ```bash
     git add .
     ```
   - Realiza el commit inicial:
     ```bash
     git commit -m "Commit inicial: Configuración del proyecto y Firebase"
     ```

3. **Configurar el Repositorio Remoto y Push:**
   - Agrega el repositorio remoto:
     ```bash
     git remote add origin https://github.com/JamilSec/MessagePushTest.git
     ```
   - Sube los cambios a la rama `main`:
     ```bash
     git push origin main
     ```

## Instrucciones de Ejecución

1. Abre el proyecto en Android Studio.
2. Conecta un dispositivo Android o utiliza un emulador.
3. Ejecuta la aplicación desde Android Studio.
4. Envía notificaciones push a través de la consola de Firebase y verifica que se reciban correctamente en la aplicación.

## Notas Adicionales

- **Finales de Línea (LF vs CRLF):**  
  Las advertencias sobre la conversión de LF a CRLF son comunes en entornos Windows y no afectan el funcionamiento del proyecto.

- **Actualizaciones y Versiones:**  
  Asegúrate de utilizar las versiones más recientes de las dependencias de Firebase y del plugin de Google Services. Consulta la [documentación oficial de Firebase](https://firebase.google.com/docs/cloud-messaging) para más detalles.

- **Soporte y Documentación:**  
  Si tienes problemas durante la implementación, revisa la documentación oficial de [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging) y los foros de la comunidad.
