/*
 * DialogNotifierImpl.java
 *
 * Created on 12 dicembre 2005, 23.26
 */

package com.davidecavestro.common.gui.dialog;

import javax.swing.event.EventListenerList;

/**
 * Implementezione di DialogNotifier.
 *
 * @author  davide
 */
public class DialogNotifierImpl implements DialogNotifier {
	
	/** A list of event listeners for this component. */
	protected EventListenerList listenerList = new EventListenerList ();
	
	/** Creates a new instance of DialogNotifierImpl */
	public DialogNotifierImpl () {
	}
	
	/**
	 * Adds the specified action listener to receive
	 * action events from this textfield.
	 *
	 * @param l the action listener to be added
	 */
	public synchronized void addDialogListener (DialogListener l) {
		listenerList.add (DialogListener.class, l);
	}
	
	/**
	 * Removes the specified action listener so that it no longer
	 * receives action events from this textfield.
	 *
	 * @param l the action listener to be removed
	 */
	public synchronized void removeDialogListener (DialogListener l) {
		listenerList.remove (DialogListener.class, l);
	}
	
	/**
	 * Returns an array of all the <code>DialogListener</code>s added
	 * to this JTextField with addDialogListener().
	 *
	 * @return all of the <code>DialogListener</code>s added or an empty
	 *         array if no listeners have been added
	 * @since 1.4
	 */
	public synchronized DialogListener[] getDialogListeners () {
		return (DialogListener[])listenerList.getListeners (
		DialogListener.class);
	}

	/**
	 * Notifica i listener dell'evento.
	 * @param el'eventoa
	 */
	public void fireDialogPerformed (DialogEvent e) {
		// Guaranteed to return a non-null array
		final Object[] listeners = listenerList.getListenerList ();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i]==DialogListener.class) {
//				// Lazily create the event:
//				if (e == null) {
//					e = new DialogEvent (this, -1);
//				}
				((DialogListener)listeners[i+1]).dialogChanged (e);
			}
		}
	}
	
	
}
