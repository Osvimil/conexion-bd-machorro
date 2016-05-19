package com.appspot.conexionendpoints.d204endpoints;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.appspot.conexionendpoints.backend.proxyEndpointConocidos.model.Conocido;

import java.util.ArrayList;
import java.util.List;

/** Despliega un listado de conocidos. */

public class AdapterConocidos extends RecyclerView.Adapter<ViewHolderConocido> {

    @Nullable private List<Conocido> lista = new ArrayList<>();

    public void setLista(@Nullable List<Conocido> lista) {

        this.lista = lista;

        notifyDataSetChanged();

    }

    @Override

    public ViewHolderConocido onCreateViewHolder(ViewGroup viewGroup, int tipo) {

        return ViewHolderConocido.creaViewHolder(viewGroup);

    }

    @Override

    public void onBindViewHolder(ViewHolderConocido viewHolderConocido, int i) {

        viewHolderConocido.setModel(lista == null ? null : lista.get(i));

    }

    @Override public int getItemCount() {

        return lista == null ? 0 : lista.size();

    }

}