package app.com.optra;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class LectorPDF_Activity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private Button btnScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector_pdf_);

        // Metodo para abrir la camara y escanear pdf417
        mScannerView = new ZXingScannerView(LectorPDF_Activity.this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(LectorPDF_Activity.this);
        mScannerView.startCamera();

    }

    @Override
    public void handleResult(Result result) {
        Intent firma = new Intent(LectorPDF_Activity.this,Firma_Activity.class);
        String datos[] = result.getText().split("_#_");
        if(datos.length == 2){
            String rut = datos[0];
            String tipo = datos[1];
            firma.putExtra("rut", rut);
            firma.putExtra("tipo", tipo);
            startActivity(firma);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "Error al leer el código QR, por favor reintente.", Toast.LENGTH_SHORT).show();
            Intent lector = new Intent(LectorPDF_Activity.this,LectorPDF_Activity.class);
            startActivity(lector);
            finish();
        }

    }

};
