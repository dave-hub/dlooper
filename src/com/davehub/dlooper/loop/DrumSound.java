package com.davehub.dlooper.loop;

import java.io.File;

import javax.sound.midi.SysexMessage;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class DrumSound extends Thread {

	/**
	 * The path to the sound file 
	 */
	private String filePath;
	/**
	 * The clip used to play audio streams to the system
	 */
	private Media media;
	
	
	// ------------
	// Constructors
	// ------------
	
	
	/**
	 * Main constructor
	 * @param filePath Path of the audio file which this drum sound plays
	 */
	public DrumSound(String filePath) {
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
	 * Sets the filePath to the new filePath, updating the audiostream too
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		try {
			this.filePath = filePath;
			this.media = new Media(new File(filePath).toURI().toString());
		} catch (Exception e) {
			System.err.println("ERROR: Unable to read file (" + filePath + ")");
		}
	}


	public void play() {
		new MediaPlayer(media).play();
	}
	
}
