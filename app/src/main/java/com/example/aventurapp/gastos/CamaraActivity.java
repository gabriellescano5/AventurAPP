package com.example.aventurapp.gastos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aventurapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CamaraActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;

    Button btnCamara;
    ImageView imgView;
    String rutaImagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        btnCamara = findViewById(R.id.btnCamara);
        imgView = findViewById(R.id.imageView);

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              verificarPermisosCamara();
            }
        });

    }
    private void verificarPermisosCamara(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//            Si no tengo permisos, solicito al usuario
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},PERMISSION_REQUEST_CODE);
        } else {
//            Si tengo permisos, abro la cámara
            abrirCamara();
        }
    }

    public void onRequestPermissionsResult(int requestCoded, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCoded, permissions, grantResults);
        if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            abrirCamara();
        } else {
//            Permiso denegado, muestra un mensaje al usuario
            Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
        }
    }
    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){

            File imagenArchivo = null;
            try {
                imagenArchivo = crearImagen();
            } catch (IOException e){
                Log.e("Error", e.toString());
            }
            if(imagenArchivo != null){
                Uri fotoUri = FileProvider.getUriForFile(this,"com.example.aventurapp.fileprovider",imagenArchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
                startActivityForResult(intent,1);
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
            imgView.setImageBitmap(imgBitmap);
        }
    }
    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen,".jpg",directorio);

        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }
}