/*
 * ConsoleLogger.java
 *
 * Created on 11 maggio 2005, 19.56
 */

package com.davidecavestro.common.log;

import com.davidecavestro.common.util.CalendarUtils;
import java.awt.Color;
import java.io.*;
import java.util.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;

/**
 * Mostra gli eventi su console grafica.
 *
 * @author  davide
 */
public class ConsoleLogger implements Logger{
	/**
	 * Il TAG di A CAPO.
	 */
	private final static String NEWLINE = "\n";
	
	/**
	 * Il documento.
	 */
	private StyledDocument _document;
	
	/**
	 * Costruttore.
	 *
	 * @param document il documento da usare per la console.
	 * @param append <TT>true</TT> se le informazioni di log vanno accodate al
	 * contenuto esitente del documento.
	 */
	public ConsoleLogger (final StyledDocument document, final boolean append) {
		this._document = document;
		createStyles ();
	}
	
	/**
	 * Rilascia le risorse allocate da questo logger.
	 *
	 * @throws Throwable in caso di eccezioni non gestibili.
	 */	
    protected void finalize() throws Throwable {
		close ();
		this._document=null;
		super.finalize ();
	}
	
	/**
	 * Chiude gli stream aperti.
	 */
	public void close (){
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
		printMessage (MessageType.ERROR, message, t);
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
	 * e' opzionale.
	 *
	 * @param type il tipo di registrazione.
	 * @param message il messaggio da registrare.
	 * @param t l'evento associato (opzionale).
	 */	
	private final void printMessage (final MessageType type, final String message, final Throwable t){
		
		addParagraph (new Paragraph ("normal", 
			new Run []{
				new Run ("messagetype", type.getType ()+"\t"),
				new Run ("timestamp", CalendarUtils.toTSString (Calendar.getInstance ().getTime ())),
				new Run ("none", new StringBuffer (": ").append (message).toString ())
		}));
		
//		final StringBuffer sb = new StringBuffer ();
//		sb.append ("<B>").append (type.getType ()).append ("</B>");
//		sb.append (CalendarUtils.toTSString (Calendar.getInstance ().getTime ()));
//		sb.append (": ");
//		sb.append (message);
//		if (t!=null){
//			/*
//			 * Registra anche l'evento, se specificato.
//			 */
//			sb.append (BR);
//			sb.append ("Related event: ");
//			sb.append (BR);
//			sb.append (ExceptionUtils.getStackTrace (t));
//			sb.append (BR);
//		}
//		sb.append (BR);
//		try {
//			System.out.println (sb);
//		} catch (final Exception e){
//			System.out.println (ExceptionUtils.getStackTrace (e));
//		}
	}
	
	private abstract static class MessageType {
		/**
		 * Messaggio di DEBUG.
		 */
		public final static MessageType DEBUG = new MessageType ("DEBUG") {
			public void accept (MessageTypeVisitor v){
				v.visitDebug (this);
			}
		};
		
		/**
		 * Messaggio di INFO.
		 */
		public final static MessageType INFO = new MessageType ("INFO") {
			public  void accept (MessageTypeVisitor v){
				v.visitInfo (this);
			}
		};
		
		/**
		 * Messaggio di WARNING.
		 */
		public final static MessageType WARNING = new MessageType ("WARNING") {
			public  void accept (MessageTypeVisitor v){
				v.visitWarning (this);
			}
		};
		
		/**
		 * Messaggio di ERROR.
		 */
		public final static MessageType ERROR = new MessageType ("ERROR") {
			public  void accept (MessageTypeVisitor v){
				v.visitError (this);
			}
		};
		
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
		
		/**
		 * Ritorna la descrizione di questo tipo.
		 *
		 * @return la descrizione di questo tipo.
		 */		
		public String getType (){
			return this._type;
		}
		
		/**
		 * Accetta l'agente esterno "visitatore", in modo da consentire un'espansione dell'interfaccia.
		 * 
		 * @param v  il visitatore.
		 */
		abstract void accept (MessageTypeVisitor v);
	}
	
	/**
	 * INterfaccia "visitatore" per l'elaborazione dei tipi di messaggio.
	 */
	private static interface MessageTypeVisitor {
		void visitDebug (MessageType mt);
		void visitInfo (MessageType mt);
		void visitWarning (MessageType mt);
		void visitError (MessageType mt);
	}
	
	
	
	private void createStyles () {
		// no attributes defined
		Style s = styles.addStyle (null, null);
		StyleConstants.setForeground (s, Color.WHITE);
		runAttr.put ("none", s);
		s = styles.addStyle (null, null);
		StyleConstants.setBold (s, true);
		StyleConstants.setForeground (s, new Color (153,153,102));
		runAttr.put ("messagetype", s);
		
		s = styles.addStyle (null, null);
		StyleConstants.setFontFamily (s, "Monospaced");
		StyleConstants.setBold (s, true);
//		StyleConstants.setForeground (s, new Color (51,102,153));
		StyleConstants.setForeground (s, Color.WHITE);
		runAttr.put ("timestamp", s); 
		
		Style def = styles.getStyle (StyleContext.DEFAULT_STYLE);
		
		Style heading = styles.addStyle ("heading", def);
		//StyleConstants.setFontFamily(heading, "SansSerif");
		StyleConstants.setBold (heading, true);
		StyleConstants.setAlignment (heading, StyleConstants.ALIGN_CENTER);
		StyleConstants.setSpaceAbove (heading, 10);
		StyleConstants.setSpaceBelow (heading, 10);
		StyleConstants.setFontSize (heading, 18);
		
		// Title
		Style sty = styles.addStyle ("title", heading);
		StyleConstants.setFontSize (sty, 32);
		
		// edition
		sty = styles.addStyle ("edition", heading);
		StyleConstants.setFontSize (sty, 16);
		
		// author
		sty = styles.addStyle ("author", heading);
		StyleConstants.setItalic (sty, true);
		StyleConstants.setSpaceBelow (sty, 25);
		
		// subtitle
		sty = styles.addStyle ("subtitle", heading);
		StyleConstants.setSpaceBelow (sty, 35);
		
		// normal
		sty = styles.addStyle ("normal", def);
		StyleConstants.setLeftIndent (sty, 10);
		StyleConstants.setRightIndent (sty, 10);
		//StyleConstants.setFontFamily(sty, "SansSerif");
		StyleConstants.setFontSize (sty, 14);
		StyleConstants.setSpaceAbove (sty, 1);
		StyleConstants.setSpaceBelow (sty, 1);
	}
	
	private final StyleContext styles = new StyleContext ();
	private final Hashtable runAttr = new Hashtable ();
	
	
	private void addParagraph (Paragraph p) {
		try {
			Style s = null;
			for (int i = 0; i < p.data.length; i++) {
				Run run = p.data[i];
				s = (Style) runAttr.get (run.attr);
				_document.insertString (_document.getLength (), run.content, s);
			}
			
			// set logical style
			Style ls = styles.getStyle (p.logical);
			_document.setLogicalStyle (_document.getLength () - 1, ls);
			_document.insertString (_document.getLength (), "\n", null);
		} catch (BadLocationException e) {
			System.err.println ("Internal error: " + e);
		}
	}
	
	static class Paragraph {
		Paragraph (String logical, Run[] data) {
			this.logical = logical;
			this.data = data;
		}
		String logical;
		Run[] data;
	}
	
	static class Run {
		Run (String attr, String content) {
			this.attr = attr;
			this.content = content;
		}
		String attr;
		String content;
	}
	
	public Document getDocument (){
		return this._document;
	}
  
}