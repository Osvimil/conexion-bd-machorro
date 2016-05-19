package com.appspot.conexionendpoints.d204endpoints;

import android.support.annotation.IdRes;

import android.support.annotation.NonNull;

import android.support.annotation.Nullable;

import android.util.Log;

import android.view.View;

import android.widget.EditText;

import android.widget.TextView;

import com.google.api.client.googleapis.json.GoogleJsonError;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;

import static com.appspot.conexionendpoints.d204endpoints.Constantes.VIEW_NO_ENCONTRADA;

import static com.appspot.conexionendpoints.d204endpoints.Constantes.VIEW_NULA;

/** funciones útiles dentro de la aplicación. */

public final class Utileria {

    @NonNull public static View busca(@NonNull View view, @IdRes int idView) {

        final View v = view.findViewById(idView);
        if (v == null) {

            throw new NullPointerException(VIEW_NO_ENCONTRADA);

        } else {

            return v;

        }

    }

    @NonNull public static String texto(String s) {

        return s == null ? "" : s;

    }

    @NonNull public static String recuperaTexto(@Nullable View view,

                                                @IdRes int idEditText) {

        if (view == null) {

            throw new NullPointerException(VIEW_NULA);

        } else {

            final EditText editText = (EditText) busca(view, idEditText);

            return editText.getText().toString();

        }

    }

    public static void muestraTexto(@Nullable View view, @IdRes int idTextView,

                                    @NonNull String s) {

        if (view == null) {

            throw new NullPointerException(VIEW_NULA);

        } else {

            final TextView lblSalida = (TextView) busca(view, idTextView);

            lblSalida.setText(texto(s));

        }

    }

    public static void setIndicadorActivo(@Nullable Indicador indicador,

                                          boolean activo) {

        if (indicador != null) {

            indicador.setActivo(activo);

        }

    }

    @NonNull public static String procesaError(@NonNull String tag,

                                               @NonNull String contexto, @NonNull Exception exception) {

        try {

            throw exception;

        } catch (GoogleJsonResponseException e) {

            return procesaError(tag, contexto, e);

        } catch (Exception e) {

            Log.e(tag, contexto, e);

            return getMessage(e);

        }

    }

    @NonNull

    public static String procesaError(@NonNull String tag,

                                      @NonNull String contexto, @NonNull GoogleJsonResponseException e) {

        final String mensaje = getMessage(e);

        Log.e(tag, contexto, e);

        return mensaje;

    }

    /**

     * Obtiene el mensaje de error para una excepción del tipo

     * GoogleJsonResponseException.

     * @param e excepción que se procesa.

     * @return mensaje asociado con la excepción.

     */

    @NonNull public static String getMessage(

            @NonNull GoogleJsonResponseException e) {
        final GoogleJsonError details = e.getDetails();

        return details == null || details.isEmpty() ? getMessage((Exception) e) :

                details.getMessage();

    }

    /**

     * Obtiene el mensaje de error para una excepción.

     * @param e excepción que se procesa.

     * @return mensaje asociado con la excepción.

     */

    @NonNull public static String getMessage(@NonNull Exception e) {

        final String localizedMessage = e.getLocalizedMessage();

        return localizedMessage == null || localizedMessage.isEmpty() ?

                e.toString() : localizedMessage;

    }

    @Nullable public static String catchMensaje(@NonNull String nombreException,

                                                String texto) {

        final String busqueda = nombreException + ": ";

        if (texto.contains(busqueda)) {

            return texto.substring(busqueda.length());

        } else {

            return null;

        }

    }

    private Utileria() {}

}