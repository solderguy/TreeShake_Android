/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import com.pair.jsoper.android.framework.DynamicGameObject;

public class Net extends DynamicGameObject {
	public static final float NET_WIDTH = 1;
	public static final float NET_HEIGHT = 0.6f;
	public static final float NET_VELOCITY = 0;

	float stateTime = 0;

	public Net(float x, float y) {
		super(x, y, NET_WIDTH, NET_HEIGHT);
		velocity.set(NET_VELOCITY, 0);
	}

	public void update(float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(NET_WIDTH / 2, NET_HEIGHT / 2);

		velocity.x = NET_VELOCITY;
		stateTime += deltaTime;
	}
}