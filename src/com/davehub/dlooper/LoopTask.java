package com.davehub.dlooper;

import java.util.TimerTask;

import com.davehub.dlooper.loop.Loop;
import com.davehub.dlooper.loop.Pattern;

public class LoopTask extends TimerTask {
	
	/**
	 * The loop this task plays
	 */
	private Loop loop;
	/**
	 * The current beat when playing the loop.
	 */
	private int currentBeat;
	
	public LoopTask(Loop loop) {
		this.loop = loop;
		this.currentBeat = 0;
	}

	@Override
	public void run() {
		if (currentBeat >= loop.getPatternLength()) {
			if (loop.getRepeat()) {
				currentBeat = 0;
			} else {
				loop.stop();
				return;
			}
		}
		for (Pattern pattern: loop.getPatterns()) {
			pattern.playPosition(currentBeat);
		}
		currentBeat++;
	}

}
