package com.davehub.dlooper;

public class Pattern {
	
	/**
	 * The default symbol used to represent an audible beat within the pattern string
	 */
	private static final char DEFAULT_SYMBOL = 'X';
	/**
	 * The symbol used to represent a pause, where no sound is playing for this pattern
	 */
	private static final char PAUSE_SYMBOL = '-';
	/**
	 * The string representing the pattern, consisting of the symbol attribute and
	 */
	private String pattern;
	/**
	 * The maximum length the pattern string is allowed to be, to be played properly
	 */
	private int length;
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
	private Pattern(String pattern, int length, char symbol) {
		this.pattern = pattern;
		this.length = length;
		this.symbol = symbol;
	}
	
	/**
	 * The sets constructor used by user interfaces
	 * @param patternLength The length of the patterns, for ensuring pattern strings don't exceed this
	 */
	public Pattern(int patternLength) {
		this(generateEmptyPatternString(patternLength), patternLength, DEFAULT_SYMBOL);
	}
	
	
	
	// -------------------
	// Getters and Setters
	// -------------------
	
	
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
		if (pattern.length() == getLength()) {
			this.pattern = pattern;
			return true;
		} else return false;
	}
	
	/**
	 * Returns the length of the pattern
	 * @return The length of the pattern
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * Sets the length of the pattern string
	 * @param length The value to set the length to
	 */
	public void setLength(int length) {
		this.length = length;
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
	
	
	// --------------
	// Static methods
	// --------------
	
	
	/**
	 * Used for initialisation. Generates a string of PAUSE_SYMBOL's ('-') of the length given
	 * @param length The number of PAUSE_SYMBOL's of which to form a pattern string from
	 * @return A string of PAUSE_SYMBOL's ('-') with given length to use as a initial pattern string
	 */
	private static String generateEmptyPatternString(int length) {
		String pattern = "";
		for (int i=0; i<length; i++) {
			pattern += PAUSE_SYMBOL;
		}
		return pattern;
	}
}
