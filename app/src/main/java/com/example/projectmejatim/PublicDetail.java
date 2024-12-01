package com.example.projectmejatim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PublicDetail extends AppCompatActivity {

    TextView tvJudul;
    TextView tvPenulis;
    TextView tvDeskripsi;
    ImageView iTampilLukisan;
    ImageView btBacktoHalamanPublic;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_detail);
        tvJudul = findViewById(R.id.tvJudul);
        tvPenulis = findViewById(R.id.tvPelukis);
        tvDeskripsi = findViewById(R.id.tvDeskripsi);
        iTampilLukisan = findViewById(R.id.iTampilLukisan);
        btBacktoHalamanPublic = findViewById(R.id.btBacktoHalamanPublic);

        Intent i = getIntent();
        String id = i.getStringExtra("id");
        String judul = i.getStringExtra("judul");
        String pelukis = i.getStringExtra("pelukis");
        String deskripsi = i.getStringExtra("deskripsi");

        DatabaseReference lukisRef = FirebaseDatabase.getInstance().getReference("lukisans").child(id);
        lukisRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lukisan lukis = snapshot.getValue(Lukisan.class);
                tvJudul.setText(lukis.getJudul());
                tvPenulis.setText(lukis.getPelukis());
                tvDeskripsi.setText(lukis.getDeskripsi());
                Picasso.get().load(lukis.getUrl_gambar()).into(iTampilLukisan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btBacktoHalamanPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();            }
        });
    }
}