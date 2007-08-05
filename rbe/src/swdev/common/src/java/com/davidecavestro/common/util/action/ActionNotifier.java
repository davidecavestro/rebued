/*
 * ActionAdapter.java
 *
 * Created on 10 dicembre 2005, 14.27
 */

package com.davidecavestro.common.util.action;

import java.awt.event.ActionListener;

/**
 * Gestisce una lista di oggetti interessati (listener) alla notifica di un'azione.
 *
 * @author  davide
 */
public interface ActionNotifier {
/**
 * Adds the specified action listener to receive
 * action events from this textfield.
 *
 * @param l the action listener to be added
 */
	void addActionListener (ActionListener l);
	
	/**
	 * Removes the specified action listener so that it no longer
	 * receives action events from this textfield.
	 *
	 * @param l the action listener to be removed
	 */
	void removeActionListener (ActionListener l);
	
	/**
	 * Returns an array of all the <code>ActionListener</code>s added
	 * to this JTextField with addActionListener().
	 *
	 * @return all of the <code>ActionListener</code>s added or an empty
	 *         array if no listeners have been added
	 * @since 1.4
	 */
	ActionListener[] getActionListeners ();
}
