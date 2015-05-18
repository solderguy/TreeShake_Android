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

public class HighscoresScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle backBounds;
	Vector2 touchPoint;
	String[] highScores;
	String[] easyHighScores;
	float xOffset = 0;

	public HighscoresScreen(Game game) {
		super(game);

		guiCam = new Camera2D(glGraphics, 320, 480);
		backBounds = new Rectangle(0, 0, 64, 64);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 100);
		highScores = new String[5];
		easyHighScores = new String[5];
		for (int i = 0; i < 5; i++) {
			easyHighScores[i] = (i + 1) + ". " + Settings.easyHighscores[i];
			xOffset = Math.max(easyHighScores[i].length()
					* Assets.font.glyphWidth, xOffset);
		}
		for (int i = 0; i < 5; i++) {
			highScores[i] = (i + 1) + ". " + Settings.highscores[i];
			xOffset = Math.max(highScores[i].length() * Assets.font.glyphWidth,
					xOffset);
		}
		xOffset = 160 - xOffset / 2 + Assets.font.glyphWidth / 2;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);

			if (event.type == TouchEvent.TOUCH_UP) {
				if (OverlapTester.pointInRectangle(backBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					game.setScreen(new MainMenuScreen(game));
					return;
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
		batcher.drawSprite(160, 380, 300, 33, Assets.highScoresRegion);
		batcher.drawSprite(170, 320, 100, 20, Assets.easyRegion);
		batcher.drawSprite(170, 180, 100, 20, Assets.hardRegion);

		float y = 220;
		for (int i = 4; i >= 0; i--) {
			Assets.font.drawText(batcher, easyHighScores[i], xOffset, y);
			y += Assets.font.glyphHeight;
		}
		y = 80;
		for (int i = 4; i >= 0; i--) {
			Assets.font.drawText(batcher, highScores[i], xOffset, y);
			y += Assets.font.glyphHeight;
		}

		batcher.drawSprite(32, 22, 64, 64, Assets.arrow);
		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void resume() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void dispose() {
	}
}
