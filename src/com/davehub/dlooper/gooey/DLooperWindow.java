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
	
	private ControlPanel controlPanel;
	private JPanel contentPanel;
	private ArrayList<PatternPanel> patternPanels;
	private Controller controller;
	
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
	
	private void addPatternPanel(PatternPanel panel) {
		patternPanels.add(panel);
		contentPanel.add(panel);
	}
	
	public void clearPatternPanels() {
		contentPanel.removeAll();
		patternPanels.clear();
	}
	
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
