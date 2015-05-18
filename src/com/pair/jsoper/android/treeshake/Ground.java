/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import com.pair.jsoper.android.framework.GameObject;

public class Ground extends GameObject {
	public static float GROUND_WIDTH = 1.0f;
	public static float GROUND_HEIGHT = 1.0f;

	public Ground(float x, float y) {
		super(x, y, GROUND_WIDTH, GROUND_HEIGHT);
	}
}