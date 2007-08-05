/*
 * PlainTextLogger.java
 *
 * Created on 11 dicembre 2004, 20.18
 */

package com.davidecavestro.common.log;

import com.davidecavestro.common.util.CalendarUtils;
import com.davidecavestro.common.util.ExceptionUtils;
import com.davidecavestro.common.util.FileUtils;
import java.io.*;
import java.util.*;

/**
 * Registra li eventi su file di testo piano.
 *
 * @author  davide
 */
public class PlainTextLogger implements Logger{
	/**
	 * Il carattere di A CAPO.
	 */
	private final static String CR = "\n";
	
	/**
	 */
	private Writer _fw;
	
	/**
	 * Costruttore.
	 *
	 * @param logFile il file da usare per il logging.
	 * @param append <TT>true</TT> se le informazioni di log vanno accodate al 
	 * contenuto esitente del file.
	 * @throws IOException in caso di eccezione non prevista.
	 */
	public PlainTextLogger (final File logFile, final boolean append, final int bufferSize) throws IOException {
		FileUtils.makeFilePath (logFile);
		try {
			this._fw = new BufferedWriter (new FileWriter (logFile, append), bufferSize);
		} catch (Exception e){
			System.out.println ("Error creating log file. CAUSE:\n"+ ExceptionUtils.getStackTrace (e).toString ()+"\nI'll try to continue using a temporary file.");
			/* tenta scrittura su file temporaneo */
			this._fw = new BufferedWriter (new FileWriter (File.createTempFile ("timekeeper_log_", ".txt")), bufferSize);;
		}
	}
	
	/**
	 * Rilascia le risorse allocate da questo logger.
	 *
	 * @throws Throwable in caso di eccezioni non gestibili.
	 */	
    protected void finalize() throws Throwable {
		close ();
		this._fw=null;
		super.finalize ();
	}
	
	/**
	 * Chiude gli stream aperti.
	 */
	public void close (){
		if (this._fw!=null){
			try {
				this._fw.flush ();
				this._fw.close ();
			} catch (IOException ioe){
				System.out.println (ExceptionUtils.getStackTrace (ioe));
			}
		}
	}
	
	/**
	 * Registra un messaggio di DEBUG.
	 * @param message il messaggio.
	 */
	public void debug (final String message) {
		printMessage (MessageType.DEBUG, message);
	}
	
	/**
	 * Registra un messaggio di DEBUG associato ad un evento specificato.
	 * @param message il messaggio.
	 * @param t l'evento.
	 */
	public void debug (final String message, final Throwable t) {
		printMessage (MessageType.DEBUG, message);
	}
	
	/**
	 * Registra un messaggio di ERRORE.
	 * @param message il messaggio.
	 */
	public void error (final String message) {
		printMessage (MessageType.ERROR, message);
	}
	
	/**
	 * Registra un messaggio di ERRORE associato ad un evento specificato.
	 * @param message il messaggio.
	 * @param t l'evento.
	 */
	public void error (final String message, final Throwable t) {
		printMessage (MessageType.ERROR, message);
	}
	
	/**
	 * Registra un messaggio di INFORMAZIONE.
	 * @param message il messaggio.
	 */
	public void info (final String message) {
		printMessage (MessageType.INFO, message);
	}
	
	/**
	 * Registra un messaggio di AVVISO.
	 * @param message il messaggio.
	 */
	public void warning (final String message) {
		printMessage (MessageType.WARNING, message);
	}
	
	/**
	 * Registra un messaggio di AVVISO associato ad un evento specificato.
	 * @param message il messaggio.
	 * @param t l'evento.
	 */
	public void warning (final String message, final Throwable t) {
		printMessage (MessageType.WARNING, message);
	}
	
	/**
	 * Registra nel file di log il messaggio con il tipo.
	 *
	 * @param type il tipo di registrazione.
	 * @param message il messaggio da registrare.
	 */	
	private final void printMessage (final MessageType type, final String message){
		printMessage (type, message, null);
	}
	
	/**
	 * Registra nel file di log il messaggio, con il tipo e l'evento specificati. L'evento 
	 *  e'  opzionale.
	 *
	 * @param type il tipo di registrazione.
	 * @param message il messaggio da registrare.
	 * @param t l'evento associato (opzionale).
	 */	
	private final void printMessage (final MessageType type, final String message, final Throwable t){
		final StringBuffer sb = new StringBuffer ();
		sb.append (CalendarUtils.toTSString (Calendar.getInstance ().getTime ()));
		sb.append (": ");
		sb.append (message);
		if (t!=null){
			/*
			 * Registra anche l'evento, se specificato.
			 */
			sb.append (CR);
			sb.append ("Related event: ");
			sb.append (CR);
			sb.append (ExceptionUtils.getStackTrace (t));
			sb.append (CR);
		}
		sb.append (CR);
		try {
			this._fw.write (sb.toString ());
//			this._fw.flush ();
		} catch (final IOException ioe){
			System.out.println (ExceptionUtils.getStackTrace (ioe));
		}
	}
	
	private final static class MessageType {
		/**
		 * Messaggio di DEBUG.
		 */
		public final static MessageType DEBUG = new MessageType ("DEBUG");
		
		/**
		 * Messaggio di INFO.
		 */
		public final static MessageType INFO = new MessageType ("INFO");
		
		/**
		 * Messaggio di WARNING.
		 */
		public final static MessageType WARNING = new MessageType ("WARNING");
		
		/**
		 * Messaggio di ERROR.
		 */
		public final static MessageType ERROR = new MessageType ("ERROR");
		
		/**
		 * L adescrizione.
		 */
		private String _type;
		/**
		 * Costruttore privato. Evita istanziazione.
		 * @param type la descrizione di questo tipo.
		 */
		private MessageType (final String type){
			this._type = type;
		}
	}
}