/*
 * FindNextAction.java
 *
 * Created on 31 dicembre 2005, 10.51
 */

package com.davidecavestro.rbe.actions;

import com.davidecavestro.common.util.action.ActionNotifier;
import com.davidecavestro.common.util.action.ActionNotifierImpl;
import com.davidecavestro.rbe.ApplicationContext;
import com.davidecavestro.rbe.gui.search.Matcher;
import com.davidecavestro.rbe.model.DefaultResourceBundleModel;
import com.davidecavestro.rbe.model.LocalizationProperties;
import com.davidecavestro.rbe.model.ResourceBundleModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.AbstractAction;

/**
 * Muove il focus sulla prossima occorrenza di un valore che soddisfa il pattern di ricerca.
 *
 * @author  davide
 */
public class FindNextAction extends AbstractAction implements PropertyChangeListener, ActionNotifier {
	
//	private final ApplicationContext _context;
//	private final Matcher _matcher;
	
	private final ActionNotifierImpl _actionNotifier;
	/**
	 * Costruttore.
	 * @param context il contesto applicativo.
	 */
	public FindNextAction (/*ApplicationContext context, Matcher matcher*/) {
//		this._context = context;
//		this._matcher = matcher;
		this._actionNotifier = new ActionNotifierImpl ();
		this.setEnabled (true);
		this.putValue (ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke (java.awt.event.KeyEvent.VK_F3, 0));
	}
	
	public void actionPerformed (java.awt.event.ActionEvent e) {
		this._actionNotifier.fireActionPerformed (new ActionEvent (this, -1, "findNext"));
	}
	
	public void propertyChange (java.beans.PropertyChangeEvent evt) {
		Object source = evt.getSource ();
		if (source instanceof Matcher ){
			Matcher m = (Matcher)evt.getSource ();
			if (evt.getPropertyName ().equals ("pattern")){
				this.setEnabled (m.getPattern ()!=null && m.getPattern ().length ()>0);
				return;
			}
		}
	}
	
	public void addActionListener (ActionListener l) {
		this._actionNotifier.addActionListener (l);
	}
	
	public ActionListener[] getActionListeners () {
		return this._actionNotifier.getActionListeners ();
	}
	
	public void removeActionListener (ActionListener l) {
		this._actionNotifier.removeActionListener (l);
	}
	
}
