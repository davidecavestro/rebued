/*
 * PasteAction.java
 *
 * Created on 10 gennaio 2006, 21.02
 */

package com.davidecavestro.rbe.actions;

import com.davidecavestro.common.gui.ComponentSupport;
import java.awt.Component;
import javax.swing.AbstractAction;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;

/**
 * Azione di PASTE.
 *
 * @author  davide
 */
public class PasteAction extends AbstractAction implements CaretListener {
	
	
	/**
	 * Costruttore.
	 */
	public PasteAction () {

	}
	
	public void caretUpdate (javax.swing.event.CaretEvent e) {
	}
	
	public void actionPerformed (java.awt.event.ActionEvent e) {
		Component focusOwner = ComponentSupport.getWindow ((Component)e.getSource ()).getFocusOwner ();
		
		if (focusOwner instanceof JTextComponent){
			((JTextComponent)focusOwner).cut ();
		}
		
	}
	
}
