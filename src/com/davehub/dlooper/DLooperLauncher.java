package com.davehub.dlooper;

import com.davehub.dlooper.ui.DLooperCLI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class DLooperLauncher extends Application {

	@Override
	public void start(Stage arg0) throws Exception {
		DLooperCLI cli = new DLooperCLI();
		cli.run();
		Platform.exit();
	}

	public static void main(String[] args) {
		launch(args);
		System.exit(0);
	}

}
