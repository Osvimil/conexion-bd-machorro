package com.appspot.conexionendpoints.d204endpoints;

import android.app.Application;

import android.content.Context;

import android.support.annotation.NonNull;

import android.support.annotation.Nullable;

import com.appspot.conexionendpoints.backend.proxyEndpointConocidos.ProxyEndpointConocidos;

import com.google.api.client.extensions.android.http.AndroidHttp;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;

import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import com.google.api.client.http.HttpRequest;

import com.google.api.client.http.HttpRequestInitializer;

import com.google.api.client.http.HttpTransport;

import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;

import static com.appspot.conexionendpoints.d204endpoints.Constantes.SERVIDOR_LOCAL;

import static com.appspot.conexionendpoints.d204endpoints.Constantes.URL_LOCAL;

import static com.appspot.conexionendpoints.d204endpoints.Constantes.URL_REMOTA;

public class App extends Application {

    @NonNull public static App getApp(@NonNull Context context) {

        return (App) context.getApplicationContext();

    }

    @Nullable private ProxyEndpointConocidos proxyEndpointConocidos;

    @NonNull public ProxyEndpointConocidos getProxyEndpointConocidos() {

        if (proxyEndpointConocidos == null) {

            proxyEndpointConocidos = creaProxyEndpointConocidos();

        }

        return proxyEndpointConocidos;

    }

    @NonNull private ProxyEndpointConocidos creaProxyEndpointConocidos() {

        final JacksonFactory jacksonFactory = new JacksonFactory();

        final HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();

        final HttpRequestInitializer httpRequestInitializer =

                new HttpRequestInitializer() {

                    @Override public void initialize(HttpRequest solicitudHttp)

                            throws IOException {

                    }

                };

        final ProxyEndpointConocidos.Builder builder =

                new ProxyEndpointConocidos.Builder(httpTransport, jacksonFactory,

                        httpRequestInitializer);

        builder.setApplicationName(getString(R.string.app_name));

        @SuppressWarnings("ConstantConditions")

        final String url = SERVIDOR_LOCAL ? URL_LOCAL : URL_REMOTA;
        builder.setRootUrl(url + "/_ah/api/");

        // Solo se usa GZip al conectarse al servidor remoto.

        builder.setGoogleClientRequestInitializer(

                new GoogleClientRequestInitializer() {

                    @Override

                    public void initialize(AbstractGoogleClientRequest<?> request)

                            throws IOException {

                        if (SERVIDOR_LOCAL) {

                            request.setDisableGZipContent(true);

                        }

                    }

                });

        return builder.build();

    }

}
