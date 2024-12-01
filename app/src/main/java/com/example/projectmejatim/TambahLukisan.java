package com.example.projectmejatim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class TambahLukisan extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView btTambahGambar;
    private TextView etJudulLukisan;
    private TextView etNamaPelukis;
    private TextView etDeskripsi;
    private Button btSimpan;
    ImageView btBacktoHalAdmin4;
    private ImageView imageview;
    private Uri imageUri;
    StorageReference storageRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_lukisan);

        btTambahGambar = findViewById(R.id.btTambahGambar);
        etJudulLukisan = findViewById(R.id.etJudulLukisan);
        etNamaPelukis = findViewById(R.id.etNamaPelukis);
        etDeskripsi = findViewById(R.id.etDeskripsi);
        btSimpan = findViewById(R.id.btSimpan);
        btBacktoHalAdmin4 = findViewById(R.id.btBacktoHalAdmin2);
        storageRef = FirebaseStorage.getInstance().getReference();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference();

        btTambahGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(Intent.ACTION_PICK);
                j.setType("image/*");
                startActivityForResult(j,PICK_IMAGE_REQUEST);
            }
        });

        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference lukisReference = dbRef.child("lukisans");
                String judul = etJudulLukisan.getText().toString();
                String penulis = etNamaPelukis.getText().toString();
                String deskripsi = etDeskripsi.getText().toString();
                Lukisan lukisan = null;

                try {
                    lukisan = new Lukisan();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if(imageUri != null) {
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                    StorageReference imagesRef = storageRef.child("images/" + UUID.randomUUID().toString());

                    UploadTask uploadTask = imagesRef.putFile(imageUri);

                    uploadTask.addOnSuccessListener(taskSnapshot -> {
                        Task<Uri> downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        downloadUrl.addOnSuccessListener( uri -> {
                            String key = lukisReference.push().getKey();
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("id", key);
                            data.put("judul", judul);
                            data.put("pelukis", penulis);
                            data.put("deskripsi", deskripsi);
                            data.put("url_gambar", uri.toString());
                        });
                    });

                }


                lukisan.setJudul(judul);
                lukisan.setPelukis(penulis);
                lukisan.setDeskripsi(deskripsi);
                lukisan.setUrl_gambar(imageUri.toString());

                String key = lukisReference.push().getKey();
                lukisan.setId(key);
                lukisReference.child(key).setValue(lukisan);
                Intent i = new Intent(getApplicationContext(), HalamanAdmin.class);
                startActivity(i);
                finish();
            }
        });

        btBacktoHalAdmin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HalamanAdmin.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
//            imageview.setImageURI(imageUri);
        }
    }
}