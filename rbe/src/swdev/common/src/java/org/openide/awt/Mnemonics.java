package org.openide.awt;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.AbstractButton;
import javax.swing.JLabel;

public final class Mnemonics {
	
	private Mnemonics () {
	}
	
	private static void setLocalizedText2 (Object item, String text) {
		if(text == null) {
			setText (item, null);
			return;
		}
		int i = findMnemonicAmpersand (text);
		if(i < 0) {
			setText (item, text);
			setMnemonic (item, 0);
		} else {
			setText (item, text.substring (0, i) + text.substring (i + 1));
			char ch = text.charAt (i + 1);
			if(ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z' || ch >= '0' && ch <= '9') {
				setMnemonic (item, ch);
				setMnemonicIndex (item, i, ch);
			} else {
				int latinCode = getLatinKeycode (ch);
				setMnemonic (item, latinCode);
				setMnemonicIndex (item, i, latinCode);
			}
		}
	}
	
	public static void setLocalizedText (AbstractButton item, String text) {
		setLocalizedText2 (item, text);
	}
	
	public static void setLocalizedText (JLabel item, String text) {
		setLocalizedText2 (item, text);
	}
	
	public static int findMnemonicAmpersand (String text) {
		int i = -1;
		do {
			i = text.indexOf ('&', i + 1);
			if(i >= 0 && i + 1 < text.length () && text.charAt (i + 1) != ' ' && (text.charAt (i + 1) != '\'' || i <= 0 || text.charAt (i - 1) != '\''))
				return i;
		} while(i >= 0);
		return -1;
	}
	
	private static int getLatinKeycode (char localeChar) {
		try {
			String str = getBundle ().getString ("MNEMONIC_" + localeChar);
			if(str.length () == 1)
				return str.charAt (0);
			return Integer.parseInt (str);
		} catch (MissingResourceException x){
			x.printStackTrace ();
		}
		return localeChar;
	}
	
	private static boolean isJDK14orLater () {
		if(weKnowJDK)
			return isJDK14orLaterCache;
		String spec = System.getProperty ("java.specification.version");
		if(spec == null) {
			weKnowJDK = true;
			isJDK14orLaterCache = false;
		} else {
			int major = Integer.parseInt (spec.substring (0, spec.indexOf ('.')));
			int minor = Integer.parseInt (spec.substring (spec.indexOf ('.') + 1));
			weKnowJDK = true;
			isJDK14orLaterCache = major > 1 || minor >= 4;
		}
		return isJDK14orLaterCache;
	}
	
	private static void setMnemonicIndex (Object item, int index, int latinCode) {
		if(isJDK14orLater ()) {
			try {
				Method sdmi = item.getClass ().getMethod ("setDisplayedMnemonicIndex", new Class[] {
					Integer.TYPE
				});
				sdmi.invoke (item, new Object[] {
					new Integer (index)
				});
			}
			catch(Exception x) {
				x.printStackTrace ();
				isJDK14orLaterCache = false;
				setMnemonicIndex (item, index, latinCode);
			}
		} else {
			String text = getText (item);
			if(text.indexOf (latinCode) == -1)
				setText (item, MessageFormat.format (getBundle ().getString ("FORMAT_MNEMONICS"), new Object[] {
					text, new Character ((char)latinCode)
				}));
				setMnemonic (item, latinCode);
		}
	}
	
	private static void setText (Object item, String text) {
		if(item instanceof AbstractButton)
			((AbstractButton)item).setText (text);
		else
			((JLabel)item).setText (text);
	}
	
	private static String getText (Object item) {
		if(item instanceof AbstractButton)
			return ((AbstractButton)item).getText ();
		else
			return ((JLabel)item).getText ();
	}
	
	private static void setMnemonic (Object item, int mnem) {
		if(mnem >= 97 && mnem <= 122)
			mnem += -32;
		if(item instanceof AbstractButton)
			((AbstractButton)item).setMnemonic (mnem);
		else
			((JLabel)item).setDisplayedMnemonic (mnem);
	}
	
	private static ResourceBundle getBundle () {
		return ResourceBundle.getBundle ("org.openide.awt.Mnemonics");
	}
	
	private static boolean isJDK14orLaterCache;
	private static boolean weKnowJDK;
}
