package com.example.dentalcare;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dentalcare.modelo.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Questionnaire extends AppCompatActivity {

    Dialog optionsDialog;
    Fragment fragment;
    Spinner respuesta1,respuesta2,respuesta3,respuesta4,respuesta5;
    TextView txtclose;
    Button btnCargarFoto, btnTomarFoto;
    String rutaImagen, nombreImagen, urlImagen;
    Uri fotoUri;
    DatabaseReference mRootReference;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private StorageReference mStorage;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        mStorage = FirebaseStorage.getInstance().getReference();
        mRootReference = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        respuesta1 = findViewById(R.id.edTxtPregunta1);
        respuesta2 = findViewById(R.id.edTxtPregunta2);
        respuesta3 = findViewById(R.id.edTxtPregunta3);
        respuesta4 = findViewById(R.id.edTxtPregunta4);
        respuesta5 = findViewById(R.id.edTxtPregunta5);

        optionsDialog = new Dialog(this);
        mProgressDialog = new ProgressDialog(this);

    }

    public void ShowPopup(View v){
        optionsDialog.setContentView(R.layout.popup);
        txtclose = (TextView) optionsDialog.findViewById(R.id.txtClose);
        btnCargarFoto = (Button) optionsDialog.findViewById(R.id.btnCargarFoto);
        btnTomarFoto = (Button) optionsDialog.findViewById(R.id.btnTomarFoto);

        //Cerrar dialog
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsDialog.dismiss();
            }
        });
        //Boton Cargar Foto
        btnCargarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        //Boton tomar foto -> presionar -> abrir camara
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });

        optionsDialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            StorageReference filePath = mStorage.child("Fotos").child(uri.getLastPathSegment());
            mProgressDialog.setTitle("Subiendo...");
            mProgressDialog.setMessage("Subiendo foto");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            nombreImagen = uri.getLastPathSegment().toString();

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgressDialog.dismiss();
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            urlImagen = uri.toString();
                            Toast.makeText(Questionnaire.this, "Imágen cargada exitosamente.", Toast.LENGTH_SHORT).show();
                            obtenerDatosEncuesta();
                            //btnContinuar.setVisibility(View.VISIBLE);
                        }
                    });


                }
            });

        }


            if (requestCode == 2 && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
                Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
                StorageReference filePath = mStorage.child("Fotos").child(fotoUri.getLastPathSegment());
                mProgressDialog.setTitle("Subiendo...");
                mProgressDialog.setMessage("Subiendo foto");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                nombreImagen = fotoUri.getLastPathSegment().toString();


                filePath.putFile(fotoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mProgressDialog.dismiss();

                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                urlImagen = uri.toString();
                                Toast.makeText(Questionnaire.this, "Imágen cargada exitosamente.", Toast.LENGTH_SHORT).show();
                                obtenerDatosEncuesta();
                            }
                        });
                    }
                });

            }
    }

    //metodo para abrir camara
    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File imgFile = null;

        try{
            imgFile = crearImg();
        }catch (IOException ex){
            Log.e("Error", ex.toString());
        }

        if (imgFile != null){
            fotoUri = FileProvider.getUriForFile (this, "com.example.dentalcare.fileprovider", imgFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            startActivityForResult(intent, 2);
        }
    }

    public void obtenerDatosEncuesta(){
        String RP1 = respuesta1.getSelectedItem().toString();
        String RP2 = respuesta2.getSelectedItem().toString();
        String RP3 = respuesta3.getSelectedItem().toString();
        String RP4 = respuesta4.getSelectedItem().toString();
        String RP5 = respuesta5.getSelectedItem().toString();
        String nombreImg = nombreImagen;
        String urlImg = urlImagen;
        String uid = auth.getUid().toString();

        subirEncuesta(RP1, RP2, RP3, RP4, RP5, nombreImg, urlImg, uid);
        abrirDiagnosticos();

//        String[] datos = new String[]{RP1, RP2, RP3, RP4, RP5,};
//
//
//        Intent form = new Intent(this, Presentacion.class);
//
//        Bundle pasarRuta = new Bundle();
//        pasarRuta.putString("keyRuta",rutaImagen);
//
//
//        Bundle pasarDatos = new Bundle();
//        pasarDatos.putStringArray("keyDatos",datos);
//
//        form.putExtras(pasarDatos);
//        form.putExtras(pasarRuta);
//
//        startActivity(form);
    }

    private void subirEncuesta(String RP1, String RP2, String RP3, String RP4, String RP5,String nombreImagen, String urlImg, String uid) {
        Date format = Calendar.getInstance().getTime();
        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String Fecha = fecha.format(format);

        String urlHSV = "";

        Date hformat = Calendar.getInstance().getTime();
        SimpleDateFormat hora = new SimpleDateFormat("hh:mm", Locale.getDefault());
        String Hora = hora.format(hformat.getTime());

        String key = mRootReference.push().getKey();

        Map<String, Object> RPEncuesta = new HashMap<>();
        RPEncuesta.put("uid", uid);
        RPEncuesta.put("key", key);
        RPEncuesta.put("RP1", RP1);
        RPEncuesta.put("RP2", RP2);
        RPEncuesta.put("RP3", RP3);
        RPEncuesta.put("RP4", RP4);
        RPEncuesta.put("RP5", RP5);
        RPEncuesta.put("Hora", Hora);
        RPEncuesta.put("Fecha", Fecha);
        RPEncuesta.put("nameImg",nombreImagen);
        RPEncuesta.put("urlImg", urlImg);
        RPEncuesta.put("urlHSV", urlHSV);

        db.collection("Diagnosticos").document(key).set(RPEncuesta).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Questionnaire.this, "Respuestas enviadas correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Questionnaire.this, "Error al enviar las respuestas.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void abrirDiagnosticos(){
        Intent home= new Intent(this, Home.class);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home);
        finishAffinity();
    }


    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void Cancel(View view){
        Intent cancel = new Intent(this, Home.class);
        cancel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(cancel);
        finishAffinity();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent cancel = new Intent(this, Home.class);
        cancel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(cancel);
        finishAffinity();
    }

    private  File crearImg() throws IOException {
        String fileName = "dental_care_";
        File storage = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File img = File.createTempFile(fileName, ".jpg", storage);

        rutaImagen = img.getAbsolutePath();
        return img;
    }
}