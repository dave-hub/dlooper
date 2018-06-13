package com.davehub.dlooper;

import java.io.IOException;
import java.util.Collection;

import com.davehub.dlooper.loop.Loop;
import com.davehub.dlooper.loop.Pattern;

/**
 * Interface for a Controller that user interfaces interact with in a View-Controller-Model design
 * @author dave
 *
 */
public interface Controller {
	
	/**
	 * Starts the loop playing
	 */
	public void play();
	/**
	 * Stops playing the loop
	 */
	public void stop();
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
	 * Set this controller to use the given loop
	 * @param loop The loop to use.
	 */
	public void setLoop(Loop loop);
	/**
	 * Returns the loop this controller uses
	 * @return The loop instance this controller controls
	 */
	public Loop getLoop();
	/**
	 * Adds a new pattern with the specified audio file
	 * @param filePath The path to the audio file
	 * @throws Exception 
	 */
	public void addPattern(String filePath) throws Exception;
	/**
	 * Removes the pattern at the given index from the loop
	 * @param index The index of the pattern, the number on the left when using 'view'
	 * @return True if the pattern was in the loop and was removed.
	 */
	public boolean removePattern(int index);
	/**
	 * Set the pattern at the given index to the pattern string specified
	 * @param index The index of the pattern in the loop ArrayList
	 * @param pattern The pattern string
	 * @return True if the pattern string is changed @see Pattern.setPattern
	 */
	public boolean setPattern(int index, String pattern);
	/**
	 * Get the pattern string at the given index.
	 * @param index The index of the pattern
	 * @return Returns the pattern string at the given index
	 */
	public Pattern getPattern(int index);
	/**
	 * Returns a list of the pattern strings in the loop.
	 * @return The list of pattern strings
	 */
	public String[] getPatternStrings();
	/**
	 * Return a collection of the patterns in the loop.
	 * @return A Collection of patterns.
	 */
	public Collection<Pattern> getPatterns();
	/**
	 * Returns the number of patterns in the loop
	 * @return The number of patterns in the loop
	 */
	public int getNumPatterns();
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
	 * @throws Exception 
	 */
	public void setPatternSound(int index, String filePath) throws Exception;
	/**
	 * Returns the current repeat setting.
	 */
	public boolean getRepeat();
	/**
	 * Set the loop to repeat or not
	 * @param repeat True if you want the loop to repeat.
	 */
	public void setRepeat(boolean repeat);
	/**
	 * Save to a file of the given path. Will create a file.
	 * @param filePath The path to the file to save to. 
	 * @throws IOException 
	 * @throws Exception 
	 */
	public void saveToFile(String filePath) throws IOException, Exception;
	/**
	 * Loads the loop from the given file.
	 * @param filePath
	 */
	public void loadFromFile(String filePath) throws IOException, Exception;
	/**
	 * Returns pattern string for pattern at given index
	 * @param index The index of the pattern to retrieve
	 * @return The pattern string of the pattern at the given index
	 */
	public String getPatternString(int index);

}
