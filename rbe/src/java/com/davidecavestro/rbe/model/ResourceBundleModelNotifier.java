/*
 * ResourceBundleModelNotifier.java
 *
 * Created on 2 dicembre 2005, 0.35
 */

package com.davidecavestro.rbe.model;

import com.davidecavestro.rbe.model.event.ResourceBundleModelListener;

/**
 * Definisce l'accesso al registro dei listener per il <CODE>ResourceBundleModel</CODE>.
 *
 * @author  davide
 */
public interface ResourceBundleModelNotifier {
    /**
     * Registra un listener per essere notificato alla modifica del modello dati.
     *
     * @param	l		il ResourceBundleModelListener.
     */
    public void addResourceBundleModelListener (ResourceBundleModelListener l);

    /**
     * Rimuove il listener dal registro degli interessati alla notifica della modifica del modello dati.
     *
     * @param	l		il ResourceBundleModelListener.
     */
    public void removeResourceBundleModelListener(ResourceBundleModelListener l);
}
