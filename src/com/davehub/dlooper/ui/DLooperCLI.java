package com.davehub.dlooper.ui;

import com.davehub.dlooper.Controller;
import com.davehub.dlooper.DLooper;
import com.davehub.dlooper.DLooperError;

/**
 * The runnable command line user interface for using the DLooper system
 * @author dave
 *
 */
public class DLooperCLI implements View {
	
	/**
	 * Enum of runnable commands
	 */
	private enum COMMAND {
		help, quit, play, addpattern, setpl, pl, setbpm, bpm, setrepeat};
		
	/**
	 * The Controller this user interface interacts with
	 */
	@SuppressWarnings("unused")
	private Controller controller;
	
	
	// -----------
	// Constructor
	// -----------
	
	
	/**
	 * Default constructor, creates a DLooper Controller
	 */
	public DLooperCLI() {
		this.controller = new DLooper();
	}
	
	
	// -------------------
	// Implemented Methods
	// -------------------
	
	
	/**
	 * Prints an error message based on the input from the {@link com.davehub.dlooper.DLooperError}
	 */
	@Override
	public void displayError(DLooperError e) {
		switch(e) {
			case UnsupportedAudioFormat:
				System.out.println("ERROR: Unsupported Audio Format");
				//Add accepted formats later, dont know them right now
			case SystemAudioError:
				System.out.println("ERROR: System Audio Unavailible");
			case FileIOError:
				System.out.println("ERROR: Couldn't read file, check spelling");
		}
	}
	
	
	// -------
	// Methods
	// -------
	
	
	/**
	 * Prints a list of commands to the console
	 */
	public void help() {
		System.out.println("\n---Help--\n");
		for (COMMAND command: COMMAND.values()) {
			switch (command) {
			case help:
				System.out.println("help              - Displays the help message (this)");
			case quit:
				System.out.println("quit              - Quits the program");
			case play:
				System.out.println("play              - Plays the loop");
			case addpattern:
				System.out.println("addpattern <file> - Adds a new pattern with the specified file as the sound");
			case setpl:
				System.out.println("setpl <length>	  - Sets the length of the patterns to the specified length");
			case pl:
				System.out.println("pl                - Prints the current pattern length");
			case setbpm:
				System.out.println("setbpm <bpm>      - Sets the BPM to the given int");
			case bpm:
				System.out.println("bpm               - Prints the current BPM");
			case setrepeat:
				System.out.println("setrepeat <bool>  - Sets whether to repeat or not, true to repeat.");
				
			}
		}
	}
	
	
	// ------------
	// Main Methods
	// ------------
	
	
	public static void main(String[] args) {
		//Init
		System.out.println("Initialising...\n");
		DLooperCLI cli = new DLooperCLI();
		
		//Welcome Message
		System.out.println("---Welcome To DLooper---");
		System.out.println("Create looping drum beats from audio files.");
		System.out.println("An empty Loop has been created for you to start with...");
		//put calls to pl and bpm here
		System.out.println("Type 'help' for help with commands.\n");
		
		cli.help();
	}
}
