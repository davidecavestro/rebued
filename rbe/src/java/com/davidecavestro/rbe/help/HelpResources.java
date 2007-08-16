/*
 * HelpResources.java
 *
 * Created on 30 gennaio 2006, 22.55
 */

package com.davidecavestro.rbe.help;

import com.davidecavestro.common.help.HelpResource;

/**
 * Dichiara le costanti di utilizzo per la mappatura delle risorse di help
 *
 * @author  davide
 */
public interface HelpResources {
	
	/** La pulsantiera principale. */
	public final static HelpResource MAIN_TOOLBAR = new HelpResource ("html.maintoolbar");
	
	/** La tabella dei valori. */
	public final static HelpResource VALUES_TABLE = new HelpResource ("html.valuestable");
	
	/** L'albero dei bundle. */
	public final static HelpResource BUNDLE_TREE = new HelpResource ("html.bundletree");
	
	/** Il pannello di ditazione. */
	public final static HelpResource EDITOR_PANEL = new HelpResource ("html.editorpanel");
	
	/** La barra di stato. */
	public final static HelpResource STATUS_BAR = new HelpResource ("html.statusbar");
	
	/** La finestra di ricerca. */
	public final static HelpResource FIND_DIALOG = new HelpResource ("html.finddialog");
	
	
}
