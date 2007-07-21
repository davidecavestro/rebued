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
	public void debug (final String message) {
		debug (this._actualLogger, message);
		debug (this._successor, message);
	}
	
	private void debug (final Logger logger, final String message) {
		if (logger!=null){
			logger.debug (message);
		}
	}
	
	/**
	 * Registra un messaggio di ERRORE.
	 * @param message il messaggio.
	 */
	public void error (final String message) {
		error (this._actualLogger, message);
		error (this._successor, message);
	}
	
	private void error (final Logger logger, final String message) {
		if (logger!=null){
			logger.error (message);
		}
	}
	
	/**
	 * Registra un messaggio di ERRORE associato ad un evento specificato.
	 * @param message il messaggio.
	 * @param t l'evento.
	 */
	public void error (final String message, final Throwable t) {
		error (this._actualLogger, message, t);
		error (this._successor, message, t);
	}
	
	private void error (final Logger logger, final String message, final Throwable t) {
		if (logger!=null){
			logger.error (message, t);
		}
	}
	
	/**
	 * Registra un messaggio di INFORMAZIONE.
	 * @param message il messaggio.
	 */
	public void info (final String message) {
		info (this._actualLogger, message);
		info (this._successor, message);
	}
	
	private void info (final Logger logger, final String message) {
		if (logger!=null){
			logger.info (message);
		}
	}
	
	/**
	 * Registra un messaggio di AVVISO.
	 * @param message il messaggio.
	 */
	public void warning (final String message) {
		warning (this._actualLogger, message);
		warning (this._successor, message);
	}
	
	private void warning (final Logger logger, final String message) {
		if (logger!=null){
			logger.warning (message);
		}
	}
	
	/**
	 * Registra un messaggio di AVVISO associato ad un evento specificato.
	 * @param message il messaggio.
	 * @param t l'evento.
	 */
	public void warning (final String message, final Throwable t) {
		warning (this._actualLogger, message,t);
		warning (this._successor, message, t);
	}
	
	private void warning (final Logger logger, final String message, final Throwable t) {
		if (logger!=null){
			logger.warning (message, t);
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
	
	public void debug (String message, Throwable t) {
		debug (this._actualLogger, message, t);
		debug (this._successor, message, t);
	}
	
	private void debug (final Logger logger, final String message, final Throwable t) {
		if (logger!=null){
			logger.debug (message, t);
		}
	}
	
}
