/*
 * HelpManager.java
 *
 * Created on 24 dicembre 2004, 15.04
 */

package com.davidecavestro.common.help;

import javax.help.*;
import javax.swing.*;
import java.net.*;

/**
 * Gestore centralizzato dell'help dell'applicazione.
 *
 * @author  davide
 */
public final class HelpManager {
	
	private HelpBroker _mainHelpBroker;
	private CSH.DisplayHelpFromSource _csh;
	
	private final HelpResourcesResolver _resolver;
	
	/** Costruttore . */
	public HelpManager (HelpResourcesResolver resolver, String mainHelpsetPath) {
		_resolver = resolver;
		// try to find the helpset and create a HelpBroker object
		if (_mainHelpBroker == null){
			HelpSet mainHelpSet = null;
			try {
				URL hsURL = HelpSet.findHelpSet (null, mainHelpsetPath);
				if (hsURL == null) {
					mainHelpSet = new HelpSet (null);
					System.err.println ("HelpSet " + mainHelpsetPath + " not found.");
				} else {
					mainHelpSet = new HelpSet (null, hsURL);
				}
			} catch (HelpSetException ee) {
				ee.printStackTrace (System.err);
			}
			if (mainHelpSet != null)
				_mainHelpBroker = mainHelpSet.createHelpBroker ();
			if (_mainHelpBroker != null)
				// CSH.DisplayHelpFromSource is a convenience class to display the helpset
				_csh = new CSH.DisplayHelpFromSource (_mainHelpBroker);
		}
	}
	
	
	/**
	 * Inizializza la voce di menu specificata per l'utilizzo come lancio dell'help.
	 *
	 * @param helpItem la voce di menu.
	 */	
	public void initialize (JMenuItem helpItem){
		
		// listen to ActionEvents from the helpItem
		if (_csh != null)
			helpItem.addActionListener (_csh);
		
	}
	
	/**
	 * Inizializza il pulsante specificato per l'utilizzo come lancio dell'help.
	 *
	 * @param helpButton il pulsante
	 */	
	public void initialize (JButton helpButton){
		
		// listen to ActionEvents from the helpItem
		if (_csh != null)
			helpButton.addActionListener (_csh);
		
	}
	
	/**
	 * Ritorna il broker principale.
	 *
	 * @return il broker principale.
	 */	
	public HelpBroker getMainHelpBroker (){
		return _mainHelpBroker;
	}
	
	public HelpResourcesResolver getResolver (){
		return _resolver;
	}
}
