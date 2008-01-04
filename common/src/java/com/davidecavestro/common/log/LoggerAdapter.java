/*
 * LoggerAdapter.java
 *
 * Created on 30 novembre 2005, 23.05
 */

package com.davidecavestro.common.log;

/**
 * Implementazione di comodo vuota. ignora tutti i messaggi.
 *
 * @author  davide
 */
public class LoggerAdapter implements Logger {
	
	/** Costruttore */
	public LoggerAdapter () {
	}
	
	public void close () {
	}
	
	public void debug (final String... message) {
	}
	
	public void debug (final Throwable t, final String... message) {
	}
	
	public void error (final String... message) {
	}
	
	public void error (final Throwable t, final String... message) {
	}
	
	public void info (final String... message) {
	}
	
	public void warning (final String... message) {
	}
	
	public void warning (final Throwable t, final String... message) {
	}
	
}
