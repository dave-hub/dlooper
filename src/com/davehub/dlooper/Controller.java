package com.davehub.dlooper;

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
	 * Set the pattern at the given index to the pattern string specified
	 * @param index The index of the pattern in the loop ArrayList
	 * @param pattern The pattern string
	 * @return True if the pattern string is changed @see Pattern.setPattern
	 */
	public boolean setPattern(int index, String pattern);
	/**
	 * Sets the pattern length of all patterns in the loop to the specified value
	 * @param index The index of the patter
	 * @param pattern
	 * @return
	 */
	public boolean setPatternLength(int length);
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
}
