package com.davehub.dlooper.gooey;

import java.awt.BorderLayout;
import java.awt.Color;
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
		
		super.add(idLabel, BorderLayout.PAGE_START);
		super.add(middlePanel, BorderLayout.CENTER);
		super.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		setupListeners();
	}
	
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
	
	public void refresh() {
		audioLabel.setText(controller.getPattern(id).getSound().getFilePath());
		patternField.setText(controller.getPattern(id).getPattern());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
