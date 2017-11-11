package com.davehub.dlooper;

import java.util.ArrayList;

/**
 * Represents a playable drum loop through layers of patterns that play sounds to mimic playing the drums
 */
public class Loop {
	
	/**
	 * The default bpm set when starting a new loop in any user interface
	 */
	private static final int DEFAULT_BPM = 120;
	/**
	 * The default pattern length when starting a new loop in any interface
	 */
	private static final int DEFAULT_PATTERN_LENGTH = 32;
	/**
	 * Value of the beats per minute at which the loop will play.
	 */
	private int bpm;
	/**
	 * Length of the patterns in notes, so a patternLength of 30 means you can play a sound 30 times within the loop.
	 */
	private int patternLength;
	/**
	 * The collection of patterns us
	 */
	private ArrayList<Pattern> patterns;
	
	
	// ------------
	// Constructors
	// ------------
	
	
	/**
	 * Full attribute constructor
	 * @param bpm The beats per minute at which the loop will play
	 * @param patternLength The maximum length that a pattern can be
	 */
	private Loop(int bpm, int patternLength) {
		this.bpm = bpm;
		this.patternLength = patternLength;
		this.patterns = new ArrayList<Pattern>();
	}
	
	/**
	 * Main constructor used by any user interface
	 */
	public Loop() {
		this(DEFAULT_BPM, DEFAULT_PATTERN_LENGTH);
	}
	
	
	// -------
	// Methods
	// -------
	
	
	/**
	 * Adds a pattern to the patterns arraylist
	 * @param pattern The pattern instance to add to patterns
	 * @return True if patterns is changes as a result, this will rarely be false, no need to use the value
	 */
	private boolean addPattern(Pattern pattern) {
		return patterns.add(pattern);
	}
	
	/**
	 * Adds a new default pattern to the pattern list.
	 * @return boolean from addPattern
	 */
	public boolean newPattern() {
		return addPattern(new Pattern(getPatternLength()));
	}
	
	/**
	 * Returns the pattern instance at the given index within the patterns arraylist
	 * @param index The index of the pattern
	 * @return The instance of the pattern at that index
	 */
	public Pattern getPatternAt(int index) {
		return patterns.get(index);
	}
	
	
	// -------------------
	// Getters and Setters
	// -------------------
	
	
	/**
	 * Returns the value of the current bpm
	 * @return The bpm value as an int
	 */
	public int getBpm() {
		return bpm;
	}

	
	/**
	 * Sets the beats per minute at which the loop will play.
	 * @param bpm The bpm value, must be > 0
	 * @return Returns false when the 
	 */
	public boolean setBpm(int bpm) {
		if (bpm > 0) {
			this.bpm = bpm;
			return true;
		} else return false;
	}

	/**
	 * Returns the value of the length of the pattern
	 * @return
	 */
	public int getPatternLength() {
		return patternLength;
	}

	/**
	 * Set the pattern length to the given value
	 * @param patternLength The length you want the patterns to be
	 */
	public void setPatternLength(int patternLength) {
		this.patternLength = patternLength;
	}

}
