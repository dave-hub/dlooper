package com.davehub.dlooper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import com.davehub.dlooper.loop.DrumSound;
import com.davehub.dlooper.loop.Loop;
import com.davehub.dlooper.loop.Pattern;

/**
 * Acts as a Controller in a View Model Controller design
 * @author dave-hub
 */
public class DLooper implements Controller {
	
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
	 * Plays each pattern within the loop 
	 */
	@Override
	public void play() {
		loop.play();
	}
	
	/**
	 * Stops playing the loop
	 */
	@Override
	public void stop() {
		loop.stop();
	}
	
	/**
	 * Returns the loop this controller uses
	 * @return The loop instance this controller controls
	 */
	public Loop getLoop() {
		return loop;
	}

	/**
	 * Set this controller to use the given loop
	 * @param loop The loop to use.
	 */
	public void setLoop(Loop loop) {
		this.loop = loop;
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
	 * Adds a pattern to the loop with the given length and audio file specified by path.
	 * @param patternLength The length of the pattern
	 * @param filePath The path to the audio file to load
	 * @throws Exception 
	 */
	@Override
	public void addPattern(String filePath) throws Exception {
		loop.addPattern(new Pattern(new DrumSound(filePath)));
	}
	
	/**
	 * Removes the pattern at the given index from the loop
	 * @param index The index of the pattern, the number on the left when using 'view'
	 * @return True if the pattern was in the loop and was removed.
	 */
	@Override
	public boolean removePattern(int index) {
		return loop.removePattern(index);
	}
	
	/**
	 * Attempts to set the pattern at the given index to the given string
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
	 * @throws Exception 
	 */
	@Override
	public void setPatternSound(int index, String filePath) throws Exception {
		loop.getPatternAt(index).setSoundFilePath(filePath);
	}
	
	/**
	 * Returns a list of the pattern strings in the loop.
	 * @return The list of pattern strings
	 */
	@Override
	public String[] getPatternStrings() {
		return loop.getPatternStrings();
	}
	
	/**
	 * Returns a Collection of the Patterns in the loop.
	 * @return Returns a Collection of Patterns
	 */
	@Override
	public Collection<Pattern> getPatterns() {
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
	 * Returns pattern at given index
	 * @param index The index of the pattern to retrieve
	 * @return The pattern at the given index
	 */
	@Override
	public Pattern getPattern(int index) {
		return loop.getPatternAt(index);
	}
	
	/**
	 * Returns pattern string for pattern at given index
	 * @param index The index of the pattern to retrieve
	 * @return The pattern string of the pattern at the given index
	 */
	@Override
	public String getPatternString(int index) {
		return loop.getPatternAt(index).getPattern();
	}
	
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

	/**
	 * Get the current repeat setting of the loop
	 * @return True if the loop will repeat
	 */
	@Override
	public boolean getRepeat() {
		return loop.getRepeat();
	}
	
	/**
	 * Set the loop to repeat or not
	 * @param repeat True to make the loop repeat
	 */
	@Override
	public void setRepeat(boolean repeat) {
		loop.setRepeat(repeat);
	}

	/**
	 * Save to file at the given path. Will create a new file.
	 * @param filePath The path of the file to save to.
	 * @throws IOException 
	 */
	@Override
	public void saveToFile(String filePath) throws IOException {
		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(filePath))) {
			System.out.println("Writing to file: " + filePath);
			bw.write(loop.toString());
			System.out.println("Done.");
			bw.close();
		} catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * Loads the loop from the given file to this controller
	 * @param filePath The path of the file to load
	 * @throws IOException
	 * @throws Exception
	 */
	public void loadFromFile(String filePath) throws IOException, Exception{
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;
		ArrayList<String> patternLines = new ArrayList<String>();
		
		//read loop settings from first 2 lines
		String patternLengthString = br.readLine();
		String bpmString = br.readLine();
		
		//read patterns from file
		while ((line = br.readLine()) != null) {
			patternLines.add(line);
		}
		
		br.close();
		
		//check for errors
		if (!isNumeric(patternLengthString)) {
			throw new Exception("Line 1: Pattern Length should be only numeric");
		}
		if (!isNumeric(bpmString)) {
			throw new Exception("Line 2: Beats Per Minute should be only numeric");
		}
		
		
		//initialise new Loop
		Loop loop = new Loop(Integer.parseInt(bpmString), Integer.parseInt(patternLengthString));
		
		//add patterns
		for (String patternLine: patternLines) {
			String[] parts = patternLine.split(" ");
			Pattern pattern = new Pattern(new DrumSound(parts[1]));
			pattern.setPattern(parts[0]);
			loop.addPattern(pattern);
		}
		
		//Load the loop into this controller
		setLoop(loop);
	}
	
	/**
	 * Checks whether a string is purely numeric or not
	 * @param str The string to check
	 * @return Returns true if the string contains only numbers
	 */
	public static boolean isNumeric(String str) {
		return java.util.regex.Pattern.compile("[0-9]+").matcher(str).matches();
	}
	
}
