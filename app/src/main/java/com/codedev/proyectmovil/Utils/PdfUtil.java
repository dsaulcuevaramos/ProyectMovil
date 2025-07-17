package com.codedev.proyectmovil.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.codedev.proyectmovil.Models.Requests.UsuarioRequest;
import com.codedev.proyectmovil.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PdfUtil {
    public static void generarReporteUsuarios(Context context, List<UsuarioRequest> listaUsuarios, Uri uri) throws IOException {
        final int PAGE_WIDTH = 595;
        final int PAGE_HEIGHT = 842;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        LayoutInflater inflater = LayoutInflater.from(context);
        View content = inflater.inflate(R.layout.usuario_reporte, null);

        TextView reportDate = content.findViewById(R.id.report_date);
        String fechaActual = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        reportDate.setText("Fecha: " + fechaActual);

        LinearLayout tableContainer = content.findViewById(R.id.table_container);
        for (UsuarioRequest usuario : listaUsuarios) {
            TextView row = new TextView(context);
            String textoFila = usuario.getNombrePersona() + " " + usuario.getApellidoPersona() + " (" + usuario.getRolNombre() + ")";
            row.setText(textoFila);
            row.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            row.setTextSize(12f);
            row.setPadding(0, 4, 0, 4);
            tableContainer.addView(row);
        }

        content.measure(
                View.MeasureSpec.makeMeasureSpec(PAGE_WIDTH, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(PAGE_HEIGHT, View.MeasureSpec.EXACTLY)
        );
        content.layout(0, 0, PAGE_WIDTH, PAGE_HEIGHT);
        content.draw(canvas);

        document.finishPage(page);

        ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "w");
        FileOutputStream fos = new FileOutputStream(pfd.getFileDescriptor());
        document.writeTo(fos);

        document.close();
        fos.close();
        pfd.close();
    }
}
