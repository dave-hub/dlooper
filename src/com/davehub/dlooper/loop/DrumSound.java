package com.davehub.dlooper.loop;

import java.io.File;
import java.io.IOException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class DrumSound {

	/**
	 * The path to the sound file 
	 */
	private String filePath;
	/**
	 * The clip used to play audio streams to the system
	 */
	private Media media;
	/**
	 * MediaPlayer instance for playing the media
	 */
	private MediaPlayer player;
	
	
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
	public DrumSound(String filePath) {
		this.setFilePath(filePath);
	}
	
	
	// -------
	// Methods
	// -------
	
	
	/**
	 * Plays the audio file. Note that start() spawns a new thread to play the audio
	 * Clip must be opened first, do so by calling DrumSound.open() for your given instance. 
	 */
	public void play() {
		player.play();
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
	public void setFilePath(String filePath) {
		this.filePath = filePath;
		this.media = new Media(new File(filePath).toURI().toString());
		this.player = new MediaPlayer(media);
	}

}
