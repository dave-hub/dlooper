package com.davehub.dlooper.gooey;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
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

	private Controller controller;
	private JPanel mainPanel;
	private JButton newButton;
	private JButton loadButton;
	private JButton saveButton;
	private JButton addButton;
	private JButton playButton;
	private JButton stopButton;
	private JPanel settingsPanel;
	private JCheckBox repeatCheckBox;
	private JPanel fieldPanel;
	private JLabel bpmLabel;
	private JTextField bpmField;
	private JLabel patternLengthLabel;
	private JTextField patternLengthField;
	
	public ControlPanel(Controller controller) {
		this.controller = controller;
		
		//swing components
		//main panel
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
		this.settingsPanel = new JPanel();
		this.repeatCheckBox = new JCheckBox("Repeat");
		this.bpmLabel = new JLabel("BPM: ");
		this.bpmField = new JTextField(""+controller.getLoop().getBpm());
		this.patternLengthLabel = new JLabel("Pattern Length: ");
		this.patternLengthField = new JTextField(""+controller.getLoop().getPatternLength());
		this.fieldPanel = new JPanel();
		fieldPanel.setLayout(new GridLayout(2, 2));
		fieldPanel.add(bpmLabel);
		fieldPanel.add(bpmField);
		fieldPanel.add(patternLengthLabel);
		fieldPanel.add(patternLengthField);
		settingsPanel.add(repeatCheckBox);
		settingsPanel.add(fieldPanel);
		
		super.add(mainPanel, BorderLayout.WEST);
		super.add(settingsPanel, BorderLayout.EAST);
		
		setupListeners();
	}
	
	public void setupListeners() {
		//new loop
		newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Object[] options = { "OK", "CANCEL" };
            	JOptionPane prompt = new JOptionPane(
            		(Object) "Overwrite current loop?",
            		JOptionPane.DEFAULT_OPTION,
            		JOptionPane.WARNING_MESSAGE,
            		null, options, options[0]
            	);
            	Object val = prompt.getValue();
            	if (val == options[0]) {
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
            		JFileChooser chooser = new JFileChooser();
            		chooser.setCurrentDirectory(new File("samples"));
            	    chooser.setFileFilter(new FileNameExtensionFilter("DLooper files", "dlf"));
            	    if(chooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
            	    	controller.loadFromFile(chooser.getSelectedFile().getPath());;
            	    	DLooperWindow window = (DLooperWindow) SwingUtilities.getWindowAncestor(mainPanel);
            	    	window.clearPatternPanels();
            	    	window.refresh();
            	    }
            	} catch (Exception ex) {
            		ex.printStackTrace();
            	}
            }
        });
		//save loop to file
		saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		JFileChooser chooser = new JFileChooser();
            		chooser.setCurrentDirectory(new File("samples"));
            	    chooser.setFileFilter(new FileNameExtensionFilter("DLooper files", "dlf"));
            	    if(chooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
            	    	controller.saveToFile(chooser.getSelectedFile().getPath());
            	    }
            	} catch (Exception ex) {
            		ex.printStackTrace();
            	}
            }
        });
		//add new pattern
		addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		JFileChooser chooser = new JFileChooser();
            		chooser.setCurrentDirectory(new File("samples"));
            	    chooser.setFileFilter(new FileNameExtensionFilter("Audio Files", "wav", "mp3", "m4a"));
            	    if(chooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
            	    	controller.addPattern(chooser.getSelectedFile().getPath());
            	    	DLooperWindow window = (DLooperWindow) SwingUtilities.getWindowAncestor(mainPanel);
            	    	window.refresh();
            	    }
            	} catch (Exception ex) {
            		ex.printStackTrace();
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
	        	changeBpm(""+controller.getBpm());
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
	            changePatternLength(""+controller.getPatternLength());
	        }
	    });
	}
	
	public void refresh() {
		bpmField.setText(controller.getBpm()+"");
		patternLengthField.setText(controller.getPatternLength()+"");
	}
	
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
