/*
 * NewBundleAction.java
 *
 * Created on 6 dicembre 2005, 23.14
 */

package com.davidecavestro.rbe.actions;

import com.davidecavestro.common.util.*;
import com.davidecavestro.rbe.*;
import com.davidecavestro.rbe.gui.MainWindow;
import com.davidecavestro.rbe.gui.WindowManager;
import com.davidecavestro.rbe.model.DefaultResourceBundleModel;
import com.davidecavestro.rbe.model.LocalizationProperties;
import com.davidecavestro.rbe.model.ResourceBundleModel;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 * Crea e imposta nell'appliczione un nuovo bundle di risorse localizzate.
 *
 * @author  davide
 */
public class NewBundleAction extends AbstractAction {
	
	private final ApplicationContext _context;
	
	/**
	 * Costruttore.
	 * @param context il contesto applicativo.
	 */
	public NewBundleAction (ApplicationContext context) {
		this._context = context;
		this.putValue (ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke (java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
	}
	
	public void actionPerformed (java.awt.event.ActionEvent e) {
		final DefaultResourceBundleModel rbm = _context.getModel ();
		if (rbm.isModified ()){
			if (
			JOptionPane.showConfirmDialog (
			this._context.getWindowManager ().getMainWindow (), 
			java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("Discard_all_changes?"))!=JOptionPane.OK_OPTION){
				return;
			}
		}
		
		rbm.setBundles (new LocalizationProperties [] {new LocalizationProperties (LocalizationProperties.DEFAULT, new CommentedProperties (_context.getPropertiesExceptionHandler ()))}); 
		final String baseName = (String)JOptionPane.showInputDialog (this._context.getWindowManager ().getMainWindow (), 
			java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("Insert_bundle_base_name"),
			java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("Insert_bundle_base_name"),
			JOptionPane.PLAIN_MESSAGE,
			null, null, "blank");
		rbm.setName (baseName!=null && baseName.length ()>0?baseName:"blank");
		rbm.setPath (null);
		this._context.getUndoManager ().discardAllEdits ();
		
	}
	
	
}
