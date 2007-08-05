/*
 * Logger.java
 *
 * Created on 11 dicembre 2004, 19.10
 */

package com.davidecavestro.common.log;

/**
 * Componente per le registrazione di messaggi inerenti allo stato 
 * dell'applicazione ed alle elaborazioni in atto.
 *
 * @author  davide
 */
public interface Logger {
	/**
	 * Registra un messaggio di DEBUG.
	 * @param message il messaggio.
	 */
	public void debug (final String message);
	/**
	 * Registra un messaggio di DEBUG associatoall'eventospecificato.
	 * @param t l'evento.
	 * @param message il messaggio.
	 */
	public void debug (final String message, Throwable t);
	/**
	 * Registra un messaggio di INFORMAZIONE.
	 * @param message il messaggio.
	 */
	public void info (final String message);
	/**
	 * Registra un messaggio di AVVISO.
	 * @param message il messaggio.
	 */
	public void warning (final String message);
	/**
	 * Registra un messaggio di AVVISO associato ad un evento specificato.
	 * @param message il messaggio.
	 * @param t l'evento.
	 */
	public void warning (final String message, final Throwable t);
	/**
	 * Registra un messaggio di ERRORE.
	 * @param message il messaggio.
	 */
	public void error (final String message);
	/**
	 * Registra un messaggio di ERRORE associato ad un evento specificato.
	 * @param message il messaggio.
	 * @param t l'evento.
	 */
	public void error (final String message, final Throwable t);
	
	/**
	 * Chiude gli stream aperti.
	 */
	public void close();
}
