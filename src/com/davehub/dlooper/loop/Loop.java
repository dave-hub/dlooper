package com.davehub.dlooper.loop;

import java.util.ArrayList;
import java.util.Collection;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

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
	 * The collection of patterns us
	 */
	private ArrayList<Pattern> patterns;
	/**
	 * The timer for playing the loop at a given bpm
	 */
	private Timeline timer;
	/**
	 * Value of the beats per minute at which the loop will play.
	 */
	private int bpm;
	/**
	 * Length of the patterns in notes, so a patternLength of 30 means you can play a sound 30 times within the loop.
	 */
	private int patternLength;
	/**
	 * Delay between polling the patterns for beats, equivalent
	 */
	private int pollDelay;
	/**
	 * The current beat during playback
	 */
	private int currentBeat;
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
	public Loop(int bpm, int patternLength) {
		this.bpm = bpm;
		this.patternLength = patternLength;
		this.timer = new Timeline();
		this.patterns = new ArrayList<Pattern>();
		this.repeat = false;
		this.currentBeat = 0;
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
	 * Starts a timer to play
	 */
	public synchronized void play() {
		if (timer.getStatus() != Animation.Status.RUNNING) {
			currentBeat = 0;
			this.timer = new Timeline(new KeyFrame(
			    Duration.millis(pollDelay),
			    ae ->{playBeat(nextBeat());
			}));
			if (repeat) {
				timer.setCycleCount(Animation.INDEFINITE);
			} else {
				timer.setCycleCount(patternLength);
			}
			timer.play();
		}
	}
	
	/**
	 * Returns the next beat, increasing the value of the current beat so the next call will be one greater
	 * @return The next beat
	 */
	public synchronized int nextBeat() {
		if (this.currentBeat == this.patternLength) {
			this.currentBeat = 0;
		}	
		return this.currentBeat++;
	}
	
	/**
	 * Plays the beats in each of the pattern at the given beat
	 * @param beat The index of the beat to play
	 * @return Returns false when the beat was out the range 0 <= beat < patternLength
	 */
	public boolean playBeat(int beat) {
		if (beat < patternLength && beat >= 0) {
			for (Pattern pattern: patterns) {
				pattern.playPosition(beat);
			}
			return true;
		} else return false;
	}
	
	/**
	 * Stops the loop from playing
	 */
	public synchronized void stop() {
		if (timer.getStatus() == Animation.Status.RUNNING) {
			this.timer.stop();
			this.currentBeat = 0;
			for (Pattern pattern: patterns) {
				pattern.getSound().stop_playing();
			}
		}
	}
	
	/**
	 * Adds a pattern to the patterns arraylist
	 * @param pattern The pattern instance to add to patterns
	 * @return True if patterns is changes as a result, this will rarely be false, no need to use the value
	 */
	public boolean addPattern(Pattern pattern) {
		pattern.setLength(patternLength);
		return patterns.add(pattern);
	}
	
	/**
	 * Removes the pattern at the given index from the loop
	 * @param index The index of the pattern, the number on the left when using 'view'
	 * @return True if the pattern was in the loop and was removed.
	 */
	public boolean removePattern(int index) {
		if (patterns.remove(index) != null)
			return true;
		else return false;
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
		setPollDelay((int) (60000.0f / bpm));
	}
	
	
	// -------------------
	// Getters and Setters
	// -------------------
	
	
	/**
	 * Gets an array of equal size to the number of patterns, containing the respective patterns' pattern strings
	 * @return An array of pattern strings
	 */
	public String[] getPatternStrings() {
		String[] patternStrings = new String[patterns.size()];
		int i = 0;
		for (Pattern pattern: patterns) {
			patternStrings[i++] = pattern.getPattern();
		}
		return patternStrings;
	}
	
	/**
	 * Get the Collection of patterns in this loop
	 * @return A Collection of patterns
	 */
	public Collection<Pattern> getPatterns() {
		return patterns;
	}
	
	/**
	 * Returns the number of patterns in the loop
	 * @return the number of patterns in the loop
	 */
	public int getNumPatterns() {
		return patterns.size();
	}
	
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
	 * Calls setRepeat in loop, indicating whether the patterns should repeat themselves indefinitely.
	 * @param repeat True if you want the loop to repeat.
	 */
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
	
	/**
	 * Gets whether the loop will repeat or not.
	 * @return True if the loop repeats
	 */
	public boolean getRepeat() {
		return repeat;
	}
	
	/**
	 * Returns the loop in a string format used for saving to file
	 * lines:
	 * 1: (pattern length)
	 * 2: (bpm)
	 * 3..n: (pattern string) (pattern file path)
	 * 
	 * Replacing any bracketed section with the real value
	 * 
	 * e.g.:
	 * 1: 8
	 * 2: 160
	 * 3: x-x-x-x- samples/acoustic1/kick1 
	 * 4: -x-x-x-x samples/acoustic1/snareclosed1
	 */
	@Override
	public String toString() {
		String output = "";
		output += patternLength + "\n";
		output += bpm + "\n";
		for (Pattern pattern: patterns) {
			output += pattern.getPattern() + " " + pattern.getSound().getFilePath()  + "\n";
		}
		return output;
	}
	
}
