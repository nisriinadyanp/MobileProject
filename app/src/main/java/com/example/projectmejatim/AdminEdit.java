package com.example.projectmejatim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.UUID;

public class AdminEdit extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText etJudulEdit;
    EditText etPelukisEdit;
    EditText etDeksripsiEdit;
    Button btSimpanEdit;
    TextView tvJudulEdit;
    ImageView btBacktoHalAdmin3;
    DatabaseReference databaseLukisan;
    ImageView iEditGambar;
    Uri imageUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit);

        etJudulEdit = findViewById(R.id.etJudulEdit);
        etPelukisEdit = findViewById(R.id.etPelukisEdit);
        etDeksripsiEdit = findViewById(R.id.etDeskripsiEdit);
        btSimpanEdit = findViewById(R.id.btSimpanEdit);
        tvJudulEdit = findViewById(R.id.tvJudulEdit);
        btBacktoHalAdmin3 = findViewById(R.id.imageView7);
        iEditGambar = findViewById(R.id.imageView9);


        databaseLukisan = FirebaseDatabase.getInstance().getReference("lukisans");

        Intent i = getIntent();
        String lukisanId = i.getStringExtra("id");




        databaseLukisan.child(lukisanId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lukisan lukis = snapshot.getValue(Lukisan.class);
//                Lukisan lukis = snapshot.child(lukisanId).getValue(Lukisan.class);

                etJudulEdit.setText(lukis.getJudul());
                tvJudulEdit.setText(lukis.getJudul());
                etPelukisEdit.setText(lukis.getPelukis());
                etDeksripsiEdit.setText(lukis.getDeskripsi());
                Picasso.get().load(lukis.getUrl_gambar()).into(iEditGambar);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btSimpanEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newJudul = etJudulEdit.getText().toString();
                String newNama = etPelukisEdit.getText().toString();
                String newDeskripsi = etDeksripsiEdit.getText().toString();


                Lukisan lukis = new Lukisan(lukisanId, newJudul, newNama, newDeskripsi);
                databaseLukisan.child(lukisanId).setValue(lukis);

                Intent i = new Intent(getApplicationContext(), AdminDetail.class);
                startActivity(i);
            }
        });

        btBacktoHalAdmin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HalamanAdmin.class);
                startActivity(i);
                finish();
            }
        });


    }
}