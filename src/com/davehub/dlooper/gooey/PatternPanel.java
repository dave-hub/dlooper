package com.davehub.dlooper.gooey;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.davehub.dlooper.Controller;

@SuppressWarnings("serial")
public class PatternPanel extends JPanel {

	/**
	 * The identifying number of the pattern, also the index within the Loop pattern array
	 */
	private int id;
	/**
	 * The Controller for the GUI
	 */
	private Controller controller;
	/**
	 * Root window this panel is contained in
	 */
	private DLooperWindow window;
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
	/**
	 * The slider fot the volume of this pattern
	 */
	private JSlider volumeSlider;
	/**
	 * The checkbox to mute this pattern
	 */
	private JCheckBox muteCheckBox;
	/**
	 * The Button for removing this pattern from the loop
	 */
	private JButton removeButton;
	/**
	 * The Button for changin this patterns sound file
	 */
	private JButton changeSoundButton;
	
	
	// -----------
	// Constructor
	// -----------
	
	
	/**
	 * Creates a new PatternPanel for the pattern with the given id in the Controller's Loop
	 * @param id The id of the Pattern within the Loop
	 * @param controller The Controller for this UI
	 */
	public PatternPanel(int id, Controller controller, DLooperWindow window) {
		this.id = id;
		this.controller = controller;
		this.window = window;
		
		//swing components
		this.idLabel = new JLabel(id + ")");
		this.middlePanel = new JPanel();
		this.audioLabel = new JLabel(controller.getPattern(id).getSound().getFilePath());
		this.patternField = new JTextField(controller.getPattern(id).getPattern());
		this.volumeSlider = new JSlider(0, 100);
		this.muteCheckBox = new JCheckBox("Mute");
		this.removeButton = new JButton("X");
		this.changeSoundButton = new JButton("File");
		
		//change patternField font to monospace for clarity when editing
		Font monofont = new Font(Font.MONOSPACED, Font.PLAIN, 18);
		patternField.setFont(monofont);
		
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
		c = new GridBagConstraints();
		c.gridx = 2;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		add(muteCheckBox, c);
		c = new GridBagConstraints();
		c.gridx = 3;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		add(volumeSlider, c);
		c = new GridBagConstraints();
		c.gridx = 4;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		add(changeSoundButton, c);
		c = new GridBagConstraints();
		c.gridx = 5;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		add(removeButton, c);
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
		//change on loss of focus
		patternField.addFocusListener(new FocusListener() {
	        public void focusLost(FocusEvent e) {
	        	changePattern(patternField.getText());
	        }
			public void focusGained(FocusEvent arg0) {}
	    });
		//change sound on button click
		changeSoundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		String path = DLooperWindow.loadSoundFileWithPreview(window);
                	if (path != null) {
                		controller.setPatternSound(id, path);
                		window.refresh();
                	}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(window,
        				    "Unable to add pattern.\n" +
        				    ex.getMessage(),
        				    "Add Error",
        				    JOptionPane.ERROR_MESSAGE);
				}
            }
        });
		//remove pattern on button click
		removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	controller.removePattern(id);
            	window.refresh();
            }
		});
		//change mute value on box checking or unchecking
		muteCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				controller.setPatternMute(id, ((JCheckBox) e.getSource()).isSelected());
			}
		});
		//change volume on slider change
		volumeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				controller.setPatternVolume(id, (((JSlider) e.getSource()).getValue() / 100f));
			}
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
		audioLabel.setText(controller.getPattern(id).getSound().getFilePath());
		patternField.setText(controller.getPattern(id).getPattern());
	}
}
