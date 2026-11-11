# Laboratorio: Started Services

Aplicación Android (Java) que implementa un servicio iniciado (`DelayedMessageService`,
usando `IntentService`) siguiendo la guía del laboratorio.

## Funcionalidad base (guía)

Botón "Probar chiste": inicia el servicio, que espera 10 segundos, registra el mensaje
en el log y lo muestra como notificación del sistema.

## Funcionalidad adicional (requisito del laboratorio)

Se agregaron dos escenarios relacionados con el proyecto de curso:

- "Simular calificación de taller": notifica al estudiante que su taller ya fue
  calificado (4.5 / 5.0).
- "Simular entrega de tarea": notifica al docente que un estudiante entregó una
  tarea y queda pendiente de calificar.

Las tres notificaciones usan el mismo `DelayedMessageService`, diferenciadas por un
tipo (`EXTRA_TIPO`) que determina el título, el texto y el ID de la notificación, de
forma que las tres pueden coexistir en la barra de notificaciones.

En Android 13 (API 33) o superior, la app solicita el permiso `POST_NOTIFICATIONS`
en tiempo de ejecución la primera vez que se abre.

## Ejecutar

1. Abrir esta carpeta en Android Studio.
2. Sincronizar Gradle y ejecutar `app` en un emulador o dispositivo con Android 7.0
   (API 24) o superior.
3. Pulsar cualquiera de los tres botones y esperar 10 segundos para ver la
   notificación.
