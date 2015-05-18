/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import com.pair.jsoper.android.framework.GameObject;

public class Trim extends GameObject {
	public static float TRIM_WIDTH = 1.0f;
	public static float TRIM_HEIGHT = 1.0f;

	public Trim(float x, float y) {
		super(x, y, TRIM_WIDTH, TRIM_HEIGHT);
	}
}