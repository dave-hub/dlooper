package com.davehub.dlooper.ui;

import java.util.Scanner;

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
	private enum Command {
		help, quit, play, addpattern, setpl, pl, setbpm, bpm, setrepeat,
		view};
		
	/**
	 * The Controller this user interface interacts with
	 */
	private Controller controller;
	
	/**
	 * Whether the program is still taking inputs and executing. Used when quit() is called.
	 */
	private boolean running;
	
	
	// -----------
	// Constructor
	// -----------
	
	
	/**
	 * Default constructor, creates a DLooper Controller
	 */
	public DLooperCLI() {
		this.controller = new DLooper();
		this.running = true;
		controller.addViewer(this);
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
		System.out.println("\n---Help---\n");
		System.out.println("help              - Displays the help message (this)");
		System.out.println("quit              - Quits the program");
		System.out.println("play              - Plays the loop");
		System.out.println("addpattern <file> - Adds a new pattern with the specified file as the sound");
		System.out.println("setpl <length>	  - Sets the length of the patterns to the specified length");
		System.out.println("pl                - Prints the current pattern length");
		System.out.println("setbpm <bpm>      - Sets the BPM to the given int");
		System.out.println("bpm               - Prints the current BPM");
		System.out.println("setrepeat <bool>  - Sets whether to repeat or not, true to repeat.");
		System.out.println("\n----------\n");
	}
	
	/**
	 * Quits the program
	 */
	public void quit() {
		this.running = false;
	}
	
	/**
	 * Plays the loop
	 */
	public void play() {
		controller.play();
	}
	
	/**
	 * Adds a pattern with the sound from the given sound
	 * @param filePath The path to a sound file.
	 */
	public void addPattern(String filePath) {
		controller.addPattern(filePath);
	}
	
	/**
	 * Sets the pattern length of all patterns in the loop
	 * @param length The length of which you want the pattern
	 */
	public void setPatternLength(String length) {
		if (length.matches("[0-9]+")) {
			int len = Integer.parseInt(length);
			if (!controller.setPatternLength(len)) {
				System.out.println("ERROR: Pattern length not set, length <= 0");
			}
		} else {
			System.out.println("ERROR: Argument must be an integer");
		}
	}
	
	/**
	 * Displays the current pattern length
	 */
	public void patternLength() {
		System.out.println("Pattern Length: " + controller.getPatternLength());
	}
	
	/**
	 * Set the Beats Per Minute (BPM) to the given value
	 * @param bpm
	 */
	public void setBpm(String bpm) {
		System.out.println(bpm);
		if (bpm.matches("[0-9]+")) {
			int BPM = Integer.parseInt(bpm);
			System.out.println(BPM);
			if (!controller.setBpm(BPM)) {
				System.out.println("ERROR: BPM not set, bpm <= 0");
			}
		} else {
			System.out.println("ERROR: Argument must be an integer");
		}
	}
	
	/**
	 * Displays the current bpm
	 */
	public void bpm() {
		System.out.println("Beats Per Minute: " + controller.getBpm());
	}
	
	/**
	 * Sets whether the loop repeats when playing
	 * @param repeat String representing "true" or "false", printing an error otherwise.
	 */
	public void setRepeat(String repeat) {
		if (repeat.equals("true")) {
			controller.setRepeat(true);
		} else if (repeat.equals("false")) {
			controller.setRepeat(false);
		} else {
			System.out.println("ERROR: Argument must be \"true\" or \"false\"");
		}
	}
	
	/**
	 * Print the loop with some details
	 */
	public void view() {
		System.out.println("Details:");
		bpm();
		patternLength();
		System.out.println("----------------------");
		int i = 0;
		for (String pattern: controller.getPatterns()) {
			System.out.println(i + ": " + pattern);
			i++;
		}
		System.out.println("----------------------");
	}
	
	/**
	 * Executes the given command with the given arguments
	 * @param command The command to execute
	 * @param args Any arguments (can be empty)
	 */
	public void execute(String command, String[] args) {
		//matches Command to given string, set as help when command not recognised.
		Command cmd = Command.help;
		for (Command c: Command.values()) {
			if (c.toString().equals(command)) {
				cmd = c;
				break;
			}
		}
		switch(cmd) {
			case help:
				help();
				break;
			case quit:
				quit();
				break;
			case play:
				play();
				break;
			case addpattern:
				if (args.length >= 1) //1 is for at least one argument in the array
					addPattern(args[0]);
				break;
			case setpl:
				if (args.length >= 1)
					setPatternLength(args[0]);
				break;
			case pl:
				patternLength();
				break;
			case setbpm:
				if (args.length >= 1) 
					setBpm(args[0]);
				break;
			case bpm:
				bpm();
			case setrepeat:
				if (args.length >= 1)
					setRepeat(args[0]);
				break;
			case view:
				view();
			default:
				System.out.println("ERROR: Unrecognised Command \"" + command + "\". Type \"help\" for help");
			}
		
	}
	
	/**
	 * Returns whether the program is running or not
	 * @return The boolean value 'running'.
	 */
	private boolean running() {
		return running;
	}
	
	// ------------
	// Main Methods
	// ------------
	
	
	public static void main(String[] args) {
		//Init
		System.out.println("Initialising...\n");
		DLooperCLI cli = new DLooperCLI();
		Scanner input = new Scanner(System.in);
		
		//Welcome Message
		System.out.println("---Welcome To DLooper---");
		System.out.println("Create looping drum beats from audio files.");
		System.out.println("An empty Loop has been created for you to start with...");
		cli.bpm();
		cli.patternLength();
		System.out.println("Type 'help' for help with commands.\n");
		
		//loop command execution until.
		while(cli.running()) {
			//Takes line from command line, splits into a command and arguments
			System.out.print(">>");
			String inp = input.nextLine();
			String[] inputLine = inp.split(" ");
			for (String il: inputLine)
				System.out.println(il);
			String command = inputLine[0];
			String[] arguments = new String[inputLine.length-1];
			for (int i=1; i<inputLine.length; i++) {
				System.out.println(inputLine[i]);
				arguments[i-1] = inputLine[i];
			}
			for (String arg: arguments) {
				System.out.println("\n arg");
				System.out.println(arg);
			}
			//executes command
			cli.execute(command, arguments);
		}
		
		//Exit
		input.close();
		System.exit(0);
	}


	
}
