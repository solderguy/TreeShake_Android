/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.pair.jsoper.android.framework.Game;
import com.pair.jsoper.android.framework.Input.TouchEvent;
import com.pair.jsoper.android.framework.gl.Camera2D;
import com.pair.jsoper.android.framework.gl.FPSCounter;
import com.pair.jsoper.android.framework.gl.SpriteBatcher;
import com.pair.jsoper.android.framework.impl.GLScreen;
import com.pair.jsoper.android.framework.math.OverlapTester;
import com.pair.jsoper.android.framework.math.Rectangle;
import com.pair.jsoper.android.framework.math.Vector2;
import com.pair.jsoper.android.treeshake.World.WorldListener;

public class GameScreen extends GLScreen {
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	static final int GAME_WIN_SEQUENCE = 5;

	int state;
	Camera2D guiCam;
	Vector2 touchPoint;
	SpriteBatcher batcher;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	int lastScore;
	String scoreString;
	FPSCounter fpsCounter;

	public GameScreen(Game game) {
		super(game);
		state = GAME_READY;
		guiCam = new Camera2D(glGraphics, 320, 480);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 1000);
		worldListener = new WorldListener() {
			@Override
			public void jump() {
				Assets.playSound(Assets.coinSound);
			}

			@Override
			public void highJump() {
				Assets.playSound(Assets.highJumpSound);
			}

			@Override
			public void hit() {
				Assets.playSound(Assets.hitSound);
			}

			@Override
			public void netFire() {
				Assets.playSound(Assets.netFireSound);
			}

			@Override
			public void birdCry() {
				Assets.playSound(Assets.birdCrySound);
			}
		};
		world = new World(worldListener);
		renderer = new WorldRenderer(glGraphics, batcher, world);
		pauseBounds = new Rectangle(320 - 64, 480 - 64, 64, 64);
		resumeBounds = new Rectangle(160 - 96, 240, 192, 36);
		quitBounds = new Rectangle(160 - 96, 240 - 36, 192, 36);
		lastScore = 0;
		scoreString = "score: 0";
		fpsCounter = new FPSCounter();
	}

	@Override
	public void update(float deltaTime) {
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;

		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		case GAME_WIN_SEQUENCE:
			updateWinSequence();
			break;
		}
	}

	private void updateReady() {
		if (game.getInput().getTouchEvents().size() > 0) {
			state = GAME_RUNNING;
		}
	}

	private void updateRunning(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;

			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);

			if (OverlapTester.pointInRectangle(pauseBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
				return;
			}
		}

		// Tablet portrait Y axis is Phone portrait X axis
		if (Treeshake.isTablet)
			world.update(deltaTime, game.getInput().getAccelY());
		else
			world.update(deltaTime, game.getInput().getAccelX());

		if (world.score != lastScore) {
			lastScore = world.score;
			scoreString = "" + lastScore;
		}
		
		if (world.state == World.WORLD_STATE_NEXT_LEVEL) {
			state = GAME_LEVEL_END;
		}
		else if (world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			if (((lastScore >= Settings.highscores[4]) && World.maxGameDifficulty)
					|| ((lastScore >= Settings.easyHighscores[4]) && !World.maxGameDifficulty))
				scoreString = "new highscore:" + lastScore;
			else
				scoreString = "score: " + lastScore;
			Settings.addScore(lastScore);
			Settings.save(game.getFileIO());
		}
		else if (world.state == World.WORLD_STATE_WIN_SEQUENCE) {
			state = GAME_WIN_SEQUENCE;
			if (((lastScore >= Settings.highscores[4]) && World.maxGameDifficulty)
					|| ((lastScore >= Settings.easyHighscores[4]) && !World.maxGameDifficulty))
				scoreString = "new highscore:" + lastScore;
			else
				scoreString = "score: " + lastScore;
			Settings.addScore(lastScore);
			Settings.save(game.getFileIO());
		}
	}

	private void updatePaused() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;

			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);

			if (OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}

			if (OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
	}

	private void updateLevelEnd() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;
			world = new World(worldListener);
			renderer = new WorldRenderer(glGraphics, batcher, world);
			world.score = lastScore;
			state = GAME_READY;
		}
	}

	private void updateGameOver() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;
			game.setScreen(new MainMenuScreen(game));
		}
	}
	
	private void updateWinSequence() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;
			game.setScreen(new MainMenuScreen(game));
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		renderer.render();

		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.items);
		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		case GAME_WIN_SEQUENCE:
			presentWinSequence();
			break;
		}
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
		fpsCounter.logFrame();
	}

	private void presentReady() {
		batcher.drawSprite(160, 240, 192, 32, Assets.ready);
	}

	private void presentRunning() {
		batcher.drawSprite(320 - 50, 480 - 20, 50, 50, Assets.pause);
		Assets.font.drawText(batcher, scoreString, 16, 480 - 20);
	}

	private void presentPaused() {
		batcher.drawSprite(160, 240, 192, 96, Assets.pauseMenu);
		Assets.font.drawText(batcher, scoreString, 16, 480 - 20);
	}

	private void presentLevelEnd() {
		String topText = "the princess is ...";
		String bottomText = "in another castle!";
		float topWidth = Assets.font.glyphWidth * topText.length();
		float bottomWidth = Assets.font.glyphWidth * bottomText.length();
		Assets.font.drawText(batcher, topText, 160 - topWidth / 2, 480 - 40);
		Assets.font.drawText(batcher, bottomText, 160 - bottomWidth / 2, 40);
	}

	private void presentGameOver() {
		batcher.drawSprite(160, 240, 160, 96, Assets.gameOver);
		float scoreWidth = Assets.font.glyphWidth * scoreString.length();
		Assets.font.drawText(batcher, scoreString, 160 - scoreWidth / 2,
				480 - 20);
	}
	
	private void presentWinSequence() {
		String topText = "You saved the tree!";
		batcher.drawSprite(160, 240, 160, 96, Assets.gameOver);
		float scoreWidth = Assets.font.glyphWidth * scoreString.length();
		float messageWidth = Assets.font.glyphWidth * topText.length();
		Assets.font.drawText(batcher, scoreString, 160 - scoreWidth / 2,
				480 - 20);
		Assets.font.drawText(batcher, topText, 160 - messageWidth / 2,
				480 - 80);
	}

	@Override
	public void pause() {
		if (state == GAME_RUNNING)
			state = GAME_PAUSED;
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
