package com.davehub.dlooper.gooey;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.davehub.dlooper.Controller;

@SuppressWarnings("serial")
public class PatternPanel extends JPanel {

	/**
	 * The identifying number of the pattern, also the index within the Loop pattern array
	 */
	private int id;
	/**
	 * The Pattern this Panel represents
	 */
	private Controller controller;
	/**
	 * The label for displaying the id
	 */
	private JLabel idLabel;
	/**
	 * The panel for the middle section containing audioLabel and patternField
	 */
	private JPanel middlePanel;
	/**
	 * The label that displays the file path of the DrumSound in the Pattern
	 */
	private JLabel audioLabel;
	/**
	 * The field at which the pattern string can be edited
	 */
	private JTextField patternField;
	
	
	// -----------
	// Constructor
	// -----------
	
	
	/**
	 * Creates a new PatternPanel for the pattern with the given id in the Controller's Loop
	 * @param id The id of the Pattern within the Loop
	 * @param controller The Controller for this UI
	 */
	public PatternPanel(int id, Controller controller) {
		this.id = id;
		this.controller = controller;
		
		//swing components
		this.idLabel = new JLabel(id + ")");
		this.middlePanel = new JPanel();
		this.audioLabel = new JLabel(controller.getPattern(id).getSound().getFilePath());
		this.patternField = new JTextField(controller.getPattern(id).getPattern());
		
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		middlePanel.add(audioLabel);
		middlePanel.add(patternField);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(idLabel, c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(middlePanel, c);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		setupListeners();
	}
	
	
	// ---------
	// Listeners
	// ---------
	
	
	/**
	 * Setup listeners for components
	 */
	private void setupListeners() {
		//pattern change on enter press
		patternField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
				changePattern(patternField.getText());
            }
        });
		//undo change on loss of focus
		patternField.addFocusListener(new FocusListener() {
	        public void focusLost(FocusEvent e) {
	        	patternField.setText(controller.getPatternString(id));
	        }
			public void focusGained(FocusEvent arg0) {}
	    });
	}

	
	// -------
	// Methods
	// -------
	
	/**
	 * Attempts to change the pattern to the given string and reports errors
	 * @param pattern The pattern string to change to
	 */
	public void changePattern(String pattern) {
		if (patternField.getText().length() != controller.getPatternLength()) {
			JOptionPane.showMessageDialog(this,
				    "Pattern not the correct length: " + pattern.length() + ", should be: " + controller.getPatternLength(),
				    "Bad Pattern",
				    JOptionPane.ERROR_MESSAGE);
			patternField.setText(controller.getPatternString(id));
			patternField.requestFocusInWindow();
		} else if (!controller.setPattern(id, pattern)) { //attempt pattern change, catch fail
			JOptionPane.showMessageDialog(this,
				    "Pattern contains invalid characters",
				    "Bad Pattern",
				    JOptionPane.ERROR_MESSAGE);
			patternField.setText(controller.getPatternString(id));
			patternField.requestFocusInWindow();
		}
	}
	
	/**
	 * Refreshes this component with the Controller
	 */
	public void refresh() {
		System.out.println("Pattern refresh");
		audioLabel.setText(controller.getPattern(id).getSound().getFilePath());
		patternField.setText(controller.getPattern(id).getPattern());
	}
}
