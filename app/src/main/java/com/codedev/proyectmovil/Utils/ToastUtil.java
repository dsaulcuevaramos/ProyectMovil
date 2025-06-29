package com.codedev.proyectmovil.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codedev.proyectmovil.R;

public class ToastUtil {
    public static void show(Context context, String mensaje, String tipo) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        LinearLayout fondoLayout = layout.findViewById(R.id.toast_layout);
        ImageView icono = layout.findViewById(R.id.toast_icon);
        TextView texto = layout.findViewById(R.id.toast_text);

        texto.setText(mensaje);

        GradientDrawable fondo = (GradientDrawable) fondoLayout.getBackground();

        switch (tipo) {
            case "success":
                fondo.setColor(Color.parseColor("#43A047"));
                icono.setImageResource(R.drawable.ic_check_circle);
                break;
            case "warning":
                fondo.setColor(Color.parseColor("#FFA000"));
                icono.setImageResource(android.R.drawable.ic_dialog_alert);
                break;
            case "danger":
                fondo.setColor(Color.parseColor("#E53935"));
                icono.setImageResource(android.R.drawable.ic_delete);
                icono.setColorFilter(Color.WHITE);
                break;
            default:
                fondo.setColor(Color.parseColor("#2196F3")); // info
                icono.setImageResource(android.R.drawable.ic_dialog_info);
                break;
        }

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
