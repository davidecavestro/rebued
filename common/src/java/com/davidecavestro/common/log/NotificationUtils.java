/*
 * NotificationUtils.java
 *
 * Created on January 3, 2008, 1:27 PM
 *
 */

package com.davidecavestro.common.log;

import com.davidecavestro.common.log.MessageType.MessageTypeVisitor;
import com.davidecavestro.common.util.ArrayUtils;
import com.davidecavestro.common.util.ExceptionUtils;
import java.awt.HeadlessException;
import java.io.PrintStream;
import javax.swing.JOptionPane;

/**
 * Un <CODE>Logger</CODE> che fornisce le operazioni di notifica utilizzando Swing se possibile, e poi inoltrandole agli stream di sistema.
 *
 *
 * @author Davide Cavestro
 */
public class NotificationUtils implements Logger, MessageTypeVisitor {
	
	/**
	 * Costruttore.
	 */
	public NotificationUtils () {
	}
	
	public void debug (final String... message) {
		MessageType.DEBUG.accept (this, message);
	}
	
	public void debug (final Throwable t, final String... message) {
		MessageType.DEBUG.accept (this, message + " Stacktrace: "+ ExceptionUtils.getStackTrace (t).toString ());
	}
	
	public void info (final String... message) {
		MessageType.INFO.accept (this, message);
	}
	
	public void warning (final String... message) {
		MessageType.WARNING.accept (this, message);
	}
	
	public void warning (final Throwable t, final String... message) {
		MessageType.WARNING.accept (this, message);
	}
	
	public void error (final String... message) {
		MessageType.ERROR.accept (this, message);
	}
	
	public void error (final Throwable t, final String... message) {
		MessageType.ERROR.accept (this, message + " Stacktrace: "+ ExceptionUtils.getStackTrace (t).toString ());
	}
	
	public void close () {
	}

	public Object acceptError (MessageType t, String... message) {
		return showMessage (JOptionPane.ERROR_MESSAGE, "Error message", System.err, message);
	}

	public Object acceptDebug (MessageType t, String... message) {
		return showMessage (JOptionPane.INFORMATION_MESSAGE, "Debug message", System.out, message);
	}
	
	public Object acceptInfo (MessageType t, String... message) {
		return showMessage (JOptionPane.INFORMATION_MESSAGE, "Information message", System.out, message);
	}

	public Object acceptWarning (MessageType t, String... message) {
		return showMessage (JOptionPane.WARNING_MESSAGE, "Warning message", System.err, message);
	}
	
	private Object showMessage (final int messageType, final String title, final PrintStream failoverStream, final String... message) {
		try {
			JOptionPane.showMessageDialog (null, message, title, messageType);
		} catch (final HeadlessException he) {
			/*
			 * Ambiente grafico non disponibile
			 * si continua sullo stream failover
			 */
		}
		ArrayUtils.printlnArray (failoverStream, message);
		
		return null;
	}

}
