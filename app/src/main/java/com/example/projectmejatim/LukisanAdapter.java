package com.example.projectmejatim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LukisanAdapter extends RecyclerView.Adapter<LukisanAdapter.LukisanViewHolder> {
    Context context;
    ArrayList<Lukisan> list;

    public LukisanAdapter(Context context, ArrayList<Lukisan> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LukisanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_lukisan,parent, false);
        return new LukisanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LukisanViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Lukisan lukis = list.get(position);
        holder.judul.setText(lukis.getJudul());
        holder.pelukis.setText(lukis.getPelukis());
        Picasso.get().load(lukis.getUrl_gambar()).into(holder.lukisan);
        //holder.lukisan. ini buat nampilin gambarnya yah

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lukisan lukis = list.get(position);

                SharedPreferences sharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE);
                String role = sharedPreferences.getString("role", "user");

                Intent i;

                if (role.equals("admin")){
                    i = new Intent(context, AdminDetail.class);
                } else {
                    i = new Intent(context, PublicDetail.class);
                }

                i.putExtra("judul", lukis.getJudul());
                i.putExtra("id", lukis.getId());
                i.putExtra("pelukis", lukis.getPelukis());
                i.putExtra("deksripsi", lukis.getDeskripsi());
                i.putExtra("url_gambar", lukis.getUrl_gambar());

                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class LukisanViewHolder extends RecyclerView.ViewHolder{
        TextView judul, pelukis;
        ImageView lukisan;

        public LukisanViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tvJudulLukisan);
            pelukis = itemView.findViewById(R.id.tvNamaPelukis);
            lukisan = itemView.findViewById(R.id.iLukisan);
        }
    }
}
