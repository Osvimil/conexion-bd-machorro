package com.appspot.conexionendpoints.backend;

import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletContextEvent;

import javax.servlet.ServletContextListener;

/**

 * Detecta cuando la aplicación web se levanta o se dá de baja. En la api de
 *
 *
 * servlets 2.5 hay que registrar esta clase en web.xml

 */

public class ListenerBackend implements ServletContextListener {

    /** Se invoca cuando el servidor se levanta. */

    @Override public void contextInitialized(ServletContextEvent sce) {

        // Registra las entity en objectify.

        ObjectifyService.register(Conocido.class);

    }

    /** Se invoca cuando el servidor se dá de baja. */

    @Override public void contextDestroyed(ServletContextEvent sce) {}

}