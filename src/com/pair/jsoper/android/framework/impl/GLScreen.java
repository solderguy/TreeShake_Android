/**
 * @author Mario Zechner
 * 
 * Gaming framework provided from book "Beginning Android Games"
 */

package com.pair.jsoper.android.framework.impl;

import com.pair.jsoper.android.framework.Game;
import com.pair.jsoper.android.framework.Screen;

public abstract class GLScreen extends Screen {
	protected final GLGraphics glGraphics;
	protected final GLGame glGame;

	public GLScreen(Game game) {
		super(game);
		glGame = (GLGame) game;
		glGraphics = ((GLGame) game).getGLGraphics();
	}

}
