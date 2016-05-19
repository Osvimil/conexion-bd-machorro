package com.appspot.conexionendpoints.d204endpoints;

public class Respuesta<T> {

    private final T resultado;

    private final Exception exception;

    public Respuesta(T resultado, Exception exception) {

        this.resultado = resultado;

        this.exception = exception;

    }
    public Exception getException() {

        return exception;

    }

    public T getResultado() {

        return resultado;

    }

}