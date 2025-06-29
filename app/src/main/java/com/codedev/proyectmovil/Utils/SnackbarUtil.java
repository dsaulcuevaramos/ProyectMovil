package com.codedev.proyectmovil.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.codedev.proyectmovil.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

public class SnackbarUtil {

    public static void show(View view, String message, String type) {
        Context context = view.getContext();

        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        View snackbarRoot = snackbar.getView();

        snackbarRoot.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        snackbarRoot.setPadding(0, 0, 0, 0);

        View customView = LayoutInflater.from(context).inflate(R.layout.custom_snackbar, null);

        CardView cardView = customView.findViewById(R.id.snackbar_card);
        ImageView icon = customView.findViewById(R.id.snackbar_icon);
        MaterialTextView text = customView.findViewById(R.id.snackbar_text);

        text.setText(message);

        int backgroundColor;
        int contentColor = ContextCompat.getColor(context, R.color.colorOnMessage);
        int iconResId;

        switch (type) {
            case "success":
                backgroundColor = ContextCompat.getColor(context, R.color.colorSuccess);
                iconResId = R.drawable.ic_check_circle;
                break;
            case "warning":
                backgroundColor = ContextCompat.getColor(context, R.color.colorWarning);
                iconResId = android.R.drawable.ic_dialog_alert;
                break;
            case "danger":
                backgroundColor = ContextCompat.getColor(context, R.color.colorDanger);
                iconResId = android.R.drawable.ic_delete;
                break;
            default: // INFO
                backgroundColor = ContextCompat.getColor(context, R.color.colorInfo);
                iconResId = android.R.drawable.ic_dialog_info;
                break;
        }

        cardView.setCardBackgroundColor(backgroundColor);
        icon.setImageResource(iconResId);
        icon.setColorFilter(contentColor);
        text.setTextColor(contentColor);

        ((ViewGroup) snackbarRoot).addView(customView, 0);

        snackbar.show();
    }
}
