package com.unipiloto.startedservices;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class DelayedMessageService extends IntentService {

    public static final String EXTRA_TIPO = "tipo";
    public static final String EXTRA_TITULO = "titulo";
    public static final String EXTRA_MENSAJE = "mensaje";

    public static final int TIPO_CHISTE = 1;
    public static final int TIPO_CALIFICACION = 2;
    public static final int TIPO_ENTREGA = 3;

    private static final String CANAL_ID = "laboratorio_started_services";

    public DelayedMessageService() {
        super("DelayedMessageService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        crearCanalNotificaciones();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        int tipo = intent.getIntExtra(EXTRA_TIPO, TIPO_CHISTE);
        String titulo = intent.getStringExtra(EXTRA_TITULO);
        String mensaje = intent.getStringExtra(EXTRA_MENSAJE);

        synchronized (this) {
            try {
                wait(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.v("DelayedMessageService", "El mensaje es: " + mensaje);
        mostrarNotificacion(tipo, titulo, mensaje);
    }

    private void mostrarNotificacion(int tipo, String titulo, String mensaje) {
        Intent intentAccion = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, tipo, intentAccion,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CANAL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{0, 1000})
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(tipo, builder.build());
    }

    private void crearCanalNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(
                    CANAL_ID,
                    "Notificaciones del laboratorio",
                    NotificationManager.IMPORTANCE_HIGH);
            canal.setDescription("Avisos de calificaciones y entregas del curso");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(canal);
        }
    }
}
