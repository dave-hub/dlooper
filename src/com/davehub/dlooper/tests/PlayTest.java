package com.davehub.dlooper.tests;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class PlayTest extends Application {

	public PlayTest() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		TimerTask task = new TimerTask() {
	        @Override
	        public void run() {
	        	new MediaPlayer(new Media(new File("samples/acoustic1/kick1.wav").toURI().toString())).play();
	        }
	    };
	    Timer timer = new Timer();
	    timer.scheduleAtFixedRate(task, 0, 1000); //start immediately, 1000ms period
	}

	public static void main(String[] args) {
		launch(args);
	}

}
