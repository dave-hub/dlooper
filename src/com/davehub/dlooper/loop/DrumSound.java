package com.davehub.dlooper.loop;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class DrumSound extends Thread {

	/**
	 * The path to the sound file 
	 */
	private String filePath;
	/**
	 * The Media object representing the sound
	 */
	private Media media;
	/**
	 * The MediaPlayer object that plays the media
	 */
	private MediaPlayer player;
	
	
	// ------------
	// Constructors
	// ------------
	
	
	/**
	 * Main constructor
	 * @param filePath Path of the audio file which this drum sound plays
	 * @throws Exception 
	 */
	public DrumSound(String filePath) throws Exception {
		this.setFilePath(filePath);
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
	 * Returns the Media instance this DrumSound uses
	 * @return the Media instance this sound represents
	 */
	public synchronized Media getMedia() {
		synchronized(this) {
			return this.media;
		}
	}
	
	/**
	 * Returns the MediaPlayer instance that plays this sound
	 * If i'm honest im not sure if i even need the synchronized stuff here
	 * @return see above
	 */
	public synchronized MediaPlayer getPlayer() {
		synchronized(this) {
			return this.player;
		}
	}

	/**
	 * Sets the filePath to the new filePath, updating the audiostream too
	 * @param filePath
	 */
	public void setFilePath(String filePath) throws Exception {
		try {
			this.filePath = filePath;
			this.media = new Media(new File(filePath).toURI().toString());
			this.player = new MediaPlayer(media);
		} catch (Exception e) {
			throw new Exception("Unable to read file: " + filePath);
		}
	}

	/**
	 * Plays this DrumSound's audio
	 */
	public void play() {
		System.out.println("play " + filePath);
		player.seek(Duration.ZERO);
		player.play();
	}
	
	/**
	 * Stops the DrumSound's audio
	 */
	public void stop_playing() {
		player.stop();
	}
	
}
