/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import javax.microedition.khronos.opengles.GL10;

import com.pair.jsoper.android.framework.gl.Animation;
import com.pair.jsoper.android.framework.gl.Camera2D;
import com.pair.jsoper.android.framework.gl.SpriteBatcher;
import com.pair.jsoper.android.framework.gl.TextureRegion;
import com.pair.jsoper.android.framework.impl.GLGraphics;

public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	GLGraphics glGraphics;
	World world;
	Camera2D cam;
	SpriteBatcher batcher;

	public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher,
			World world) {
		this.glGraphics = glGraphics;
		this.world = world;
		this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.batcher = batcher;
	}

	public void render() {
		if (world.cannon.position.y > cam.position.y)
			cam.position.y = world.cannon.position.y;
		cam.setViewportAndMatrices();
		renderBackground();
		renderObjects();
	}

	public void renderBackground() {
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(cam.position.x, cam.position.y, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT, Assets.backgroundRegion);
		batcher.endBatch();
	}

	public void renderObjects() {
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.items);
		renderBirdFlying();
		renderFallingLeaves();
		renderLeaves();
		renderNetted();
		renderItems(); // includes trim so put after leaves
		renderCannon();
		renderNet();
		renderApples();
		renderWoodpecker();

		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);

	}

	private void renderCannon() {
		switch (world.gameState) {
		case World.GAME_STATE_ACQUIRE1:
			batcher.drawSprite(world.cannon.position.x,
					world.cannon.position.y, 3, 3, Assets.cannonB);
			break;
		case World.GAME_STATE_ACQUIRE2:
			batcher.drawSprite(world.cannon.position.x,
					world.cannon.position.y, 3, 3, Assets.cannonC);
			break;
		case World.GAME_STATE_ACQUIRE3:
			batcher.drawSprite(world.cannon.position.x,
					world.cannon.position.y, 3, 3, Assets.cannonD);
			break;
		default:
			batcher.drawSprite(world.cannon.position.x,
					world.cannon.position.y, 3, 3, Assets.cannonA);
		}
	}

	private void renderNet() {
		float netSize;

		if (world.net.position.y < 15)
			netSize = 3.5f * ((world.net.position.y - 0.5f) / 8.5f);
		else
			netSize = 0.1f;
		
		batcher.drawSprite(world.net.position.x, world.net.position.y, netSize,
				netSize, Assets.net);
	}

	private void renderNetted() {
		float netSize = 2.0f;
		batcher.drawSprite(world.netted.position.x, world.netted.position.y,
				netSize, netSize, Assets.netted);
	}

	private void renderItems() {
		
		
		batcher.drawSprite(10, 146, 300,
				0.4f, Assets.trim);
		
		batcher.drawSprite(world.trim.position.x, world.trim.position.y, 20,
				0.4f, Assets.trim);
		batcher.drawSprite(world.ground.position.x, world.ground.position.y,
				20, 2.0f, Assets.ground);
	}

	private void renderWoodpecker() {
		TextureRegion keyFrame = Assets.woodpecker.getKeyFrame(
				world.woodpecker.stateTime, Animation.ANIMATION_LOOPING);
		batcher.drawSprite(world.woodpecker.position.x,
				world.woodpecker.position.y, 2, 2, keyFrame);
	}

	private void renderBirdFlying() {
		if (world.gameState == World.GAME_STATE_LANDING) {
			batcher.drawSprite(world.birdFlying.position.x,
					world.birdFlying.position.y, 2, 2, Assets.birdLanding);
		} else if (world.gameState == World.GAME_STATE_FLYING) {
			TextureRegion keyFrame = Assets.birdFlying.getKeyFrame(
					world.birdFlying.stateTime, Animation.ANIMATION_LOOPING);
			batcher.drawSprite(world.birdFlying.position.x,
					world.birdFlying.position.y, 2, 2f, keyFrame);
		}
	}

	private void renderFallingLeaves() {
		int len = world.fallingLeaves.size();
		for (int i = 0; i < len; i++) {
			FallingLeaf fallingLeaf = world.fallingLeaves.get(i);
			TextureRegion keyFrame = Assets.fallingLeaf.getKeyFrame(
					fallingLeaf.stateTime, Animation.ANIMATION_LOOPING);
			batcher.drawSprite(fallingLeaf.position.x, fallingLeaf.position.y,
					1.5f, 1.5f, keyFrame);
		}
	}

	private void renderApples() {
		int len = world.apples.size();
		for (int i = 0; i < len; i++) {
			Apple apple = world.apples.get(i);
			batcher.drawSprite(apple.position.x, apple.position.y, 0.8f, 0.8f,
					Assets.apple);
		}
	}

	private void renderLeaves() {
		int len = world.leaves.size();
		for (int i = 0; i < len; i++) {
			Leaf leaf = world.leaves.get(i);
			batcher.drawSprite(leaf.position.x, leaf.position.y, 1.5f, 1.5f,
					Assets.leaf);
		}
	}
}
