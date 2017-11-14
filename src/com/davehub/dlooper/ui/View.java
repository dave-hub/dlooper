package com.davehub.dlooper.ui;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.davehub.dlooper.Controller;
import com.davehub.dlooper.DLooperError;

/**
 * Interface for the {@link Controller} to interact with any user interfaces
 * The Provided Controller implemented class is {@link com.davehub.dlooper.DLooper}
 * @author dave
 *
 */
public interface View {
	/**
	 * Handles Exceptions passed by a Controller
	 * Default controller {@link com.davehub.dlooper.DLooper} throws :
	 * {@link UnsupportedAudioFileException} - Incorrect audio format
	 * {@link LineUnavailableException}		 - System audio not availible
	 * {@link IOException}					 - File cannot be read or found
	 * @param e The exception thrown by the Controller
	 */
	public void displayError(DLooperError e);
	/**
	 * The (View) user interface needs a controller to interact with.
	 * If using the existing {@link Controller} class {@link com.davehub.dlooper.DLooper}
	 * @param controller The controller to add
	 */
	//public void addController(Controller controller);
}
