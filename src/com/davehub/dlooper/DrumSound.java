package com.davehub.dlooper;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import sun.applet.Main;

public class DrumSound {

	/**
	 * The path to the sound file 
	 */
	private String filePath;
	/**
	 * The clip used to play audio streams to the system
	 */
	private Clip clip;
	/**
	 * The audio stream from the audio file at filePath
	 */
	private AudioInputStream inputStream;
	
	
	// ------------
	// Constructors
	// ------------
	
	
	/**
	 * Main constructor
	 * @param filePath Path of the audio file which this drum sound plays
	 * @throws UnsupportedAudioFileException When audio file format is unsupported
	 * @throws LineUnavailableException When problems allocating clip
	 * @throws IOException When file specified cannot be read, probably due to incorrect path.
	 */
	public DrumSound(String filePath) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		this.setFilePath(filePath);
		try {
			this.clip = AudioSystem.getClip();
			this.inputStream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream(filePath));
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			throw e;
		}
	}
	
	
	// -------
	// Methods
	// -------
	
	
	/**
	 * Opens the clip audio line.
	 * @throws IOException When inputStream cannot be loaded from the file
	 * @throws LineUnavailableException When clip cannot be opened.
	 */
	public void open() throws IOException, LineUnavailableException {
		try {
			clip.open(inputStream);
		} catch (IOException | LineUnavailableException e) {
			throw e;
		}
	}
	
	/**
	 * Plays the audio file. Note that start() spawns a new thread to play the audio
	 * Clip must be opened first, do so by calling DrumSound.open() for your given instance. 
	 * @return True if the clip is open, and the sound was played. 
	 */
	public boolean play() {
		if (clip.isOpen()) {
			clip.start();
			return true;
		} else return false;
	}

	
	// -------------------
	// Getters and Setters
	// -------------------
	
	
	/**
	 * Returns the path to the file this sound represents as a string
	 * @return The filePath string
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Sets the filePath to the new filePath, updating the audiostream too
	 * @param filePath
	 * @throws IOException When the filePath specified
	 * @throws UnsupportedAudioFileException When the file specified is of an unsupported file type.
	 */
	public void setFilePath(String filePath) throws UnsupportedAudioFileException, IOException {
		this.filePath = filePath;
		this.inputStream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream(filePath));
	}

}
