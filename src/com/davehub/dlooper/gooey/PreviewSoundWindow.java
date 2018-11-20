package com.davehub.dlooper.gooey;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.davehub.dlooper.loop.DrumSound;

@SuppressWarnings("serial")
public class PreviewSoundWindow extends JDialog {

	/**
	 * Constant for user confirming a file
	 */
	public static final int CONFIRMED = 1;
	/**
	 * Constant for user requesting to change file
	 */
	public static final int CHANGE_FILE = 0;
	/**
	 * Constant for user cancelling the action
	 */
	public static final int CANCELLED = -1;
	/**
	 * Constant for when user has not yet selected an option
	 */
	public static final int WAITING = -2;
	/**
	 * The panel that holds all the Buttons and the text
	 */
	protected JPanel mainPanel;
	/**
	 * The panel that holds the file label and the play button
	 */
	private JPanel soundPanel;
	/**
	 * The panel that holds the ok, change, and cancel buttons
	 */
	private JPanel confirmPanel;
	/**
	 * Button to play the sound
	 */
	private JButton playButton;
	/**
	 * Button to confirm the current sound selection
	 */
	private JButton okButton;
	/**
	 * Button to change the current sound selection
	 */
	private JButton changeButton;
	/**
	 * Button to cancel a file selection
	 */
	private JButton cancelButton;
	/**
	 * Label for which file is currently loaded
	 */
	private JLabel fileLabel;
	/**
	 * path of the current sound file
	 */
	@SuppressWarnings("unused")
	private String filePath;
	/**
	 * The current sound object
	 */
	private DrumSound sound;
	/**
	 * Value returned
	 */
	protected int retval;
	
	/**
	 * Creates a window for previewing files
	 * @throws Exception 
	 */
	public PreviewSoundWindow(String filePath, JFrame parentFrame) throws Exception {
		super(parentFrame, "Preview Sound File");
		this.filePath = filePath;
		
		//check if sound can actually be loaded before creating 
		this.sound = new DrumSound(filePath);
		this.mainPanel = new JPanel();
		this.soundPanel = new JPanel();
		this.confirmPanel = new JPanel();
		this.playButton = new JButton("Play");
		this.okButton = new JButton("OK");
		this.changeButton = new JButton("Change");
		this.cancelButton = new JButton("Cancel");
		this.fileLabel = new JLabel(filePath);
		this.sound = new DrumSound(filePath);
		
		soundPanel.add(playButton, BorderLayout.BEFORE_LINE_BEGINS);
		soundPanel.add(fileLabel, BorderLayout.CENTER);
		
		confirmPanel.add(okButton);
		confirmPanel.add(changeButton);
		confirmPanel.add(cancelButton);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(soundPanel);
		mainPanel.add(confirmPanel);
		
		super.setContentPane(mainPanel);
		
		retval = WAITING;
		
		setupListeners();
		
		pack();
		setModal(true);
	}
	
	private void setupListeners() {
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sound.play();
			}
		});
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retval = CONFIRMED;
				setVisible(false);
			}
		});
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retval = CHANGE_FILE;
				setVisible(false);
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retval = CANCELLED;
				setVisible(false);
			}
		});
	}
	
	public static int previewFile(String filePath, JFrame parentFrame) throws Exception {
		PreviewSoundWindow window = new PreviewSoundWindow(filePath, parentFrame);
		window.setVisible(true);
		return window.retval;
	}
}
