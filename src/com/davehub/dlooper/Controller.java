package com.davehub.dlooper;

import com.davehub.dlooper.ui.View;

/**
 * Interface for a Controller that user interfaces interact with in a View-Controller-Model design
 * @author dave
 *
 */
public interface Controller {
	/**
	 * Adds a new pattern with the specified audio file
	 * @param filePath The path to the audio file
	 */
	public void addPattern(String filePath);
	/**
	 * Starts the loop playing
	 * Calls
	 */
	public void play();
	/**
	 * Sets the bpm to the given value
	 * @param bpm The desired bpm value
	 * @return False when bpm <= 0, in which case the bpm isnt changed
	 */
	public boolean setBpm(int bpm);
	/**
	 * Get the BPM setting of the current loop
	 * @return The BPM as an int
	 */
	public int getBpm();
	/**
	 * Set the pattern at the given index to the pattern string specified
	 * @param index The index of the pattern in the loop ArrayList
	 * @param pattern The pattern string
	 * @return True if the pattern string is changed @see Pattern.setPattern
	 */
	public boolean setPattern(int index, String pattern);
	/**
	 * 
	 * @param index
	 * @return
	 */
	public String getPattern(int index);
	/**
	 * Returns a list of the pattern strings in the loop.
	 * @return The list of pattern strings
	 */
	public String[] getPatterns();
	/**
	 * Sets the pattern length of all patterns in the loop to the specified value
	 * @param index The index of the patter
	 * @param pattern
	 * @return
	 */
	public boolean setPatternLength(int length);
	/**
	 * Gets the pattern length of the current loop
	 * @return The pattern length as an int
	 */
	public int getPatternLength();
	/**
	 * Sets the sound of the pattern at the given index, to the audio file specified by the file path
	 * @param index The index of the pattern to change within the loop ArrayList
	 * @param filePath The path to the audio file
	 */
	public void setPatternSound(int index, String filePath);
	/**
	 * Set the loop to repeat or not
	 * @param repeat True if you want the loop to repeat.
	 */
	public void setRepeat(boolean repeat);
	/**
	 * Connect a View component to allow passing Controller error messages back to the View
	 * @param view The View object to connect
	 */
	public void addViewer(View view);
}
