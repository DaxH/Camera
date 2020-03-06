package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bnt_tomar_foto;
    private ImageView img_foto;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final String ruta_foto = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "Imagenes";
    private File archivo_foto = new File(ruta_foto);
    String imagen_nombre = "imagenApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Permisos();

        bnt_tomar_foto = (Button)findViewById(R.id.btn_tomar_foto);
        img_foto = (ImageView)findViewById(R.id.img_foto);

        bnt_tomar_foto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == bnt_tomar_foto){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,},1000);

            }else {
                archivo_foto.mkdirs();
                TomarFoto();
            }
        }
    }
    public  void TomarFoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img_foto.setImageBitmap(imageBitmap);
        }
    }

    private void Permisos(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }else{
            archivo_foto.mkdirs();
        }
    }
}
