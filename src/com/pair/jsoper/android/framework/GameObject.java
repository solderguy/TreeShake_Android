/**
 * @author Mario Zechner
 * 
 * Gaming framework provided from book "Beginning Android Games"
 */

package com.pair.jsoper.android.framework;

import com.pair.jsoper.android.framework.math.Rectangle;
import com.pair.jsoper.android.framework.math.Vector2;

public class GameObject {
	public final Vector2 position;
	public final Rectangle bounds;

	public GameObject(float x, float y, float width, float height) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x - width / 2, y - height / 2, width,
				height);
	}
}
