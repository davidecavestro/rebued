/*
 * AddLocale.java
 *
 * Created on 10 dicembre 2005, 23.43
 */

package com.davidecavestro.rbe.gui;

import com.davidecavestro.common.gui.dialog.DialogEvent;
import com.davidecavestro.common.gui.dialog.DialogNotifier;
import com.davidecavestro.common.gui.dialog.DialogNotifierImpl;
import com.davidecavestro.common.gui.persistence.PersistenceUtils;
import com.davidecavestro.common.gui.persistence.PersistentComponent;
import com.davidecavestro.rbe.model.DefaultResourceBundleModel;
import com.davidecavestro.rbe.model.LocalizationProperties;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author  davide
 */
public class AddLocaleDialog extends javax.swing.JDialog implements PersistentComponent, DialogNotifier {
	private Locale _locale;
    public static final String PROP_CUSTOMIZED_LOCALE = "customized_locale";
	
	
    private static final Locale predefinedLocales[] = {
        new Locale("ar", "AE", ""), new Locale("ar", "BH", ""), new Locale("ar", "DZ", ""), new Locale("ar", "EG", ""), new Locale("ar", "IQ", ""), new Locale("ar", "JO", ""), new Locale("ar", "KW", ""), new Locale("ar", "LB", ""), new Locale("ar", "LY", ""), new Locale("ar", "MA", ""), 
        new Locale("ar", "OM", ""), new Locale("ar", "QA", ""), new Locale("ar", "SA", ""), new Locale("ar", "SD", ""), new Locale("ar", "SY", ""), new Locale("ar", "TN", ""), new Locale("ar", "YE", ""), new Locale("be", "BY", ""), new Locale("bg", "BG", ""), new Locale("ca", "ES", ""), 
        new Locale("cs", "CZ", ""), new Locale("da", "DK", ""), new Locale("de", "AT", ""), new Locale("de", "AT", "EURO"), new Locale("de", "CH", ""), new Locale("de", "DE", ""), new Locale("de", "DE", "EURO"), new Locale("de", "LU", ""), new Locale("de", "LU", "EURO"), new Locale("el", "GR", ""), 
        new Locale("en", "AU", ""), new Locale("en", "CA", ""), new Locale("en", "GB", ""), new Locale("en", "IE", ""), new Locale("en", "IE", "EURO"), new Locale("en", "NZ", ""), new Locale("en", "US", ""), new Locale("en", "ZA", ""), new Locale("es", "AR", ""), new Locale("es", "BO", ""), 
        new Locale("es", "CL", ""), new Locale("es", "CO", ""), new Locale("es", "CR", ""), new Locale("es", "DO", ""), new Locale("es", "EC", ""), new Locale("es", "ES", ""), new Locale("es", "ES", "EURO"), new Locale("es", "GT", ""), new Locale("es", "HN", ""), new Locale("es", "MX", ""), 
        new Locale("es", "NI", ""), new Locale("es", "PA", ""), new Locale("es", "PE", ""), new Locale("es", "PR", ""), new Locale("es", "PY", ""), new Locale("es", "SV", ""), new Locale("es", "UY", ""), new Locale("es", "VE", ""), new Locale("et", "EE", ""), new Locale("fi", "FI", ""), 
        new Locale("fi", "FI", "EURO"), new Locale("fr", "BE", ""), new Locale("fr", "BE", "EURO"), new Locale("fr", "CA", ""), new Locale("fr", "CH", ""), new Locale("fr", "FR", ""), new Locale("fr", "FR", "EURO"), new Locale("fr", "LU", ""), new Locale("fr", "LU", "EURO"), new Locale("hr", "HR", ""), 
        new Locale("hu", "HU", ""), new Locale("is", "IS", ""), new Locale("it", "CH", ""), new Locale("it", "IT", ""), new Locale("it", "IT", "EURO"), new Locale("iw", "IL", ""), new Locale("ja", "JP", ""), new Locale("ko", "KR", ""), new Locale("lt", "LT", ""), new Locale("lv", "LV", ""), 
        new Locale("mk", "MK", ""), new Locale("nl", "BE", ""), new Locale("nl", "BE", "EURO"), new Locale("nl", "NL", ""), new Locale("nl", "NL", "EURO"), new Locale("no", "NO", ""), new Locale("no", "NO", "B"), new Locale("pl", "PL", ""), new Locale("pt", "BR", ""), new Locale("pt", "PT", ""), 
        new Locale("pt", "PT", "EURO"), new Locale("ro", "RO", ""), new Locale("ru", "RU", ""), new Locale("sh", "YU", ""), new Locale("sk", "SK", ""), new Locale("sl", "SL", ""), new Locale("sq", "AL", ""), new Locale("sr", "YU", ""), new Locale("sv", "SE", ""), new Locale("th", "TH", ""), 
        new Locale("tr", "TR", ""), new Locale("uk", "UA", ""), new Locale("zh", "CN", ""), new Locale("zh", "HK", ""), new Locale("zh", "TW", "")
    };
	
	
	private final DialogNotifierImpl _dialogNotifier;
	
	/** Creates new form AddLocale */
	public AddLocaleDialog (java.awt.Frame parent, boolean modal){
		super (parent, modal);
		initComponents ();
		this._locale = Locale.getDefault ();
		
		languageCombo.setSelectedItem (_locale.getLanguage ());
		countryCombo.setSelectedItem (_locale.getCountry ());
		variantCombo.setSelectedItem (_locale.getVariant ());
		actualLocaleText.setText (_locale.toString ());
		
		this._dialogNotifier = new DialogNotifierImpl ();
		
		
		this.getRootPane ().setDefaultButton (okButton);
		
		cancelButton.getInputMap (JComponent.WHEN_IN_FOCUSED_WINDOW).put (KeyStroke.getKeyStroke ("ESCAPE"), "cancel");
		cancelButton.getActionMap().put("cancel", new javax.swing.AbstractAction ("cancel"){
			public void actionPerformed (ActionEvent ae){
				cancel ();
			}
		});
	
		pack ();
		setLocationRelativeTo (null);
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        languageCombo = new JComboBox (Locale.getISOLanguages ());
        countryCombo = new JComboBox (Locale.getISOCountries ());
        variantCombo = new javax.swing.JComboBox();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        helpButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        actualLocaleText = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        predefinedList = new JList (predefinedLocales);

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("Add_locale"));
        setModal(true);
        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel2.setLabelFor(languageCombo);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("&Language_Code:"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 5);
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel3.setLabelFor(countryCombo);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("&Country_Code:"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 5);
        getContentPane().add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel4.setLabelFor(variantCombo);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("&Variant:"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 5);
        getContentPane().add(jLabel4, gridBagConstraints);

        languageCombo.setEditable(true);
        languageCombo.setToolTipText(java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("Select_language"));
        languageCombo.setRenderer(new BasicComboBoxRenderer () {
            public Component getListCellRendererComponent (JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                final JLabel label = (JLabel)super.getListCellRendererComponent (list, value, index, isSelected, cellHasFocus);
                if ("".equals (value.toString ())) {
                    label.setText ("");
                } else {
                    label.setText (value.toString () + " - " + (new Locale ((String)value, "", "")).getDisplayLanguage ());
                }
                return label;
            }
        });
        languageCombo.insertItemAt ("", 0);
        languageCombo.setSelectedIndex (0);

        languageCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                languageComboActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 5);
        getContentPane().add(languageCombo, gridBagConstraints);

        countryCombo.setEditable(true);
        countryCombo.setToolTipText(java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("Select_country"));
        countryCombo.setRenderer(new BasicComboBoxRenderer () {
            public Component getListCellRendererComponent (JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                final JLabel label = (JLabel)super.getListCellRendererComponent (list, value, index, isSelected, cellHasFocus);
                if ("".equals (value.toString ())) {
                    label.setText ("");
                } else {
                    label.setText (value.toString () + " - " + (new Locale ("", (String)value, "")).getDisplayCountry ());
                }
                return label;
            }
        });
        countryCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                countryComboActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 5);
        getContentPane().add(countryCombo, gridBagConstraints);

        variantCombo.setEditable(true);
        variantCombo.setRenderer(new BasicComboBoxRenderer () {
            public Component getListCellRendererComponent (JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                final JLabel label = (JLabel)super.getListCellRendererComponent (list, value, index, isSelected, cellHasFocus);
                if ("".equals (value.toString ())) {
                    label.setText ("");
                } else {
                    label.setText (value.toString () + " - " + (new Locale ("", "", (String)value)).getDisplayVariant ());
                }
                return label;
            }
        });
        variantCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                variantComboActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 5);
        getContentPane().add(variantCombo, gridBagConstraints);

        okButton.setFont(new java.awt.Font("Dialog", 0, 12));
        org.openide.awt.Mnemonics.setLocalizedText(okButton, java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("Ok"));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(8, 5, 8, 5);
        getContentPane().add(okButton, gridBagConstraints);

        cancelButton.setFont(new java.awt.Font("Dialog", 0, 12));
        org.openide.awt.Mnemonics.setLocalizedText(cancelButton, java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("Cancel"));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(8, 5, 8, 5);
        getContentPane().add(cancelButton, gridBagConstraints);

        helpButton.setFont(new java.awt.Font("Dialog", 0, 12));
        org.openide.awt.Mnemonics.setLocalizedText(helpButton, java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("&Help"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(8, 5, 8, 5);
        getContentPane().add(helpButton, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("New_resulting_locale:"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 5);
        getContentPane().add(jLabel5, gridBagConstraints);

        actualLocaleText.setEditable(false);
        actualLocaleText.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 5);
        getContentPane().add(actualLocaleText, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel6.setLabelFor(variantCombo);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("&Predefined_Locales:"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 5);
        getContentPane().add(jLabel6, gridBagConstraints);

        predefinedList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        predefinedList.setToolTipText(java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("Select_a_predefinned_Locale"));
        predefinedList.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                final JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                final Locale locale = (Locale)value;
                label.setText(locale.toString() + (locale.getLanguage().equals("") ? "" : " - " + locale.getDisplayLanguage()) + (locale.getCountry().equals("") ? "" : " / " + locale.getDisplayCountry()) + (locale.getVariant().equals("") ? "" : " / " + locale.getDisplayVariant()));
                return label;
            }
        });
        predefinedList.setVisibleRowCount(4);
        predefinedList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                predefinedListKeyTyped(evt);
            }
        });
        predefinedList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                predefinedListValueChanged(evt);
            }
        });
        predefinedList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                predefinedListMouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(predefinedList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 5);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        pack();
    }//GEN-END:initComponents

	private void predefinedListKeyTyped (java.awt.event.KeyEvent evt) {//GEN-FIRST:event_predefinedListKeyTyped
		if (evt.getKeyCode ()==KeyEvent.VK_ENTER){
			confirm ();
		}
	}//GEN-LAST:event_predefinedListKeyTyped

	private void predefinedListMouseClicked (java.awt.event.MouseEvent evt) {//GEN-FIRST:event_predefinedListMouseClicked
		if (evt.getClickCount ()>1){
			confirm ();
		}
	}//GEN-LAST:event_predefinedListMouseClicked

	private void cancelButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
		cancel ();
	}//GEN-LAST:event_cancelButtonActionPerformed

	private void okButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
		confirm ();
	}//GEN-LAST:event_okButtonActionPerformed

	private void predefinedListValueChanged (javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_predefinedListValueChanged
		final Locale selectedLocale = (Locale)predefinedList.getSelectedValue();
        languageCombo.setSelectedItem(selectedLocale.getLanguage());
        countryCombo.setSelectedItem(selectedLocale.getCountry());
        variantCombo.setSelectedItem(selectedLocale.getVariant());
        predefinedList.ensureIndexIsVisible(predefinedList.getSelectedIndex());
	}//GEN-LAST:event_predefinedListValueChanged

	private void variantComboActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_variantComboActionPerformed
		comboEventHandler (evt);
	}//GEN-LAST:event_variantComboActionPerformed

	private void languageComboActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_languageComboActionPerformed
		comboEventHandler (evt);
	}//GEN-LAST:event_languageComboActionPerformed

	private void countryComboActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_countryComboActionPerformed
		comboEventHandler (evt);
	}//GEN-LAST:event_countryComboActionPerformed
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField actualLocaleText;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox countryCombo;
    private javax.swing.JButton helpButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox languageCombo;
    private javax.swing.JButton okButton;
    private javax.swing.JList predefinedList;
    private javax.swing.JComboBox variantCombo;
    // End of variables declaration//GEN-END:variables
	
	public void show () {
		reset ();
		super.show ();
	}
	
	private void reset (){
		this.languageCombo.setSelectedItem ("");
		this.countryCombo.setSelectedItem ("");
		this.variantCombo.setSelectedItem ("");
		check ();
	}
	
	
	private void check (){
//		final String key = this.keyField.getText ();
//		this.okButton.setEnabled (key!=null && key.length ()>0);
	}
	
	public String getPersistenceKey () {
		return "addlocaledialog";
	}
	
	public void makePersistent (com.davidecavestro.common.gui.persistence.PersistenceStorage props) {
		PersistenceUtils.makeBoundsPersistent (props, this.getPersistenceKey (), this);
	}
	
	public boolean restorePersistent (com.davidecavestro.common.gui.persistence.PersistenceStorage props) {
		return PersistenceUtils.restorePersistentBounds (props, this.getPersistenceKey (), this);
	}
	
//	private Vector getLanguages (){
//		final List list = new ArrayList ();
//		final String[] codes = Locale.getISOLanguages ();
//		for (int i=0;i<codes.length;i++){
//			list.add (new Locale (codes[i]));
//		}
//		
//		Collections.sort (list, new CountryLocaleComparator ());
//		
//		return new Vector (list);
//	}
//	
//	private Vector getCountries (){
//		final List list = new ArrayList ();
//		final String[] codes = Locale.getISOCountries ();
//		for (int i=0;i<codes.length;i++){
//			list.add (new Locale ("", codes[i]));
//		}
//		
//		Collections.sort (list, new CountryLocaleComparator ());
//		
//		return new Vector (list);
//	}
//	
//	private Vector getVariants (){
//		Vector v = new Vector ();
//		v.add ("EURO");
//		v.add ("USD");
//		return v;
//	}
	
//	private void valueChanged (){
//		okButton.setEnabled (languageCombo.getSelectedItem ()!=null);
//	}
	
//	private void recomputeLocaleDIsplay (){
//		final Locale languageLocale = (Locale)this.languageCombo.getSelectedItem ();
//		this.actualLocaleLabel.setText (languageLocale.toString ());
//	}
	
	
//	class ComboBoxRenderer extends JLabel implements ListCellRenderer {
//		
//		public ComboBoxRenderer () {
//			setOpaque (true);
//			setHorizontalAlignment (CENTER);
//			setVerticalAlignment (CENTER);
//		}
//		
//		/*
//		 * This method finds the image and text corresponding
//		 * to the selected value and returns the label, set up
//		 * to display the text and image.
//		 */
//		public Component getListCellRendererComponent (
//		JList list,
//		Object value,
//		int index,
//		boolean isSelected,
//		boolean cellHasFocus) {
//			
//			if (isSelected) {
//				setBackground (list.getSelectionBackground ());
//				setForeground (list.getSelectionForeground ());
//			} else {
//				setBackground (list.getBackground ());
//				setForeground (list.getForeground ());
//			}
//			
//			String text;
//			if (value instanceof Locale){
//				text = ((Locale)value).getDisplayName ();
//			} else {
//				text = value.toString ();
//			}
//			setText (text);
//			setFont (list.getFont ());
//			
//			return this;
//		}
//		
//	}
	
	class LanguageLocaleComparator implements Comparator {
		
		public int compare (Object o1, Object o2) {
			return ((Locale)o1).getLanguage ().compareTo (((Locale)o2).getLanguage ());
		}
		
	}
	
	class CountryLocaleComparator implements Comparator {
		
		public int compare (Object o1, Object o2) {
			return ((Locale)o1).getCountry ().compareTo (((Locale)o2).getCountry ());
		}
		
	}
	
//	new BasicComboBoxRenderer () {
//		
//		public Component getListCellRendererComponent (JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//			JLabel label = (JLabel)super.getListCellRendererComponent (list, value, index, isSelected, cellHasFocus);			
//			if("".equals (value.toString ()))
//				label.setText ("");
//			else
//				label.setText (value.toString () + " - " + (new Locale ((String)value, "", "")).getDisplayLanguage ());
//			return label;
//		}
//	}
	
	private void comboEventHandler (ActionEvent evt){
		final String str = (String)((JComboBox)evt.getSource ()).getSelectedItem ();
		if (str == null){
			return;
		}
		Locale oldLocale = _locale;
		Object source = evt.getSource ();
		if (source.equals (languageCombo)){
			if (str.equals (_locale.getLanguage ())) {
				return;
			}
			_locale = new Locale (str, _locale.getCountry (), _locale.getVariant ());
		} else if (source.equals (countryCombo)) {
			if (str.equals (_locale.getCountry ()))
				return;
			_locale = new Locale (_locale.getLanguage (), str, _locale.getVariant ());
		} else
			if (source.equals (variantCombo)) {
				if (str.equals (_locale.getVariant ()))
					return;
				_locale = new Locale (_locale.getLanguage (), _locale.getCountry (), str);
			}
		actualLocaleText.setText (_locale.toString ());
		firePropertyChange (PROP_CUSTOMIZED_LOCALE, oldLocale, _locale);
	}
	
	public void addDialogListener (com.davidecavestro.common.gui.dialog.DialogListener l) {
		this._dialogNotifier.addDialogListener (l);
	}	
	
	public void removeDialogListener (com.davidecavestro.common.gui.dialog.DialogListener l) {
		this._dialogNotifier.removeDialogListener (l);
	}
	
	public Locale getSelectedLocale (){
		return this._locale;
	}
	
	
	private void confirm (){
		this._dialogNotifier.fireDialogPerformed (new DialogEvent (this, JOptionPane.OK_OPTION));
		this.hide ();		
	}
	
	private void cancel (){
		this.hide ();
	}
}
