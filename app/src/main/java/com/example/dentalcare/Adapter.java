package com.example.dentalcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentalcare.modelo.RecyclerViewInterface;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<DiagUser> newsArrayList;

    public Adapter(Context context, ArrayList<DiagUser> newsArrayList,
                    RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.newsArrayList = newsArrayList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemdiagnostic, parent, false);
        return new ViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiagUser diagUser = newsArrayList.get(position);
        holder.Fecha.setText(diagUser.Fecha);
        holder.Hora.setText(diagUser.Hora);
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //ShapeableImageView ImageView;
        TextView Fecha, Hora;
        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            //ImageView = itemView.findViewById(R.id.imgViewPre);
            Fecha = itemView.findViewById(R.id.txtViewMostrarFecha);
            Hora = itemView.findViewById(R.id.txtViewMostrarHora);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
