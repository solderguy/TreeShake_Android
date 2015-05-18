/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

// might add music in future, so keep hooks
//import com.pair.jsoper.android.framework.Music;
import com.pair.jsoper.android.framework.Sound;
import com.pair.jsoper.android.framework.gl.Animation;
import com.pair.jsoper.android.framework.gl.Font;
import com.pair.jsoper.android.framework.gl.Texture;
import com.pair.jsoper.android.framework.gl.TextureRegion;
import com.pair.jsoper.android.framework.impl.GLGame;

public class Assets {
	public static Texture background;
	public static Texture items;

	public static TextureRegion backgroundRegion;
	public static TextureRegion mainMenu;
	public static TextureRegion mainMenu2;
	public static TextureRegion pauseMenu;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion highScoresRegion;
	public static TextureRegion easyRegion;
	public static TextureRegion hardRegion;
	public static TextureRegion logo;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion pause;
	public static TextureRegion spring;
	public static TextureRegion cannonA;
	public static TextureRegion cannonB;
	public static TextureRegion cannonC;
	public static TextureRegion cannonD;
	public static TextureRegion leaf;
	public static TextureRegion net;
	public static TextureRegion netted;
	public static TextureRegion apple;
	public static TextureRegion bullet;
	public static TextureRegion trim;
	public static TextureRegion ground;
	public static TextureRegion birdLanding;

	public static Animation woodpecker;
	public static Animation fallingLeaf;
	public static Animation birdFlying;
	public static Font font;

	//public static Music music;
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound coinSound;
	public static Sound clickSound;
	public static Sound birdCrySound;
	public static Sound netFireSound;

	public static void load(GLGame game) {
		background = new Texture(game, "background.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);

		items = new Texture(game, "items.png");
		mainMenu = new TextureRegion(items, 0, 224, 300, 110);
		mainMenu2 = new TextureRegion(items, 707, 1, 300, 160);
		pauseMenu = new TextureRegion(items, 224, 128, 192, 96);
		ready = new TextureRegion(items, 320, 224, 192, 32);
		gameOver = new TextureRegion(items, 352, 256, 160, 96);
		highScoresRegion = new TextureRegion(Assets.items, 0, 257, 300, 110 / 3);
		easyRegion = new TextureRegion(Assets.items, 877, 7, 118, 32);
		hardRegion = new TextureRegion(Assets.items, 877, 49, 118, 32);
		logo = new TextureRegion(items, 0, 352, 274, 142);
		soundOff = new TextureRegion(items, 0, 0, 64, 64);
		soundOn = new TextureRegion(items, 64, 0, 64, 64);
		arrow = new TextureRegion(items, 0, 64, 64, 54);
		pause = new TextureRegion(items, 64, 64, 50, 50);
		bullet = new TextureRegion(items, 150, 70, 40, 40);
		spring = new TextureRegion(items, 128, 0, 32, 32);
		leaf = new TextureRegion(items, 450, 136, 55, 55);
		fallingLeaf = new Animation(0.2f, new TextureRegion(items, 516, 135,
				55, 55), new TextureRegion(items, 589, 135, 55, 55));
		birdFlying = new Animation(0.15f, 
				new TextureRegion(items, 568, 317, 150, 90), 
				new TextureRegion(items, 760, 317, 150, 90));
		birdLanding = new TextureRegion(items, 529, 208, 126, 110);
		cannonA = new TextureRegion(items, 143, 4, 52, 52);
		cannonB = new TextureRegion(items, 529, 7, 52, 52);
		cannonC = new TextureRegion(items, 624, 8, 52, 52);
		cannonD = new TextureRegion(items, 529, 69, 52, 52);
		trim = new TextureRegion(items, 700, 168, 320, 20);
		ground = new TextureRegion(items, 701, 216, 320, 60);
		woodpecker = new Animation(0.20f, 
				new TextureRegion(items, 300, 350, 100, 140), 
				new TextureRegion(items, 400, 350, 100, 140));
		net = new TextureRegion(items, 0, 120, 85, 85);
		netted = new TextureRegion(items, 130, 120, 66, 90);
		apple = new TextureRegion(items, 150, 70, 40, 40);

		font = new Font(items, 224, 0, 16, 16, 20);

		// future option?
//		music = game.getAudio().newMusic("music.mp3");
//		music.setLooping(true);
//		music.setVolume(0.5f);
		if (Settings.soundEnabled)
			;//music.play();
		//jumpSound = game.getAudio().newSound("jump.ogg");
		highJumpSound = game.getAudio().newSound("highjump.ogg");
		hitSound = game.getAudio().newSound("hit.ogg");
		coinSound = game.getAudio().newSound("coin.ogg");
		clickSound = game.getAudio().newSound("click.ogg");
		netFireSound = game.getAudio().newSound("netFire.wav");
		birdCrySound = game.getAudio().newSound("birdCry.wav");
	}

	public static void reload() {
		background.reload();
		items.reload();
		 if (Settings.soundEnabled)
			 ;//music.play();
	}

	public static void playSound(Sound sound) {
		if (Settings.soundEnabled)
			sound.play(1);
	}
}
