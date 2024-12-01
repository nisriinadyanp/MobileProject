package com.example.projectmejatim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.Objects;

public class AdminDetail extends AppCompatActivity {

    TextView tvJudulAdmin;
    TextView tvPelukisAdmin;
    TextView tvDeskripsiAdmin;
    ImageView iLukisanAdmin;
    Button btEdit;
    Button btHapus;
    ImageView btBacktoHalamanAdmin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail2);

        tvJudulAdmin = findViewById(R.id.tvJudul2);
        tvPelukisAdmin = findViewById(R.id.tvPelukis2);
        tvDeskripsiAdmin = findViewById(R.id.tvDeskripsi2);
        iLukisanAdmin = findViewById(R.id.iTampilLukisan2);
        btEdit = findViewById(R.id.btEdit2);
        btHapus = findViewById(R.id.btHapus2);
        btBacktoHalamanAdmin = findViewById(R.id.btBacktoHalamanAdmin);

        Intent i = getIntent();
        String id = i.getStringExtra("id");

        DatabaseReference lukisRef = FirebaseDatabase.getInstance().getReference("lukisans").child(id);
        lukisRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lukisan lukis = snapshot.getValue(Lukisan.class);
                tvJudulAdmin.setText(Objects.requireNonNull(lukis).getJudul());
                tvPelukisAdmin.setText(lukis.getPelukis());
                tvDeskripsiAdmin.setText(lukis.getDeskripsi());
                Picasso.get().load(lukis.getUrl_gambar()).into(iLukisanAdmin);

                btEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(AdminDetail.this, AdminEdit.class);
                        i.putExtra("id", lukis.getId());
                        startActivity(i);
                    }
                });

                btHapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(AdminDetail.this, AdminHapus.class);
                        i.putExtra("id", lukis.getId());
                        startActivity(i);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btBacktoHalamanAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HalamanAdmin.class);
                startActivity(i);
                finish();
            }
        });

    }
}