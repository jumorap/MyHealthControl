package com.myhealth.ui.home.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdaptaMensaje extends RecyclerView.Adapter<HolderMessage> {

    private List<Mensaje> listMensaje = new ArrayList<>();
    private Context c;

    public AdaptaMensaje(Context c) {
        this.c = c;
    }

    public void addMessage(Mensaje m) {
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override
    public HolderMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(com.myhealth.R.layout.chat_view,parent,false);
        return new HolderMessage(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMessage holder, int position) {

/*
        String newName = listMensaje.get(2).getNombre();
        String actualName = listMensaje.get(position).getNombre();
        if (newName == actualName) {
            holder.getNombre().setVisibility(View.GONE);
        } else{
            holder.getNombre().setVisibility(View.VISIBLE);
        }

 */
        String newName = listMensaje.get(2).getNombre();

        holder.getHora().setText(listMensaje.get(position).getHora());
        holder.getMensaje().setText(listMensaje.get(position).getMensaje());
        holder.getNombre().setText(listMensaje.get(position).getNombre());
        holder.getFotoMensaje().setVisibility(View.VISIBLE);
        holder.getMensaje().setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}