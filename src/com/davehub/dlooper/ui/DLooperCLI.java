package com.davehub.dlooper.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.davehub.dlooper.Controller;
import com.davehub.dlooper.DLooper;
import com.davehub.dlooper.loop.Pattern;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * The runnable command line user interface for using the DLooper system
 * @author dave
 *
 */
public class DLooperCLI extends Application {
	
	/**
	 * Enum of runnable commands
	 */
	private enum Command {
		help, quit, play, stop, addpattern, rmpattern, setpl, pl, setbpm, bpm, setrepeat, view, setpattern, save, load, unknown
	};
		
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
	
	
	public DLooperCLI(Controller controller) {
		this.controller = controller;
		this.running = true;
	}
	public DLooperCLI() {
		this(new DLooper());
	}
	
	
	// -----------------------
	// Program Command Methods
	// -----------------------
	
	
	/**
	 * Prints a list of commands to the console
	 */
	public void help() {
		System.out.println("\n---Help---\n");
		System.out.println("help              - Displays the help message (this)");
		System.out.println("quit              - Quits the program");
		System.out.println("\n---Loop Control---");
		System.out.println("play              - Plays the loop");
		System.out.println("stop              - Stops playing");
		System.out.println("view              - View the loop");
		System.out.println("save <path>       - Save the loop to the file at the given path");
		System.out.println("load <path>       - Load the loop from the file at the specified path");
		System.out.println("setpl <length>	  - Sets the length of the patterns to the specified length");
		System.out.println("pl                - Prints the current pattern length");
		System.out.println("setbpm <bpm>      - Sets the BPM to the given int");
		System.out.println("bpm               - Prints the current BPM");
		System.out.println("setrepeat <bool>  - Sets whether to repeat or not, true to repeat.");
		System.out.println("\n---Pattern Control---");
		System.out.println("addpattern <file>          - Adds a new pattern with the specified file as the sound");
		System.out.println("rmpattern <num>            - Removes the pattern with the given number");
		System.out.println("setpattern <num> <pattern> - Sets the pattern with the given number to the pattern given");
		System.out.println("\n----------\n");
	}
	
	/**
	 * Quits the program
	 */
	public void quit() {
		this.running = false;
	}
	
	
	// ------------
	// Loop Control
	// ------------
	
	
	/**
	 * Plays the loop
	 */
	public void playLoop() {
		controller.play();
	}

	/**
	 * Stops the loop from playing
	 */
	private void stopLoop() {
		controller.stop();
	}
	
	/**
	 * Print the loop with some details
	 */
	public void view() {
		System.out.println("\n---Pattern---\n");
		bpm();
		patternLength();
		System.out.println();
		int i = 0;
		for (Pattern pattern: controller.getPatterns()) {
			System.out.println(i + ": " + pattern.getPattern() + " (" + pattern.getSound().getFilePath() + ")");
			i++;
		}
		System.out.println();
	}
	
	/**
	 * Load the loop from the given file.
	 * @param filePath The file containing the loop.
	 */
	private void loadFromFile(String filePath) {
		try {
			controller.loadFromFile(filePath);
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: File not found");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	/**
	 * Save loop to given file.
	 * @param filePath The file to save the loop to.
	 */
	private void saveToFile(String filePath) {
		try {
			controller.saveToFile(filePath);
		} catch (IOException e) {
			System.err.println("ERROR: Could not write to file");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Displays the current pattern length
	 */
	public void patternLength() {
		System.out.println("Pattern Length: " + controller.getPatternLength());
	}

	/**
	 * Sets the pattern length of all patterns in the loop
	 * @param length The length of which you want the pattern
	 */
	public void setPatternLength(String length) {
		if (length.matches("[0-9]+")) {
			int len = Integer.parseInt(length);
			if (!controller.setPatternLength(len)) {
				System.out.println("ERROR: Pattern length not set, length <= 0.");
			}
		} else {
			System.out.println("ERROR: Argument must be an integer.");
		}
	}

	/**
	 * Displays the current bpm
	 */
	public void bpm() {
		System.out.println("Beats Per Minute: " + controller.getBpm());
	}
	
	/**
	 * Set the Beats Per Minute (BPM) to the given value
	 * @param bpm
	 */
	public void setBpm(String bpm) {
		if (bpm.matches("[0-9]+")) {
			int BPM = Integer.parseInt(bpm);
			if (!controller.setBpm(BPM)) {
				System.out.println("ERROR: BPM not set, bpm <= 0");
			}
		} else {
			System.out.println("ERROR: Argument must be an integer");
		}
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
	
	
	// ---------------
	// Pattern Control
	// ---------------
	
	
	/**
	 * Adds a pattern with the sound from the given sound
	 * @param filePath The path to a sound file.
	 */
	public void addPattern(String filePath) {
		try {
			controller.addPattern(filePath);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Remvoes the pattern at the given index
	 * @param index The index of the pattern to remove within the loop
	 */
	private boolean rmPattern(int index) {
		if(!controller.removePattern(index)) {
			System.out.println("ERROR: Pattern " + index + " does not exist.");
			return false;
		} else return true;
	}
	
	/**
	 * Sets the pattern at the given index to the given pattern
	 * @param index The index of the pattern, the number to the left of the pattern when using 'view'
	 * @param pattern The pattern string to set the Pattern at the given index to.
	 */
	public void setPattern(int index, String pattern) {
		if (pattern.length() != controller.getPatternLength()) {
			System.out.println("ERROR: Given pattern is not of correct length");
			System.out.println("Pattern Length: " + controller.getPatternLength());
			System.out.println("Given Pattern:  " + pattern.length());
			return;
		}
		if (index >= controller.getNumPatterns()) {
			System.out.println("ERROR: Given pattern number out of range.");
			if (controller.getNumPatterns() > 0) {
				System.out.println("Pattern number range: 0 - " + (controller.getNumPatterns()-1));
			} else System.out.println("There are currently no patterns, use 'addpattern' to add one");
			return;
		}
		controller.setPattern(index, pattern);
	}
	
	// -------
	// Methods
	// -------
	
	
	/**
	 * Executes the given command with the given arguments
	 * @param command The command to execute
	 * @param args Any arguments (can be empty)
	 */
	public void execute(String command, String[] args) {
		//matches Command to given string, set as help when command not recognised.
		Command cmd = Command.unknown;
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
				playLoop();
				break;
			case stop:
				stopLoop();
				break;
			case addpattern:
				if (args.length >= 1) {
					addPattern(args[0]);
				} else System.out.println("ERROR: Requires one argument.");
				break;
			case rmpattern:
				if (args.length >= 1) {
					if (isNumeric(args[0])) {
						rmPattern(Integer.parseInt(args[0]));
					} else System.out.println("ERROR: Arguemnt must be numeric.");
				} else System.out.println("ERROR: Requires one argument.");
				break;
			case setpl:
				if (args.length >= 1) {
					setPatternLength(args[0]);
				} else System.out.println("ERROR: Requires one argument.");
				break;
			case pl:
				patternLength();
				break;
			case setbpm:
				if (args.length >= 1) {
					setBpm(args[0]);
				} else System.out.println("ERROR: Requires one argument.");
				break;
			case bpm:
				bpm();
				break;
			case setrepeat:
				if (args.length >= 1) {
					setRepeat(args[0]);
				} else System.out.println("ERROR: Requires one argument.");
				break;
			case view:
				view();
				break;
			case setpattern:
				if (args.length >= 2) {
					if (isNumeric(args[0])) {
						setPattern(Integer.parseInt(args[0]), args[1]);
					} else System.out.println("ERROR: First arguemnt must be numeric.");
				} else System.out.println("ERROR: Requires two arguments.");
				break;
			case save:
				if (args.length >= 1) {
					saveToFile(args[0]);
				} else System.out.println("ERROR: Must specify file to save to.");
				break;
			case load:
				if (args.length >= 1) {
					loadFromFile(args[0]);
				} else System.out.println("ERROR: Must specify file to load from.");
				break;
			default:
				System.out.println("ERROR: Unrecognised Command \"" + command + "\". Type \"help\" for help");
				break;
		}
	}
	
	/**
	 * Returns whether the program is running or not
	 * @return The boolean value 'running'.
	 */
	private boolean running() {
		return running;
	}
	
	
	// ------------------
	// Validation Methods
	// ------------------
	
	
	public static boolean isNumeric(String str) {
		return java.util.regex.Pattern.compile("[0-9]+").matcher(str).matches();
	}
	
	
	// ------------
	// Main Methods
	// ------------
	
	
	@Override
	public void start(Stage stage0) {
		//Init
		System.out.println("Initialising...\n");
		Scanner input = new Scanner(System.in);
		
		//Welcome Message
		System.out.println("---Welcome To DLooper---");
		System.out.println("Create looping drum beats from audio files.");
		System.out.println("An empty Loop has been created for you to start with...");
		bpm();
		patternLength();
		System.out.println("Type 'help' for help with commands.\n");
		System.out.print(">>");
		
		//loop command execution until.
		while(running()) {
			//Takes line from command line, splits into a command and arguments
			String inp = input.nextLine();
			String[] inputLine = inp.split(" ");
			String command = inputLine[0];
			String[] arguments = new String[inputLine.length-1];
			for (int i=1; i<inputLine.length; i++) {
				arguments[i-1] = inputLine[i];
			}
			//executes command
			execute(command, arguments);
			System.out.print(">>");
		}
		
		//Exit
		input.close();
	}
	
	public static void main(String[] args) {
		DLooper controller = new DLooper();
		DLooperCLI cli = new DLooperCLI(controller);
		cli.start(null);
		Platform.exit();
        System.exit(0);
	}
}