/**
 * @author Mario Zechner
 * 
 * Gaming framework provided from book "Beginning Android Games"
 */

package com.pair.jsoper.android.framework.gl;

public class FPSCounter {
	long startTime = System.nanoTime();
	int frames = 0;

	public void logFrame() {
		frames++;
		if (System.nanoTime() - startTime >= 1000000000) {
			// Log.d("FPSCounter", "fps: " + frames);
			frames = 0;
			startTime = System.nanoTime();
		}
	}
}
