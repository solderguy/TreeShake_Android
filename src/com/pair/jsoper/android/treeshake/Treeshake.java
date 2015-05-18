/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.immersion.uhl.Launcher;
import com.pair.jsoper.android.framework.Screen;
import com.pair.jsoper.android.framework.impl.GLGame;

public class Treeshake extends GLGame {
	boolean firstTimeCreate = true;
	static public boolean isTablet = false;
	static public Launcher mLauncher; // to use Immersion haptic effects

	@Override
	public Screen getStartScreen() {
		return new MainMenuScreen(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		
		float yInches= displaymetrics.heightPixels/displaymetrics.ydpi;
		float xInches= displaymetrics.widthPixels/displaymetrics.xdpi;
		double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
		if (diagonalInches>=5.9) // Galaxy Note phones go up to 5.7in
			isTablet = true;
		
		try {
			mLauncher = new Launcher(this);
		} catch (Exception e) {
			Log.e("My App", "Exception!: " + e.getMessage());
		}
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if (firstTimeCreate) {
			Settings.load(getFileIO());
			Assets.load(this);
			firstTimeCreate = false;
		} else {
			Assets.reload();
		}
	}

	@Override
	public void onPause() {
		super.onPause();

//		if (Settings.soundEnabled)
//			Assets.music.pause();

		// stop vibration effect on application pause
		try {
			mLauncher.stop();
		} catch (RuntimeException re) {
		}

	}
}