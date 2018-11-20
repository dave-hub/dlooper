package com.davehub.dlooper.gooey;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		this.controlPanel = new ControlPanel(controller, this);
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
		//remove and re-add patterns (deals with pattern removals
		clearPatternPanels();
		if (controller.getNumPatterns() != patternPanels.size()) {
			int currentSize = patternPanels.size();
			int newSize = controller.getNumPatterns();
			for (int i = 0; i<(newSize - currentSize); i++) {
				addPatternPanel(new PatternPanel(currentSize + i, controller, this));
			}
		}
		super.setSize(getPreferredSize());
	}

	/**
	 * Opens a window for loading a sound file, and once a file is selected, displays a confirmation window 
	 * with a button to play the sound they've chosen, and confirm if
	 * @param panel The Component object for which the ui pop-ups display
	 */
	protected static String loadSoundFileWithPreview(JFrame parent) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("samples"));
		do {
			try {
	    	    chooser.setFileFilter(new FileNameExtensionFilter("Audio Files", "wav", "mp3", "m4a"));
	    	    //if file chosen, add new pattern with given file
	    	    if(chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
	    	    	int val = PreviewSoundWindow.previewFile(chooser.getSelectedFile().getAbsolutePath(), parent);
	    	    	if (val == PreviewSoundWindow.CONFIRMED) {
		    	    	return chooser.getSelectedFile().getAbsolutePath();
	    	    	} else if (val == PreviewSoundWindow.CHANGE_FILE) {
	    	    		chooser.setCurrentDirectory(chooser.getSelectedFile().getParentFile());
	    	    		continue;
	    	    	} else if (val == PreviewSoundWindow.CANCELLED) {
	    	    		break;
	    	    	}
	    	    } else {
	    	    	break;
	    	    }
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	    		JOptionPane.showMessageDialog(parent,
					    ex.getMessage(),
					    "File Error",
					    JOptionPane.ERROR_MESSAGE);
	    		break;
	    	}
		} while (true);
		return null;
	}
}
