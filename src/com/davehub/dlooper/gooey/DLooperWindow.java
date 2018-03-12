package com.davehub.dlooper.gooey;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.davehub.dlooper.Controller;

@SuppressWarnings("serial")
public class DLooperWindow extends JFrame {
	
	/**
	 * ControlPanel for the window
	 */
	private ControlPanel controlPanel;
	/**
	 * Panel to store all PatternPanels
	 */
	private JPanel contentPanel;
	/**
	 * List of PatternPanels
	 */
	private ArrayList<PatternPanel> patternPanels;
	/**
	 * The Controller this ui uses
	 */
	private Controller controller;
	
	
	// -----------
	// Constructor
	// -----------
	
	
	/**
	 * Creates a new DLooperWindow that uses the given Controller
	 * @param controller
	 */
	public DLooperWindow(Controller controller) {
		super("DLooper");
		this.controller = controller;
		this.controlPanel = new ControlPanel(controller);
		this.contentPanel = new JPanel(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		this.patternPanels = new ArrayList<PatternPanel>();
		
		contentPanel.setLayout(new GridLayout(0, 1));
		contentPanel.setBorder(new EmptyBorder(10,10,10,10));
		
		getContentPane().add(controlPanel, BorderLayout.PAGE_START);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		pack();
		setVisible(true);
	}
	
	
	// -------
	// Methods
	// -------
	
	
	/**
	 * Adds a new PatternPanel to the UI
	 * @param panel The PatternPanel to add
	 */
	private void addPatternPanel(PatternPanel panel) {
		patternPanels.add(panel);
		contentPanel.add(panel);
	}
	
	/**
	 * Clears all the PatternPanels from the UI
	 */
	public void clearPatternPanels() {
		contentPanel.removeAll();
		patternPanels.clear();
	}
	
	/**
	 * Refreshes all child components.
	 */
	public void refresh() {
		controlPanel.refresh();
		//refresh existing patterns
		for (PatternPanel panel: patternPanels) {
			panel.refresh();
		}
		//add new patterns
		if (controller.getNumPatterns() != patternPanels.size()) {
			int currentSize = patternPanels.size();
			int newSize = controller.getNumPatterns();
			for (int i = 0; i<(newSize - currentSize); i++) {
				addPatternPanel(new PatternPanel(currentSize + i, controller));
			}
		}
		super.setSize(getPreferredSize());
	}

}
