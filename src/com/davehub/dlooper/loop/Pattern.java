package com.davehub.dlooper.loop;

public class Pattern {
	
	/**
	 * The default symbol used to represent an audible beat within the pattern string
	 */
	private static final char DEFAULT_SYMBOL = 'x';
	/**
	 * The symbol used to represent a pause, where no sound is playing for this pattern
	 */
	private static final char PAUSE_SYMBOL = '-';
	/**
	 * The string representing the pattern, consisting of the symbol attribute and
	 */
	private String pattern;
	/**
	 * The sound which this pattern plays
	 */
	private DrumSound sound;
	/**
	 * The symbol representing an audible beat within the pattern
	 */
	private char symbol;
	
	
	// ------------
	// Constructors
	// ------------

	
	/**
	 * The full constructor
	 * @param pattern The pattern to which initialise this pattern with.
	 * @param length The length of the pattern, maintains the number of characters in the pattern string
	 * @param symbol The symbol used to represent an audible beat within the pattern string
	 */
	private Pattern(String pattern, DrumSound sound, char symbol) {
		this.pattern = pattern;
		this.sound = sound;
		this.symbol = symbol;
		Thread t = new Thread(sound);
		t.start();
	}
	
	/**
	 * The sets constructor used by user interfaces
	 * @param patternLength The length of the patterns, for ensuring pattern strings don't exceed this
	 */
	public Pattern(DrumSound sound) {
		this("", sound, DEFAULT_SYMBOL);
	}
	
	
	// -------
	// Methods
	// -------
	
	
	/**
	 * Plays the DrumSound sound if there is supposed to be sound played at the specified position in the pattern.
	 * @param patternPosition The position at which  there should be a sound played
	 * @return True if a sound is played
	 */
	public boolean playPosition(int patternPosition) {
		if (patternPosition >= pattern.length()) {
			return false;
		}
		if (pattern.charAt(patternPosition) == symbol) {
			sound.play();
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the character at the given position within the pattern
	 * @param position The index of the symbol pattern you want to retrieve
	 * @return The character at the given index position in the pattern
	 */
	public char getSymbolAt(int position) {
		return pattern.charAt(position);
	}
	
	/**
	 * Checks the input pattern has the same length, and that it only contains the PAUSE_SYMBOL and the symbol variable as characters.
	 * Checks length against existing pattern, assumes the pattern length is always maintained form initialisation.
	 * @param pattern The pattern to test
	 * @return True if the pattern given is of the right length and contains the right characters to be used in this pattern object
	 */
	public boolean validatePattern(String test) {
		if (pattern.length() == test.length()) {
			for (char x: test.toCharArray()) {
				if (!(x == PAUSE_SYMBOL || x == symbol)) {
					return false;
				}
			}
			return true;
		} else return false;
	}
	
	/**
	 * Sets the sound to play from the specified audio file.
	 * @param filePath The path to the audio file to change the sound to
	 * @throws Exception 
	 */
	public void setSoundFilePath(String filePath) throws Exception {
		sound.setFilePath(filePath);
	}
	
	
	// -------------------
	// Getters and Setters
	// -------------------
	
	
	/**
	 * Return the sound of this pattern 
	 * @return The DrumSound that this pattern plays
	 */
	public DrumSound getSound() {
		return sound;
	}

	/**
	 * Sets the pattern to play the given sound
	 * @param sound The DrumSound to make this pattern play
	 */
	public void setSound(DrumSound sound) {
		this.sound = sound;
	}

	/**
	 * Returns the string that represents the pattern
	 * @return The pattern string
	 */
	public String getPattern() {
		return pattern;
	}
	
	/**
	 * Set pattern string to the given string
	 * @param pattern The string which represents the pattern
	 * @return Returns false when the given pattern is not the same length as the length attribute, ensuring patterns are all the same length
	 */
	public boolean setPattern(String pattern) {
		if (validatePattern(pattern)) {
			this.pattern = pattern;
			return true;
		} else return false;
	}

	/**
	 * Returns the length of the pattern
	 * @return The length of the pattern
	 */
	public int getLength() {
		return pattern.length();
	}
	
	/**
	 * Sets the length of the pattern string, and resizes the current pattern either by add PAUSE_SYMBOL's ('-') or cutting the end of the string off.
	 * @param length The value to set the length to
	 */
	public boolean setLength(int length) {
		if (length > 0) {
			while (pattern.length() < length) {
				pattern += PAUSE_SYMBOL;
			}
			this.pattern = pattern.substring(0, length);
			return true;
		} else return false;
	}
	
	/**
	 * Returns the symbol used to represent an audible beat in the pattern string
	 * @return The character symbol
	 */
	public char getSymbol() {
		return symbol;
	}
	
	/**
	 * Sets the symbol used to represent an audible beat in the pattern string to the given character symbol
	 * @param symbol The character to set as the symbol
	 */
	public boolean setSymbol(char symbol) {
		if(symbol != DEFAULT_SYMBOL) {
			this.symbol = symbol;
			return true;
		} else return false;
	}
}
