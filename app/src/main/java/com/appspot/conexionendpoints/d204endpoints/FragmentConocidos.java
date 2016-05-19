package com.appspot.conexionendpoints.d204endpoints;

import android.content.Intent;

import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.support.v4.app.LoaderManager;

import android.support.v4.content.AsyncTaskLoader;

import android.support.v4.content.Loader;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.Menu;

import android.view.MenuInflater;

import android.view.MenuItem;

import android.view.View;

import android.view.ViewGroup;

import com.appspot.conexionendpoints.backend.proxyEndpointConocidos.ProxyEndpointConocidos;

import com.appspot.conexionendpoints.backend.proxyEndpointConocidos.model.ConocidoCollection;

import java.io.IOException;

import static com.appspot.conexionendpoints.d204endpoints.App.getApp;

import static com.appspot.conexionendpoints.d204endpoints.Constantes.ACTIVITY_AGREGAR;

import static com.appspot.conexionendpoints.d204endpoints.Constantes.LISTANDO;

import static com.appspot.conexionendpoints.d204endpoints.Utileria.busca;

import static com.appspot.conexionendpoints.d204endpoints.Utileria.muestraTexto;

import static com.appspot.conexionendpoints.d204endpoints.Utileria.procesaError;

import static com.appspot.conexionendpoints.d204endpoints.Utileria.setIndicadorActivo;

import static com.appspot.conexionendpoints.d204endpoints.Utileria.texto;

public class FragmentConocidos extends Fragment

        implements LoaderManager.LoaderCallbacks<Respuesta<ConocidoCollection>> {

    private static final String TAG = FragmentConocidos.class.getName();

    private String mensaje;

    @Nullable private AdapterConocidos adapter;

    @Nullable private Indicador indicador;

    @Override public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater,

                                                 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.view_conocidos, container, false);

    }

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        indicador = (Indicador) busca(view, R.id.indicador);

        adapter = new AdapterConocidos();

        final RecyclerView listado = (RecyclerView) busca(view, R.id.listado);

        listado.setLayoutManager(new LinearLayoutManager(getActivity()));

        listado.setAdapter(adapter);

    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);

        setHasOptionsMenu(true);

        muestraMensajeError(mensaje);

        getLoaderManager().initLoader(0, null, this);

    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_maestro, menu);

    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_agregar:

                clicEnAgregar();

                return true;

            case R.id.action_actualizar:

                clicEnActualizar();

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }

    @Override public Loader<Respuesta<ConocidoCollection>> onCreateLoader(int id,

                                                                          Bundle args) {

        setIndicadorActivo(indicador, true);

        final App app = getApp(getActivity());

        return new AsyncTaskLoader<Respuesta<ConocidoCollection>>(app) {

            @Override protected void onStartLoading() {

                forceLoad();

            }

            @Override public Respuesta<ConocidoCollection> loadInBackground() {

                try {

                    final ProxyEndpointConocidos.List list =

                            app.getProxyEndpointConocidos().list();

                    final ConocidoCollection lista = list.execute();

                    return new Respuesta<>(lista, null);

                } catch (IOException e) {

                    return new Respuesta<>(null, e);

                }

            }

        };

    }

    @Override

    public void onLoadFinished(Loader<Respuesta<ConocidoCollection>> loader,

                               Respuesta<ConocidoCollection> data) {

        setIndicadorActivo(indicador, false);
        final Exception exception = data.getException();

        if (exception != null) {

            muestraError(exception);

        } else if (adapter != null) {

            adapter.setLista(data.getResultado().getItems());

        }

    }

    @Override

    public void onLoaderReset(Loader<Respuesta<ConocidoCollection>> loader) {

    }

    private void clicEnActualizar() {

        getLoaderManager().restartLoader(0, null, this);

    }

    private void clicEnAgregar() {

        final Intent intent = new Intent(getActivity(), ACTIVITY_AGREGAR);

        startActivity(intent);

    }

    private void muestraError(Exception exception) {

        final String mensaje = procesaError(TAG, LISTANDO, exception);

        muestraMensajeError(mensaje);

    }

    private void muestraMensajeError(String mensaje) {

        this.mensaje = mensaje;

        if (isAdded()) {

            muestraTexto(getView(), R.id.error, texto(mensaje));

        }

    }

}