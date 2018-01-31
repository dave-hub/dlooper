package com.davehub.dlooper;

import java.io.File;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class PlayTest extends Application {

	@Override
	public void start(Stage arg0) throws Exception {
		DSound sound = new DSound("samples/acoustic1/hihatopen1.wav");
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private class DSound implements Runnable {
		private Media media;
		private Boolean playf;
		
		public DSound(String filePath) {
			this.media = new Media(new File(filePath).toURI().toString());
		}

		public void play() {
			synchronized(playf) {
				this.playf = true;
			}
		}
		
		private Boolean getPlay() {
			synchronized(playf) {
				return this.playf;
			}
		}
		
		@Override
		public void run() {
			while(true) {
				if(getPlay()) {
					MediaPlayer player = new MediaPlayer(media);
					player.play();
				}
			}
		}
		
		
	}

}
