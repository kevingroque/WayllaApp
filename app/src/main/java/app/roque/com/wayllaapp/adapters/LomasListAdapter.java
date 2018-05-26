package app.roque.com.wayllaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.roque.com.wayllaapp.R;
import app.roque.com.wayllaapp.activities.DetalleLomaActivity;
import app.roque.com.wayllaapp.models.Lomas;


public class LomasListAdapter extends RecyclerView.Adapter<LomasListAdapter.LomasListHolder>{

    private Context context;
    private List<Lomas> lomasList;
    private String lomas_key;

    public LomasListAdapter(Context context, List<Lomas> lomasList) {
        this.context = context;
        this.lomasList = lomasList;
    }

    @NonNull
    @Override
    public LomasListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_lomas_item, parent,false);
        LomasListHolder lomasListHolder = new LomasListHolder(v);
        return lomasListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LomasListHolder holder, int position) {
        final Lomas lomas = this.lomasList.get(position);

        holder.nombre.setText(lomas.getNombre());
        Glide.with(holder.itemView.getContext()).load(lomas.getImagen()).into(holder.imagen);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetalleLomaActivity.class);
                intent.putExtra("ID", lomas_key);
                Log.d("LomasId", "Lomas ID: " +lomas_key);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lomasList.size();
    }

    public class LomasListHolder extends RecyclerView.ViewHolder{

        public TextView nombre;
        public TextView descripcion;
        public ImageView imagen;

        public LomasListHolder(View itemView) {
            super(itemView);
            nombre = (TextView)itemView.findViewById(R.id.txt_nombre_lomas_list);
            imagen = (ImageView)itemView.findViewById(R.id.img_lomas_list);

        }
    }
}


