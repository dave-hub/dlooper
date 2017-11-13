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
	/**
	 * Delay between polling the patterns for beats, equivalent
	 */
	private int pollDelay;
	/**
	 * True if at the end of the pattern, it plays the pattern again immediatly after.
	 */
	private boolean repeat;
	
	
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
		this.repeat = false;
		updatePollDelay();
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
	 * Plays the patterns in the loop, will repeat
	 * @throws InterruptedException
	 */
	public void play() throws InterruptedException {
		do {
			for (int i=0; i<patternLength-1; i++) {
				for (Pattern pattern: patterns) {
					pattern.playPosition(i);
				}
				Thread.sleep(pollDelay);
			}
		} while (repeat);
	}
	
	/**
	 * Adds a pattern to the patterns arraylist
	 * @param pattern The pattern instance to add to patterns
	 * @return True if patterns is changes as a result, this will rarely be false, no need to use the value
	 */
	public boolean addPattern(Pattern pattern) {
		return patterns.add(pattern);
	}
	
	/**
	 * Returns the pattern instance at the given index within the patterns arraylist
	 * @param index The index of the pattern
	 * @return The instance of the pattern at that index
	 */
	public Pattern getPatternAt(int index) {
		return patterns.get(index);
	}
	
	/**
	 * Sets the poll delay to 60,000 / bpm, which produces the value of the length of the beat in milliseconds
	 * E.g. 60,000 / 120 bpm = 600 ms.
	 * Takes the current value of bpm for this.
	 * Is called in setBpm for synchronicity
	 */
	private void updatePollDelay() {
		setPollDelay(60000 / bpm);
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
	 * Also updates the pollDelay value to match the new bpm value.
	 * @param bpm The bpm value, must be > 0
	 * @return Returns false when the 
	 */
	public boolean setBpm(int bpm) {
		if (bpm > 0) {
			this.bpm = bpm;
			updatePollDelay();
			return true;
		} else return false;
	}

	/**
	 * Returns the value of the length of the pattern
	 * @return The patternLength value
	 */
	public int getPatternLength() {
		return patternLength;
	}

	/**
	 * Set the pattern length to the given value
	 * @param patternLength The length you want the patterns to be
	 * @return False if patternLength given <= 0, in which case the patternLength of the object is unchanged.
	 */
	public boolean setPatternLength(int patternLength) {
		if (patternLength > 0) {
			this.patternLength = patternLength;
			for (Pattern pattern: patterns) {
				pattern.setLength(patternLength);
			}
			return true;
		} else return false;
	}
	
	/**
	 * Returns the number of milliseconds between each polling of the pattern to see if it should play a sound.
	 * @return The pollDelay value as an int representing milliseconds
	 */
	public int getPollDelay() {
		return pollDelay;
	}
	
	/**
	 * Sets the delay in milliseconds between polling the pattern to the specified value.
	 * @param pollDelay The value with which to set the pollDelay to.
	 */
	private void setPollDelay(int pollDelay) {
		this.pollDelay = pollDelay;
	}
	
	/**
	 * Sets whether the pattern should repeat itself
	 * @param repeat true if you want 
	 */
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

}
