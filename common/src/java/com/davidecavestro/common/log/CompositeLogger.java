/*
 * ParallelLogger.java
 *
 * Created on 11 dicembre 2004, 20.45
 */

package com.davidecavestro.common.log;

/**
 * Logger composto. Implementa una catena di responsabilita', permettendo 
 * l'utilizo in sequenza di diversi tipi di logger.
 *
 * @author  davide
 */
public final class CompositeLogger implements Logger{
	
	/**
	 * Il logger effettivo, a cui viene delegata la registrazione dei messaggi.
	 */
	private Logger _actualLogger;
	
	/**
	 * Il logger successivo nella catena.
	 */
	private Logger _successor;
	
	/**
	 * Costruttore.
	 * @param actualLogger il logger effettivo,
	 * @param successor il logger successivo nella catena.
	 */
	public CompositeLogger (final Logger actualLogger, final Logger successor) {
		this._actualLogger = actualLogger;
		this._successor = successor;
	}
	
	/**
	 * Imposta il logger successivo.
	 * 
	 * @param successor  il logger successivo.
	 */
	public void setSuccessor (final Logger successor) {
	    this._successor = successor;
	}
	
	/**
	 * Registra un messaggio di DEBUG.
	 * @param message il messaggio.
	 */
	public void debug (final String... message) {
		debug (this._actualLogger, message);
		debug (this._successor, message);
	}
	
	private void debug (final Logger logger, final String... message) {
		if (logger!=null){
			logger.debug (message);
		}
	}
	
	/**
	 * Registra un messaggio di ERRORE.
	 * @param message il messaggio.
	 */
	public void error (final String... message) {
		error (this._actualLogger, message);
		error (this._successor, message);
	}
	
	private void error (final Logger logger, final String... message) {
		if (logger!=null){
			logger.error (message);
		}
	}
	
	/**
	 * Registra un messaggio di ERRORE associato ad un evento specificato.
	 * @param message il messaggio.
	 * @param t l'evento.
	 */
	public void error (final Throwable t, final String... message) {
		error (this._actualLogger, t, message);
		error (this._successor, t, message);
	}
	
	private void error (final Logger logger, final Throwable t, final String... message) {
		if (logger!=null){
			logger.error (t, message);
		}
	}
	
	/**
	 * Registra un messaggio di INFORMAZIONE.
	 * @param message il messaggio.
	 */
	public void info (final String... message) {
		info (this._actualLogger, message);
		info (this._successor, message);
	}
	
	private void info (final Logger logger, final String... message) {
		if (logger!=null){
			logger.info (message);
		}
	}
	
	/**
	 * Registra un messaggio di AVVISO.
	 * @param message il messaggio.
	 */
	public void warning (final String... message) {
		warning (this._actualLogger, message);
		warning (this._successor, message);
	}
	
	private void warning (final Logger logger, final String... message) {
		if (logger!=null){
			logger.warning (message);
		}
	}
	
	/**
	 * Registra un messaggio di AVVISO associato ad un evento specificato.
	 * @param message il messaggio.
	 * @param t l'evento.
	 */
	public void warning (final Throwable t, final String... message) {
		warning (this._actualLogger, t, message);
		warning (this._successor, t, message);
	}
	
	private void warning (final Logger logger, final Throwable t, final String... message) {
		if (logger!=null){
			logger.warning (t, message);
		}
	}
	
	public void close () {
		close (this._actualLogger);
		close (this._successor);
	}
	
	private void close (final Logger logger) {
		if (logger!=null){
			logger.close ();
		}
	}
	
	public void debug (final Throwable t, final String... message) {
		debug (this._actualLogger, t, message);
		debug (this._successor, t, message);
	}
	
	private void debug (final Logger logger, final Throwable t, final String... message) {
		if (logger!=null){
			logger.debug (t, message);
		}
	}
	
}
