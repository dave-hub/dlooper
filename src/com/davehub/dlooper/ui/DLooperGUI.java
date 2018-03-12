package com.davehub.dlooper.ui;

import java.io.IOException;

import javax.swing.JFrame;

import com.davehub.dlooper.DLooper;
import com.davehub.dlooper.gooey.DLooperWindow;

import javafx.application.Application;
import javafx.stage.Stage;

public class DLooperGUI extends Application {


	public static void main(String[] args) throws IOException, Exception {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		DLooper dl = new DLooper();
		DLooperWindow window = new DLooperWindow(dl);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
