package com.davehub.dlooper.controller;

import com.davehub.dlooper.loop.DrumSound;
import com.davehub.dlooper.loop.Loop;
import com.davehub.dlooper.loop.Pattern;

/**
 * Acts as a Controller in a View Model Controller design
 * @author dave-hub
 */
public class DLooper implements Controller{
	
	/**
	 * The loop which is currently being edited and played
	 */
	private Loop loop;
	
	
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
	@Override
	public void addPattern(String filePath) {
		loop.addPattern(new Pattern(new DrumSound(filePath)));
	}
	
	/**
	 * Plays each pattern within the loop 
	 */
	@Override
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
	@Override
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
	@Override
	public boolean setPattern(int index, String pattern) {
		return loop.getPatternAt(index).setPattern(pattern);
	}
	
	/**
	 * Sets the pattern length to the specified value. Calls loop.setPatternLength()
	 * @param patternLength
	 * @return False if the length specified is <= 0
	 */
	@Override
	public boolean setPatternLength(int patternLength) {
		return loop.setPatternLength(patternLength);
	}
	
	/**
	 * Sets the pattern sound at the given to the audio file at the given path
	 * Handles exceptions depending on the interface type
	 * @param index
	 * @param filePath
	 */
	@Override
	public void setPatternSound(int index, String filePath) {
		loop.getPatternAt(index).setSoundFilePath(filePath);
	}
	
	/**
	 * Calls setRepeat in loop, indicating whether the patterns should repeat themselves indefinitely.
	 * @param repeat True if you want the loop to repeat.
	 */
	@Override
	public void setRepeat(boolean repeat) {
		loop.setRepeat(repeat);
	}
	
	/**
	 * Returns a list of the pattern strings in the loop.
	 * @return The list of pattern strings
	 */
	@Override
	public String[] getPatterns() {
		return loop.getPatterns();
	}
	
	/**
	 * Returns the number of patterns in the loop
	 * @return The number of patterns in the loop
	 */
	@Override
	public int getNumPatterns() {
		return loop.getNumPatterns();
	}
	
	/**
	 * Returns pattern string for pattern at given index
	 * @param index The index of the pattern to retrieve
	 * @return The pattern string of the pattern at the given index
	 */
	@Override
	public String getPattern(int index) {
		return loop.getPatternAt(index).getPattern();
	}
	
	
	// -----------
	// Get Methods
	// -----------
	
	
	/**
	 * Delegator function to get the pattern length of the loop
	 * @return The pattern length of the loop as an int
	 */
	@Override
	public int getPatternLength() {
		return loop.getPatternLength();
	}
	
	/**
	 * Delegator function to get the BPM of the loop
	 * @return The BPM of the loop as an int
	 */
	@Override
	public int getBpm() {
		return loop.getBpm();
	}
}
