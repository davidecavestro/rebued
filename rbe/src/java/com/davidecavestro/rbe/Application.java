/*
 * Application.java
 *
 * Created on 26 novembre 2005, 14.55
 */

package com.davidecavestro.rbe;

import com.davidecavestro.common.application.ApplicationData;
import com.davidecavestro.common.gui.persistence.PersistenceStorage;
import com.davidecavestro.common.gui.persistence.UIPersister;
import com.davidecavestro.common.help.HelpManager;
import com.davidecavestro.common.help.HelpResourcesResolver;
import com.davidecavestro.common.log.CompositeLogger;
import com.davidecavestro.common.log.ConsoleLogger;
import com.davidecavestro.common.log.Logger;
import com.davidecavestro.common.log.LoggerAdapter;
import com.davidecavestro.common.log.PlainTextLogger;
import com.davidecavestro.common.util.*;
import com.davidecavestro.rbe.conf.ApplicationEnvironment;
import com.davidecavestro.rbe.conf.CommandLineApplicationEnvironment;
import com.davidecavestro.rbe.conf.UserResources;
import com.davidecavestro.rbe.conf.UserSettings;
import com.davidecavestro.rbe.gui.WindowManager;
import com.davidecavestro.rbe.actions.ActionManager;
import com.davidecavestro.rbe.conf.ApplicationOptions;
import com.davidecavestro.rbe.conf.DefaultSettings;
import com.davidecavestro.rbe.model.DefaultResourceBundleModel;
import com.davidecavestro.rbe.model.LocalizationProperties;
import com.davidecavestro.rbe.model.ResourceBundleModel;
import com.davidecavestro.rbe.model.undo.RBUndoManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;

/**
 * Il gestore centrale dell'intera applicazione.
 *
 * @author  davide
 */
public class Application {
	private CompositeLogger _logger;
	private final ApplicationEnvironment _env;
	private final ApplicationContext _context;
	
	/** 
	 * Costruttore.
	 */
	public Application (final CommandLineApplicationEnvironment args) {
		this._env = args;

//		final Locale fooLocale = Locale.ITALIAN;
//		final Properties fooProperties = new Properties ();
//		
//		try {
//			fooProperties.load (new FileInputStream ("/tmp/foo_it.properties"));
//		} catch (FileNotFoundException fnfe){
//			fnfe.printStackTrace (System.err);
//		}catch (IOException ioe){
//			ioe.printStackTrace (System.err);
//		}
//		
//		final Locale fooLocale1 = new Locale ("");
//		final Properties fooProperties1 = new Properties ();
//		
//		try {
//			fooProperties1.load (new FileInputStream ("/tmp/foo.properties"));
//		} catch (FileNotFoundException fnfe){
//			fnfe.printStackTrace (System.err);
//		}catch (IOException ioe){
//			ioe.printStackTrace (System.err);
//		}
		
		RBUndoManager undoManager = new RBUndoManager ();

		final Properties releaseProps = new Properties ();
		try {
			/*
			 * carica dati di configurazione.
			 */
			releaseProps.load(getClass().getResourceAsStream ("release.properties"));
		} catch (final Exception e) {
			System.err.println ("Cannot load release properties");
			/*@todo mostrare stacktrace finito lo sviluppo*/
//			e.printStackTrace (System.err);
		}

		final ApplicationData applicationData = new ApplicationData (releaseProps);
		final UserSettings userSettings = new UserSettings (this, new UserResources (applicationData));
		
		final ApplicationOptions applicationOptions = new ApplicationOptions (userSettings, new ApplicationOptions (new DefaultSettings (args), null));
		
		/**
		 * Percorso del file di configuraizone/mappatura, relativo alla directory di 
		 * installazione dell'applicazione.
		 */
		final Properties p = new Properties ();
		try {
			p.load (new FileInputStream (_env.getApplicationDirPath ()+"/helpmap.properties"));
		} catch (IOException ioe){
			System.out.println ("Missing help resources mapping file");
		}
		
		
		try {
			final StringBuffer sb = new StringBuffer ();
			sb.append (applicationOptions.getLogDirPath () ).append ("/")
			.append (CalendarUtils.getTS (Calendar.getInstance ().getTime (), CalendarUtils.FILENAME_TIMESTAMP_FORMAT))
			.append (".log");
			
			final File plainTextLogFile = new File (sb.toString ());
			
//			logDocument.setParser (new javax.swing.text.html.parser.ParserDelegator ());
			
			_logger = new CompositeLogger (new PlainTextLogger (plainTextLogFile, true, 10), null);
			
		} catch (IOException ioe){
			System.out.println ("Logging disabled. CAUSE: "+ExceptionUtils.getStackTrace (ioe));
			this._logger = new CompositeLogger (new LoggerAdapter (), null);
		}
		
		final PropertiesExceptionHandler peh = new PropertiesExceptionHandler () {
				public void malformedEncoding (String s) throws IllegalArgumentException {
					if (applicationOptions.discardMalformedEncoding ()) {
						_logger.error ("Malformed \\uxxxx encoding in string: "+ s);
						 throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
					}
					_logger.warning ("Malformed \\uxxxx encoding in string: "+ s);
				}
			};
		
		final DefaultResourceBundleModel model = new DefaultResourceBundleModel (applicationOptions, peh, "blank", new LocalizationProperties [] {new LocalizationProperties (LocalizationProperties.DEFAULT, new CommentedProperties (peh))});
		
		this._context = new ApplicationContext (
			_env,
			applicationOptions,
			new WindowManager (),
			new UIPersister (new UserUIStorage (userSettings)),
			_logger,
			userSettings,
			applicationData,
			model,
			undoManager,
			new ActionManager (),
			new HelpManager (new HelpResourcesResolver (p), "help/MainUrbeHelp.hs"),
			peh
			);
		
		model.addUndoableEditListener(undoManager);
		
	}
	
	
	/**
	 * Fa partire l'applicazione.
	 */
	public void start (){
		_context.getLogger().info ("starting UI");
		final WindowManager wm = this._context.getWindowManager ();
		wm.getSplashWindow (this._context.getApplicationData ()).show ();
		try {
			wm.getSplashWindow (this._context.getApplicationData ()).showInfo ("Initializing context...");
			wm.init (this._context);
			final ConsoleLogger cl = new ConsoleLogger (new DefaultStyledDocument(), true);

			_context.getWindowManager ().getLogConsole ().init (cl.getDocument ());

			this._logger.setSuccessor (cl);
	//		{
	//			wm.getMainWindow ().addWindowListener (
	//			new java.awt.event.WindowAdapter () {
	//				public void windowClosing (java.awt.event.WindowEvent evt) {
	//					Application.this.exit ();
	//				}
	//			});
	//		}
			wm.getSplashWindow (this._context.getApplicationData ()).showInfo ("Preparing main window...");
			wm.getMainWindow ().addWindowListener (
				new java.awt.event.WindowAdapter () {
					public void windowClosing (java.awt.event.WindowEvent evt) {
						if (wm.getMainWindow ().checkForDataLoss ()){
							exit ();
						}
					}
				});
		} finally {
			wm.getSplashWindow (this._context.getApplicationData ()).hide ();
		}
		wm.getMainWindow ().show ();
		_context.getLogger().info ("UI successfully started");
	}
	
	public Logger getLogger (){
		return this._logger;
	}
	
	/**
	 * Operazioni da effettuare prima dell'uscita dall'applicazione, tra le quali:
	 *	<UL>
	 *		<LI>Salvataggio impostazioni utente.
	 *	</UL>
	 */
	public void beforeExit (){
//		synchronized (this){
//			setChanged ();
//			notifyObservers (ObserverCodes.APPLICATIONEXITING);
//		}
		this._context.getUIPersisteer ().makePersistentAll ();
		
//		closeActiveStoreData ();
		
		/* Forza chiusura logger. */
		this._logger.close ();
		this._context.getUserSettings ().storeProperties ();
	}

	/**
	 * Termina l'applicazione.
	 */
	public final void exit (){
		beforeExit ();
		System.exit (0);
	}
	
	private final class UserUIStorage implements PersistenceStorage {
		private final UserSettings _userSettings;
		public UserUIStorage (final UserSettings userSettings){
			this._userSettings = userSettings;
		}

		public java.util.Properties getRegistry () {
			return this._userSettings.getProperties ();
		}
		
	}
	
}
