/*
 * DialogListener.java
 *
 * Created on 11 dicembre 2005, 12.09
 */

package com.davidecavestro.common.gui.dialog;

/**
 * Un listener per eventi di tipo DialogEvent.
 *
 * @author  davide
 */
public interface DialogListener extends java.util.EventListener {
    /**
     * Questa notifica informa i listener di modifiche ad una dialog.
     */
    public void dialogChanged (DialogEvent e);
}
