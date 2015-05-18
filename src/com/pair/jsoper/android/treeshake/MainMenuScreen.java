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
import com.pair.jsoper.android.framework.gl.SpriteBatcher;
import com.pair.jsoper.android.framework.impl.GLScreen;
import com.pair.jsoper.android.framework.math.OverlapTester;
import com.pair.jsoper.android.framework.math.Rectangle;
import com.pair.jsoper.android.framework.math.Vector2;

public class MainMenuScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle soundBounds;
	Rectangle hardPlayBounds;
	Rectangle easyPlayBounds;
	Rectangle highscoresBounds;
	Rectangle helpBounds;
	Vector2 touchPoint;

	public MainMenuScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, 320, 480);
		batcher = new SpriteBatcher(glGraphics, 100);
		soundBounds = new Rectangle(0, 0, 64, 64);
		easyPlayBounds = new Rectangle(160 - 150, 200 + 40, 300, 40);
		hardPlayBounds = new Rectangle(160 - 150, 200 + 0, 300, 40);
		highscoresBounds = new Rectangle(160 - 150, 200 - 40, 300, 40);
		helpBounds = new Rectangle(160 - 150, 200 - 80, 300, 40);
		touchPoint = new Vector2();
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);

				if (OverlapTester.pointInRectangle(easyPlayBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					World.maxGameDifficulty = false;
					game.setScreen(new GameScreen(game));
					return;
				}
				if (OverlapTester.pointInRectangle(hardPlayBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					World.maxGameDifficulty = true;
					game.setScreen(new GameScreen(game));
					return;
				}
				if (OverlapTester
						.pointInRectangle(highscoresBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new HighscoresScreen(game));
					return;
				}
				if (OverlapTester.pointInRectangle(helpBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new HelpScreen(game));
					return;
				}
				if (OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					Settings.soundEnabled = !Settings.soundEnabled;
					if (Settings.soundEnabled)
						;// Assets.music.play();
					else
						;//Assets.music.pause();
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();

		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(Assets.background);
		batcher.drawSprite(160, 240, 320, 480, Assets.backgroundRegion);
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.items);

		batcher.drawSprite(160, 480 - 10 - 81, 322, 142, Assets.logo);
		batcher.drawSprite(160, 200, 300, 160, Assets.mainMenu2);
		batcher.drawSprite(32, 57, 64, 64,
				Settings.soundEnabled ? Assets.soundOn : Assets.soundOff);

		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
