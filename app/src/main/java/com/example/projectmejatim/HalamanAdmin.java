package com.example.projectmejatim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HalamanAdmin extends AppCompatActivity {
    RecyclerView rvLukisan;
    DatabaseReference databaseLukisan;
    ImageView btProfile;
    ArrayList<Lukisan> lukisan;
    LukisanAdapter adapter;
    ImageView btTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_admin);

        rvLukisan = findViewById(R.id.rvLukisan);
        databaseLukisan = FirebaseDatabase.getInstance().getReference("lukisans");
        btProfile = findViewById(R.id.btProfilAdmin);
        lukisan = new ArrayList<>();
        rvLukisan.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new LukisanAdapter(this, lukisan);
        rvLukisan.setAdapter(adapter);
        btTambah = findViewById(R.id.btTambah);

        databaseLukisan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Lukisan lukis = dataSnapshot.getValue(Lukisan.class);
                    lukisan.add(lukis);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminProfil.class);
                startActivity(i);
                finish();
            }
        });

        btTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TambahLukisan.class);
                startActivity(i);
                finish();
            }
        });

    }
}