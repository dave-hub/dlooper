package com.davehub.dlooper.gooey;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.davehub.dlooper.Controller;
import com.davehub.dlooper.DLooper;
import com.davehub.dlooper.loop.Loop;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

	/**
	 * The Controller this UI uses
	 */
	private Controller controller;
	/**
	 * The panel that holds all the Buttons
	 */
	private JPanel mainPanel;
	/**
	 * Button to create a new Loop
	 */
	private JButton newButton;
	/**
	 * Button to load loop from file
	 */
	private JButton loadButton;
	/**
	 * Button to save loop to file
	 */
	private JButton saveButton;
	/**
	 * Button to add a new Pattern to the Loop
	 */
	private JButton addButton;
	/**
	 * Button to play the Loop
	 */
	private JButton playButton;
	/**
	 * Button to stop playing the Loop
	 */
	private JButton stopButton;
	/**
	 * The Panel that stores the Loop settings
	 */
	private JPanel settingsPanel;
	/**
	 * The Label for the repeatCheckBox
	 */
	private JLabel repeatLabel;
	/**
	 * Checkbox for repeating Loop playback
	 */
	private JCheckBox repeatCheckBox;
	/**
	 * The Label for the BPM control
	 */
	private JLabel bpmLabel;
	/**
	 * The BPM input field
	 */
	private JTextField bpmField;
	/**
	 * The Label for patternLength control
	 */
	private JLabel patternLengthLabel;
	/**
	 * The patternLength input field
	 */
	private JTextField patternLengthField;
	/**
	 * Parent JFrame
	 */
	private JFrame parentFrame;
	
	
	// -----------
	// Constructor
	// -----------
	
	
	/**
	 * Creates a new ControlPanel that uses the given Controller 
	 * @param controller The Controller for this UI to use
	 */
	public ControlPanel(Controller controller, JFrame parentFrame) {
		this.controller = controller;
		
		//swing components
		//main panel
		this.parentFrame = parentFrame;
		this.mainPanel = new JPanel();
		this.newButton = new JButton("New");
		this.loadButton = new JButton("Load");
		this.saveButton = new JButton("Save");
		this.addButton = new JButton("Add");
		this.playButton = new JButton("Play");
		this.stopButton = new JButton("Stop");
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.add(newButton);
		mainPanel.add(loadButton);
		mainPanel.add(saveButton);
		mainPanel.add(addButton);
		mainPanel.add(playButton);
		mainPanel.add(stopButton);
		
		//settings panel
		this.settingsPanel = new JPanel(new FlowLayout());
		this.repeatLabel = new JLabel("Repeat:");
		this.repeatCheckBox = new JCheckBox();
		this.bpmLabel = new JLabel("  BPM:");
		this.bpmField = new JTextField(""+controller.getLoop().getBpm());
		this.patternLengthLabel = new JLabel("  Pattern Length:");
		this.patternLengthField = new JTextField(""+controller.getLoop().getPatternLength());
		settingsPanel.add(repeatLabel);
		settingsPanel.add(repeatCheckBox);
		settingsPanel.add(bpmLabel);
		settingsPanel.add(bpmField);
		settingsPanel.add(patternLengthLabel);
		settingsPanel.add(patternLengthField);
		
		//combine panels
		super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		super.add(mainPanel);
		super.add(settingsPanel);
		
		setupListeners();
	}
	
	
	// ---------
	// Listeners
	// ---------
	
	
	/**
	 * Sets up the listeners for each Component
	 */
	private void setupListeners() {
		//new loop
		newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int val = JOptionPane.showConfirmDialog(mainPanel, 
            			"This will overwrite current loop, continue?",
            			"Warning",
            			JOptionPane.OK_CANCEL_OPTION);
            	//if ok selected, create new loop and refresh UI
            	if (val == 0) {
            		controller.setLoop(new Loop());
            		DLooperWindow window = (DLooperWindow) SwingUtilities.getWindowAncestor(mainPanel);
        	    	window.clearPatternPanels();
        	    	window.refresh();
            	}
            }
        });
		//load loop from file
		loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		int val = JOptionPane.showConfirmDialog(mainPanel, 
                			"This will overwrite current loop, continue?",
                			"Warning",
                			JOptionPane.OK_CANCEL_OPTION);
                	//if ok selected, create new loop and refresh UI
                	if (val == 0) {
	            		JFileChooser chooser = new JFileChooser();
	            		chooser.setCurrentDirectory(new File("samples"));
	            	    chooser.setFileFilter(new FileNameExtensionFilter("DLooper files", "dlf"));
	            	    //if file is approved, attempt to load it.
	            	    if(chooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
	            	    	controller.loadFromFile(chooser.getSelectedFile().getPath());
	            	    	//refresh UI
	            	    	DLooperWindow window = (DLooperWindow) SwingUtilities.getWindowAncestor(mainPanel);
	            	    	window.clearPatternPanels();
	            	    	window.refresh();
	            	    }
                	}
            	} catch (Exception ex) {
            		JOptionPane.showMessageDialog(mainPanel,
        				    "Errors exist in the given file:\n" +
        				    ex.getMessage(),
        				    "Bad File",
        				    JOptionPane.ERROR_MESSAGE);
            	}
            }
        });
		//save loop to file
		saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		JFileChooser chooser = new JFileChooser();
            		chooser.setCurrentDirectory(new File("samples"));
            		chooser.setSelectedFile(new File("untitled.dlf"));
            	    chooser.setFileFilter(new FileNameExtensionFilter("DLooper files", "dlf"));
            	    //if file name chosen, save to file
            	    if(chooser.showSaveDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
            	    	controller.saveToFile(chooser.getSelectedFile().getPath());
            	    }
            	} catch (Exception ex) {
            		JOptionPane.showMessageDialog(mainPanel,
        				    "Unable to save to file.\n" +
        				    ex.getMessage(),
        				    "Save Error",
        				    JOptionPane.ERROR_MESSAGE);
            	}
            }
        });
		//add new pattern
		addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String path = DLooperWindow.loadSoundFileWithPreview(parentFrame);
            	if (path != null) {
            		try {
						controller.addPattern(path);
						DLooperWindow window = (DLooperWindow) SwingUtilities.getWindowAncestor(mainPanel);
		    	    	window.refresh();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(parentFrame,
	        				    "Unable to add pattern.\n" +
	        				    ex.getMessage(),
	        				    "Add Error",
	        				    JOptionPane.ERROR_MESSAGE);
					}
            	}
            }
        });
		//play loop
		playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.play();
            }
        });
		//stop playing loop
		stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.stop();
            }
        });
		//toggle repeat
		repeatCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				controller.setRepeat(repeatCheckBox.isSelected());
			}     
		});
		//change bpm
		bpmField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
				changeBpm(bpmField.getText());
            }
        });
		//undo change on loss of focus
		bpmField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent arg0) {}
	        public void focusLost(FocusEvent e) {
	        	changeBpm(bpmField.getText());
	        }
	    });
		//change pattern length
		patternLengthField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
				changePatternLength(patternLengthField.getText());
				DLooperWindow window = (DLooperWindow) SwingUtilities.getWindowAncestor(mainPanel);
    	    	window.refresh();
            }
        });
		//undo change on loss of focus
		patternLengthField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent arg0) {}
	        public void focusLost(FocusEvent e) {
	            changePatternLength(patternLengthField.getText());
	        }
	    });
	}
	
	
	// -------
	// Methods
	// -------
	
	
	/**
	 * Refreshes this objects components with Controller
	 */
	public void refresh() {
		bpmField.setText(controller.getBpm()+"");
		patternLengthField.setText(controller.getPatternLength()+"");
	}
	
	/**
	 * Changes the patternLength to the integer value of the given string and reports errors if failed.
	 * @param patternLength The String representing the new patternLength.
	 */
	private void changePatternLength(String patternLength) {
		if (!DLooper.isNumeric(patternLength)) {
			JOptionPane.showMessageDialog(this,
				    "Pattern length must be numeric",
				    "Bad Pattern",
				    JOptionPane.ERROR_MESSAGE);
			patternLengthField.setText(controller.getPatternLength()+"");
			patternLengthField.requestFocusInWindow();
		} else {
			controller.setPatternLength(Integer.parseInt(patternLength));
		}
	}
	
	/**
	 * Changes the BPM to the integer value of the given string and reports errors if failed.
	 * @param BPM The String representing the new BPM.
	 */
	private void changeBpm(String bpm) {
		if (!DLooper.isNumeric(bpm)) {
			JOptionPane.showMessageDialog(this,
				    "BPM must be numeric",
				    "Bad Pattern",
				    JOptionPane.ERROR_MESSAGE);
			bpmField.setText(controller.getBpm()+"");
			bpmField.requestFocusInWindow();
		} else {
			controller.setBpm(Integer.parseInt(bpm));
		}
	}
}
