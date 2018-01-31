package com.davehub.dlooper.loop;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class DrumSound extends Thread {

	/**
	 * The path to the sound file 
	 */
	private String filePath;
	/**
	 * The clip used to play audio streams to the system
	 */
	private Media media;
	/**
	 * Boolean for main thread loop to continue
	 */
	private Boolean running;
	/**
	 * Flag to trigger a play call within the main thread loop
	 */
	private Boolean playFlag;
	
	
	// ------------
	// Constructors
	// ------------
	
	
	/**
	 * Main constructor
	 * @param filePath Path of the audio file which this drum sound plays
	 */
	public DrumSound(String filePath) {
		this.setFilePath(filePath);
		this.running = false;
		this.playFlag = false;
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
	
	private synchronized Media getMedia() {
		synchronized(this) {
			return this.media;
		}
	}

	/**
	 * Sets the filePath to the new filePath, updating the audiostream too
	 * @param filePath
	 */
	public synchronized void setFilePath(String filePath) {
		synchronized(this) {
			this.filePath = filePath;
			this.media = new Media(new File(filePath).toURI().toString());
			this.running = false;
		}
	}
	
	private synchronized boolean getPlayFlag() {
		synchronized(this) {
			return this.playFlag;
		}
	}
	
	// -------
	// Methods
	// -------
	
	
	/**
	 * Sets the playflag to trigger a play event in the main thread.
	 */
	public synchronized void play() {
		System.out.println("play called");
		
	}
	
	private synchronized void setPlayFlag(boolean b) {
		synchronized(this) {
			this.playFlag = true;
		}
	}

	@Override
	public void run() {
		this.running = true;
		while (running) {
			if (getPlayFlag()) {
				MediaPlayer player = new MediaPlayer(getMedia());
				System.out.println("hit");
				player.play();
				setPlayFlag(false);
			}
		}
	}


	
}
