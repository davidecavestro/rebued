/*
 * FindAction.java
 *
 * Created on 31 dicembre 2005, 10.12
 */

package com.davidecavestro.rbe.actions;

import com.davidecavestro.rbe.ApplicationContext;
import com.davidecavestro.rbe.model.DefaultResourceBundleModel;
import com.davidecavestro.rbe.model.LocalizationProperties;
import com.davidecavestro.rbe.model.ResourceBundleModel;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.AbstractAction;

/**
 * Cerca le occorrenze di un pattern di ricerca.
 *
 * @author  davide
 */
public class FindAction extends AbstractAction implements PropertyChangeListener {
	
	private final ApplicationContext _context;
	
	/**
	 * Costruttore.
	 * @param context il contesto applicativo.
	 */
	public FindAction (ApplicationContext context) {
		this._context = context;
		this.setEnabled (true);
		this.putValue (ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke (java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));

	}
	
	public void actionPerformed (java.awt.event.ActionEvent e) {
		this._context.getWindowManager ().getFindDialog ().show ();
	}
	
	public void propertyChange (java.beans.PropertyChangeEvent evt) {
	}
}
