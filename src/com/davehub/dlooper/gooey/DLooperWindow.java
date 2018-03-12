package com.davehub.dlooper.gooey;

import java.awt.BorderLayout;
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
		this.controller = controller;
		this.controlPanel = new ControlPanel(controller);
		this.contentPanel = new JPanel(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		this.patternPanels = new ArrayList<PatternPanel>();
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBorder(new EmptyBorder(10,10,10,10));
		
		super.getContentPane().add(controlPanel, BorderLayout.PAGE_START);
		super.getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		super.pack();
		super.setVisible(true);
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
			for (int i = 0; i<(controller.getNumPatterns() - patternPanels.size()); i++) {
				addPatternPanel(new PatternPanel(patternPanels.size() + i, controller));
			}
		}
		super.setSize(getPreferredSize());
	}

}
