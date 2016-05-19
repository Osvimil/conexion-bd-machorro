package com.appspot.conexionendpoints.d204endpoints;

import android.os.AsyncTask;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;

import android.view.Menu;

import android.view.MenuInflater;

import android.view.MenuItem;

import android.view.View;

import android.view.ViewGroup;

import com.appspot.conexionendpoints.backend.proxyEndpointConocidos.ProxyEndpointConocidos;

import com.appspot.conexionendpoints.backend.proxyEndpointConocidos.model.Conocido;

import java.io.IOException;

import static com.appspot.conexionendpoints.d204endpoints.App.getApp;

import static com.appspot.conexionendpoints.d204endpoints.Constantes.AGREGANDO;

import static com.appspot.conexionendpoints.d204endpoints.Utileria.busca;

import static com.appspot.conexionendpoints.d204endpoints.Utileria.muestraTexto;

import static com.appspot.conexionendpoints.d204endpoints.Utileria.procesaError;

import static com.appspot.conexionendpoints.d204endpoints.Utileria.recuperaTexto;

import static com.appspot.conexionendpoints.d204endpoints.Utileria.setIndicadorActivo;

import static com.appspot.conexionendpoints.d204endpoints.Utileria.texto;

public class FragmentNuevoConocido extends Fragment {

    private static final String TAG = FragmentNuevoConocido.class.getName();

    private String mensaje;

    private boolean terminado;

    @Nullable private Indicador indicador;

    @Nullable @Override public View onCreateView(LayoutInflater inflater,

                                                 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.view_conocido, container, false);

    }

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        indicador = (Indicador) busca(view, R.id.indicador);

    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);

        setHasOptionsMenu(true);

        if (terminado) {

            termina();

        } else {

            muestraMensajeError(mensaje);

        }

    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_agregar, menu);

    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_guardar:

                clicEnGuardar();

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }

    private Conocido creaViewModel() {

        muestraMensajeError("");
        final Conocido viewModel = new Conocido();

        viewModel.setNombre(recuperaTexto(getView(), R.id.nombre));

        viewModel.setTelefono(recuperaTexto(getView(), R.id.telefono));

        return viewModel;

    }

    private void clicEnGuardar() {

        final Conocido viewModel = creaViewModel();

        setIndicadorActivo(indicador, true);

        final App app = getApp(getActivity());

        final AsyncTask<Conocido, Void, Exception> task =

                new AsyncTask<Conocido, Void, Exception>() {

                    @Override protected Exception doInBackground(Conocido... params) {

                        try {

                            final ProxyEndpointConocidos.Insert insert =

                                    app.getProxyEndpointConocidos().insert(params[0]);

                            insert.execute();

                            return null;

                        } catch (IOException e) {

                            return e;

                        }

                    }

                    @Override protected void onPostExecute(Exception e) {

                        operaciónRealizada(e);

                    }

                };

        task.execute(viewModel);

    }

    private void operaciónRealizada(Exception e) {

        if (e == null) {

            termina();

        } else {

            muestraError(e);

        }

    }

    private void termina() {

        this.terminado = true;

        if (isAdded()) {

            getActivity().finish();

        }

    }

    private void muestraError(Exception exception) {

        final String mensaje = procesaError(TAG, AGREGANDO, exception);

        muestraMensajeError(mensaje);

    }

    private void muestraMensajeError(String mensaje) {

        this.mensaje = mensaje;

        if (isAdded()) {

            muestraTexto(getView(), R.id.error, texto(mensaje));

        }

    }

}