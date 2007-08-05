/*
 * WIndowManager.java
 *
 * Created on 28 novembre 2005, 22.10
 */

package com.davidecavestro.rbe.gui;

import com.davidecavestro.common.application.ApplicationData;
import com.davidecavestro.common.gui.dialog.DialogListener;
import com.davidecavestro.common.gui.persistence.UIPersister;
import com.davidecavestro.common.util.*;
import com.davidecavestro.rbe.ApplicationContext;
import com.davidecavestro.rbe.model.DefaultResourceBundleModel;
import com.davidecavestro.rbe.model.LocalizationProperties;
import com.davidecavestro.rbe.model.ResourceBundleModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 * Il gestore delle finestre.
 *
 * @author  davide
 */
public class WindowManager implements ActionListener, DialogListener {

	private ApplicationContext _context;
	
	
	/** Costruttore. */
	public WindowManager () {
	}
	
	/**
	 * Inizializza le risorse necessarie.
	 *
	 */
	public void init (final ApplicationContext context){
		this._context= context;
	}

	private Splash _splash;	
	/**
	 * Ritorna la finestra principale.
	 * @return la finestra principale.
	 * @param appData i dati dell'applicazione.
	 * Sono necessari dato che tipicamente lo Splash viene usato prima
	 * diinizializzare il contesto applicativo.
	 */
	public Splash getSplashWindow (ApplicationData appData){
		if (this._splash==null){
			this._splash = new Splash (appData);
		}
		return this._splash;
	}
	
	private MainWindow _mainWindow;	
	/**
	 * Ritorna la finestra principale.
	 * @return la finestra principale.
	 */
	public MainWindow getMainWindow (){
		if (this._mainWindow==null){
			this._mainWindow = new MainWindow (this._context);
			this._context.getUIPersisteer ().register (this._mainWindow);
			this._mainWindow.addActionListener (this);
		}
		return this._mainWindow;
	}
	
	private AddEntryDialog _addEntryDialog;	
	/**
	 * Ritorna la dialog di inserimento nuova entry.
	 * @return la dialog di inserimento nuova entry.
	 */
	private AddEntryDialog getAddEntryDialog (){
		if (this._addEntryDialog==null){
			this._addEntryDialog = new AddEntryDialog (getMainWindow (), true);
			this._context.getUIPersisteer ().register (this._addEntryDialog);
			this._addEntryDialog.addDialogListener (this);
		}
		return this._addEntryDialog;
	}
	
	public void showEntryDialog (Locale l) {
		getAddEntryDialog ().showForLocale (l);		
	}
	
	private SpecifyBundleNameDialog _specifyBundleNameDialog;
	/**
	 * Ritorna la dialog di inserimento nuova entry.
	 * @return la dialog di inserimento nuova entry.
	 */
	private SpecifyBundleNameDialog getSpecifyBundleNameDialog (){
		if (this._specifyBundleNameDialog==null){
			this._specifyBundleNameDialog = new SpecifyBundleNameDialog (getMainWindow (), true);
			this._specifyBundleNameDialog.addDialogListener (this);
		}
		return this._specifyBundleNameDialog;
	}
	
	public String specifyBundleName (File f) {
		return getSpecifyBundleNameDialog ().showForFile (f);
	}
	
	private AddLocaleDialog _addLocaleDialog;	
	/**
	 * Ritorna la dialog di inserimento nuova entry.
	 * @return la dialog di inserimento nuova entry.
	 */
	private AddLocaleDialog getAddLocaleDialog (){
		if (this._addLocaleDialog==null){
			this._addLocaleDialog = new AddLocaleDialog (getMainWindow (), true);
			this._context.getUIPersisteer ().register (this._addLocaleDialog);
			this._addLocaleDialog.addDialogListener (this);
		}
		return this._addLocaleDialog;
	}
	
	private FindDialog _findDialog;	
	/**
	 * Ritorna la dialog di inserimento nuova entry.
	 * @return la dialog di inserimento nuova entry.
	 */
	public FindDialog getFindDialog (){
		if (this._findDialog==null){
			this._findDialog = new FindDialog (getMainWindow (), true, _context.getActionManager ().getFindNextAction ());
		}
		return this._findDialog;
	}
	
	private LogConsole _logConsole;	
	/**
	 * Ritorna la console dilog.
	 * @return la console dilog.
	 */
	public LogConsole getLogConsole (){
		if (this._logConsole==null){
			this._logConsole = new LogConsole (_context);
			this._context.getUIPersisteer ().register (this._logConsole);
		}
		return this._logConsole;
	}
	
	private OptionsDialog _optionsDialog;	
	/**
	 * Ritorna la dialog di impostazione delle opzioni.
	 * 
	 * @return la dialog di impostazione delle opzioni.
	 */
	public OptionsDialog getOptionsDialog (){
		if (this._optionsDialog==null){
			this._optionsDialog = new OptionsDialog (getMainWindow (), true, _context);
		}
		return this._optionsDialog;
	}
	
	public void dialogChanged (com.davidecavestro.common.gui.dialog.DialogEvent e) {
		if (e.getSource ()==this._addLocaleDialog){
			if (e.getType ()==JOptionPane.OK_OPTION){
				final Locale l = this._addLocaleDialog.getSelectedLocale ();
				if (this._context.getModel ().containsLocale (l)){
					JOptionPane.showMessageDialog (this._mainWindow, 
					java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("Duplicate_locale"));
				} else {
					this._context.getModel ().addLocale (new LocalizationProperties (l, new CommentedProperties (_context.getPropertiesExceptionHandler ())));
				}
			}
		} else if (e.getSource ()==this._addEntryDialog){
			if (e.getType ()==JOptionPane.OK_OPTION){
				Locale l = this._addEntryDialog.getLocale ();
				String key = this._addEntryDialog.getKeyText ();
				if (this._context.getModel ().getLocaleKeys (l).contains (key)){
					if (JOptionPane.showConfirmDialog (this._mainWindow, 
					StringUtils.toStringArray (
						java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("Existing_key._Overwrite?")
					),
					java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("Confirm"),
					JOptionPane.OK_CANCEL_OPTION
					) == JOptionPane.OK_OPTION){
						this._context.getModel ().setValue (
							l, 
							key, 
							this._addEntryDialog.getValueText ()
							);
						this._context.getModel ().setComment (
							l, 
							key, 
							this._addEntryDialog.getCommentText ()
							);
					}
				} else {
					this._context.getModel ().addKey (
						l, 
						key, 
						this._addEntryDialog.getValueText (),
						this._addEntryDialog.getCommentText ()
						);
				}
			}
		}
		
	}
	
	public void actionPerformed (java.awt.event.ActionEvent e) {
		if (e!=null && e.getActionCommand ()!=null){
			if (e.getActionCommand ().equals ("showAddEntryDialog")){
				Object source = e.getSource ();
				if (source instanceof MainWindow.NewEntryDialogRequester){
					showEntryDialog (((MainWindow.NewEntryDialogRequester)source).getLocale ());
				} else {
					showEntryDialog (LocalizationProperties.DEFAULT);
				}
			} else if (e.getActionCommand ().equals ("showAddLocaleDialog")){
				getAddLocaleDialog ().show ();
			} else if (e.getActionCommand ().equals ("showFindDialog")){
				getFindDialog ().show ();
			}
		}
	}
	
	public ApplicationContext getApplicationContext (){
		return this._context;
	}
	
	private About _about;	
	/**
	 * Ritorna la dialog di inserimento nuova entry.
	 * @return la dialog di inserimento nuova entry.
	 */
	public About getAbout (){
		if (this._about==null){
			this._about = new About (getMainWindow (), true, _context);
		}
		return this._about;
	}
	
	
}
