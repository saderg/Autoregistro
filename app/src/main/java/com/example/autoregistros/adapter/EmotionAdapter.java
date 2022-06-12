package com.example.autoregistros.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.autoregistros.R;
import com.example.autoregistros.entidades.Emotion;
import com.example.autoregistros.entidades.User;

import java.util.ArrayList;
import java.util.List;

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.ViewHolder>{

    public ArrayList<Emotion> emotionList;
    public Context context;

    public EmotionAdapter(ArrayList<Emotion> emotionList, Context context){
        this.emotionList = emotionList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitulo.setText(emotionList.get(position).getEmotion_type());
        holder.txtDescripcion.setText(emotionList.get(position).getEmotion_type());

    }

    @Override
    public int getItemCount() {
        return emotionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtTitulo;
        public TextView txtDescripcion;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.tituloEmotion);
            txtDescripcion = itemView.findViewById(R.id.descriptionEmotion);
        }

    }
}
