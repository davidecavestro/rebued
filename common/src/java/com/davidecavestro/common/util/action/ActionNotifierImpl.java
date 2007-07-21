/*
 * ActionNotifierImpl.java
 *
 * Created on 10 dicembre 2005, 14.30
 */

package com.davidecavestro.common.util.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.EventListenerList;

/**
 * Implementezione di ActionNotifier.
 *
 * @author  davide
 */
public class ActionNotifierImpl implements ActionNotifier {
	
	/** A list of event listeners for this component. */
	protected EventListenerList listenerList = new EventListenerList ();
	
	/** Creates a new instance of ActionNotifierImpl */
	public ActionNotifierImpl () {
	}
	
	/**
	 * Adds the specified action listener to receive
	 * action events from this textfield.
	 *
	 * @param l the action listener to be added
	 */
	public synchronized void addActionListener (ActionListener l) {
		listenerList.add (ActionListener.class, l);
	}
	
	/**
	 * Removes the specified action listener so that it no longer
	 * receives action events from this textfield.
	 *
	 * @param l the action listener to be removed
	 */
	public synchronized void removeActionListener (ActionListener l) {
		listenerList.remove (ActionListener.class, l);
	}
	
	/**
	 * Returns an array of all the <code>ActionListener</code>s added
	 * to this JTextField with addActionListener().
	 *
	 * @return all of the <code>ActionListener</code>s added or an empty
	 *         array if no listeners have been added
	 * @since 1.4
	 */
	public synchronized ActionListener[] getActionListeners () {
		return (ActionListener[])listenerList.getListeners (
		ActionListener.class);
	}

	/**
	 * Notifica i listener dell'evento.
	 * @param el'eventoa
	 */
	public void fireActionPerformed (ActionEvent e) {
		// Guaranteed to return a non-null array
		final Object[] listeners = listenerList.getListenerList ();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i]==ActionListener.class) {
				// Lazily create the event:
				if (e == null) {
					e = new ActionEvent (this, -1, "");
				}
				((ActionListener)listeners[i+1]).actionPerformed (e);
			}
		}
	}
	
	
}
