package com.unipiloto.startedservices;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int CODIGO_PERMISO_NOTIFICACIONES = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pedirPermisoNotificaciones();

        Button btnChiste = findViewById(R.id.btn_chiste);
        Button btnCalificarTaller = findViewById(R.id.btn_calificar_taller);
        Button btnEntregarTarea = findViewById(R.id.btn_entregar_tarea);

        btnChiste.setOnClickListener(view -> iniciarServicio(
                DelayedMessageService.TIPO_CHISTE,
                getString(R.string.chiste_pregunta),
                getString(R.string.chiste_respuesta)));

        btnCalificarTaller.setOnClickListener(view -> iniciarServicio(
                DelayedMessageService.TIPO_CALIFICACION,
                "Calificación de taller",
                getString(R.string.mensaje_calificacion)));

        btnEntregarTarea.setOnClickListener(view -> iniciarServicio(
                DelayedMessageService.TIPO_ENTREGA,
                "Nueva entrega",
                getString(R.string.mensaje_entrega)));
    }

    private void iniciarServicio(int tipo, String titulo, String mensaje) {
        Intent intent = new Intent(this, DelayedMessageService.class);
        intent.putExtra(DelayedMessageService.EXTRA_TIPO, tipo);
        intent.putExtra(DelayedMessageService.EXTRA_TITULO, titulo);
        intent.putExtra(DelayedMessageService.EXTRA_MENSAJE, mensaje);
        startService(intent);
    }

    private void pedirPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    CODIGO_PERMISO_NOTIFICACIONES);
        }
    }
}
