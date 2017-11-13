package com.davehub.dlooper;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Acts as a Controller in a View Model Controller design
 * @author dave-hub
 */
public class DLooper implements Controller{
	
	/**
	 * The loop which is currently being edited and played
	 */
	public Loop loop;
	
	
	// -----------
	// Constructor
	// -----------
	
	
	/**
	 * The constructor 
	 */
	public DLooper() {
		this.loop = new Loop();
	}
	
	
	// -------
	// Methods
	// -------
	
	
	/**
	 * Adds a pattern to the loop with the given length and audio file specified by path.
	 * @param patternLength The length of the pattern
	 * @param filePath The path to the audio file to load
	 */
	public void addPattern(String filePath) {
		try {
			loop.addPattern(new Pattern(loop.getPatternLength(), new DrumSound(filePath)));
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Plays each pattern within the loop 
	 */
	public void play() {
		try {
			loop.play();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the loop bpm to the given value
	 * @param bpm The desired bpm value as an int
	 * @return False if the bpm <= 0, in which case the bpm value is unchanged
	 */
	public boolean setBpm(int bpm) {
		return loop.setBpm(bpm);
	}

	 /**
	  * Attempts to set the pattern at the given index to the given string
	  * @see com.davehub.dlooper.Pattern.setPattern
	  * @param index The index of the pattern within the loop's pattern array 
	  * @param pattern The pattern which to attempt to use
	  * @return True if the pattern was valid and the pattern has been changed.
	  */
	public boolean setPattern(int index, String pattern) {
		return loop.getPatternAt(index).setPattern(pattern);
	}
	
	/**
	 * Sets the pattern length to the specified value. Calls loop.setPatternLength()
	 * @param patternLength
	 * @return False if the length specified is <= 0
	 */
	public boolean setPatternLength(int patternLength) {
		return loop.setPatternLength(patternLength);
	}
	
	/**
	 * Sets the pattern sound at the given to the audio file at the given path
	 * Handles exceptions depending on the interface type
	 * @param index
	 * @param filePath
	 */
	public void setPatternSound(int index, String filePath) {
		try {
			loop.getPatternAt(index).setSoundFilePath(filePath);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Calls setRepeat in loop, indicating whether the patterns should repeat themselves indefinitely.
	 * @param repeat True if you want the loop to repeat.
	 */
	public void setRepeat(boolean repeat) {
		loop.setRepeat(repeat);
	}
	
	
	// -----------
	// Main Method
	// -----------
	
	
	/**
	 * Main method for running the program
	 * @param args
	 */
	public static void main(String[] args) {

	}
}
