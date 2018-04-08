/*
 * DialogNotifier.java
 *
 * Created on 11 dicembre 2005, 12.10
 */

package com.davidecavestro.common.gui.dialog;


/**
 * Definisce l'accesso al registro dei listener per il <CODE>DialogEvent</CODE>.
 *
 * @author  davide
 */
public interface DialogNotifier {
    /**
     * Registra un listener.
     *
     * @param	l		il Listener.
     */
    public void addDialogListener (DialogListener l);

    /**
     * Rimuove il listener.
     *
     * @param	l		il Listener.
     */
    public void removeDialogListener(DialogListener l);
}
