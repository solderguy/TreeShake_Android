/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.immersion.uhl.Launcher;
import com.pair.jsoper.android.framework.math.Vector2;

public class World {

	public interface WorldListener {
		public void jump();

		public void highJump();

		public void hit();

		public void netFire();

		public void birdCry();
	}

	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 20;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final int WORLD_STATE_WIN_SEQUENCE = 3;

	public static final int GAME_STATE_OFFSCREEN = 0;
	public static final int GAME_STATE_FLYING = 1;
	public static final int GAME_STATE_LANDING = 2;
	public static final int GAME_STATE_PECKING = 3;
	public static final int GAME_STATE_ACQUIRE1 = 4;
	public static final int GAME_STATE_ACQUIRE2 = 5;
	public static final int GAME_STATE_ACQUIRE3 = 6;
	public static final int GAME_STATE_NET_LAUNCHED = 7;
	public static final int GAME_STATE_NETTED = 8;
	public static final int GAME_STATE_NETTED_SLOWDOWN = 9;
	public static boolean maxGameDifficulty;

	public static final Vector2 gravity = new Vector2(0, -12);

	public final Cannon cannon;
	public final Net net;
	public final Netted netted;
	public final Trim trim;
	public final Ground ground;
	public final Woodpecker woodpecker;
	public final BirdFlying birdFlying;
	public final List<Leaf> leaves;
	public final List<Apple> apples;
	public final List<FallingLeaf> fallingLeaves;

	public final WorldListener listener;
	public final Random rand;

	private boolean cannonAligned;// ; = false;

	public float heightSoFar;
	private int birdNum = 0;
	public int score;
	public int state;
	public int gameState = GAME_STATE_OFFSCREEN;
	private int fallingLeafCount = 1;
	private int thisBirdsInitialLeafCount = 0; // leaf count when each birds
												// start drumming
	private float windSpeed = 0.4f;
	private boolean haveBallast;
	private double windStartTime = 0.0f;
	private boolean leavesShakeRight;
	private boolean treeIsShaking;
	private boolean treeStillHasLeaves = true;

	public World(WorldListener listener) {
		this.cannon = new Cannon(7, 2.1f);
		this.net = new Net(4.0f, -2.0f);
		this.netted = new Netted(4.8f, 17);
		this.trim = new Trim(0.0f, 13.6f);
		this.ground = new Ground(0.0f, 0.75f);
		this.woodpecker = new Woodpecker(-1.0f, -1.0f);
		this.birdFlying = new BirdFlying(-8.0f, 5.2f);

		this.listener = listener;
		this.leaves = new ArrayList<Leaf>();
		this.fallingLeaves = new ArrayList<FallingLeaf>();
		this.apples = new ArrayList<Apple>();

		rand = new Random();
		generateLevel();

		this.heightSoFar = 0;
		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
	}

	private void generateLevel() {
		addLeaf(7.0f, 11.0f);
		addLeaf(9.2f, 13.3f);
		addLeaf(5.2f, 12.5f);
		addLeaf(2.5f, 11.7f);
		addLeaf(8.2f, 12.9f);
		addLeaf(3.5f, 12.7f);
		addLeaf(8.2f, 12.9f);
		addLeaf(4.1f, 10.9f);
		addLeaf(6.5f, 12.1f);
		addLeaf(1.0f, 12.8f);
		addLeaf(7.7f, 13.2f);
		addLeaf(4.9f, 13.3f);
		addLeaf(2.6f, 10.8f);
		addLeaf(2.7f, 13.0f);
		addLeaf(9.3f, 12.9f);
		addLeaf(3.8f, 13.3f);
		addLeaf(8.3f, 12.1f);
		addLeaf(5.5f, 10.7f);
		addLeaf(2.3f, 13.2f);
		addLeaf(5.5f, 12.9f);
		addLeaf(9.3f, 10.7f);
		addLeaf(4.5f, 11.1f);
		addLeaf(6.3f, 12.7f);
		addLeaf(1.8f, 12.8f);
		addLeaf(3.2f, 10.8f);
		addLeaf(0.7f, 13.3f);
		addLeaf(9.7f, 12.5f);
		addLeaf(3.3f, 11.1f);
		addLeaf(4.0f, 11.8f);
		addLeaf(8.3f, 10.9f);
		addLeaf(2.0f, 10.8f);
		addLeaf(7.1f, 11.9f);
		addLeaf(1.5f, 13.2f);
		addLeaf(6.7f, 13.1f);
		addLeaf(1.2f, 11.2f);
		addLeaf(1.4f, 11.1f);
		addLeaf(5.3f, 11.9f);
		addLeaf(7.9f, 11.7f);
		addLeaf(6.6f, 10.7f);
		addLeaf(7.5f, 10.8f);
		addLeaf(4.6f, 12.5f);
		addLeaf(7.2f, 12.6f);
		addLeaf(6.0f, 11.4f);
		addLeaf(9.8f, 12.3f);
		addLeaf(3.2f, 12.0f);
		addLeaf(9.0f, 11.1f);
		addLeaf(2.8f, 12.5f);
		addLeaf(6.0f, 12.1f);
		addLeaf(8.9f, 12.7f);
		addLeaf(1.0f, 11.9f);
		addLeaf(4.8f, 11.9f);
		addLeaf(8.8f, 11.5f);
		addLeaf(4.8f, 10.8f);
		addLeaf(6.7f, 11.3f);
		addLeaf(2.3f, 12.2f);
		addLeaf(9.9f, 13.2f);
		addLeaf(2.8f, 10.9f);
		addLeaf(3.3f, 13.1f);
		addLeaf(9.7f, 11.4f);
		addLeaf(6.0f, 13.2f);
		addLeaf(3.9f, 12.6f);
		addLeaf(9.4f, 12.0f);
		addLeaf(2.3f, 11.5f);
		addLeaf(6.1f, 10.8f);
		addLeaf(7.3f, 11.6f);
		addLeaf(1.5f, 12.5f);
		addLeaf(8.7f, 12.0f);
		addLeaf(4.0f, 11.1f);
		addLeaf(5.8f, 11.3f);
		addLeaf(8.5f, 13.2f);
		addLeaf(7.7f, 10.8f);
		addLeaf(4.3f, 13.3f);
		addLeaf(5.7f, 13.3f);
		addLeaf(8.3f, 11.6f);
		addLeaf(3.8f, 11.8f);
		addLeaf(7.5f, 13.1f);
		addLeaf(3.4f, 11.8f);
		addLeaf(6.0f, 10.9f);
		addLeaf(3.2f, 11.4f);
		addLeaf(5.3f, 11.3f);
		addLeaf(4.2f, 11.6f);
		addLeaf(7.8f, 11.1f);
		addLeaf(1.7f, 11.8f);
		addLeaf(7.6f, 12.3f);
		addLeaf(4.3f, 12.5f);
		addLeaf(9.7f, 12.6f);

		addLeaf(-1.4f, 14.0f); // this leaf is kept out of sight and used for
								// shaking
		// boundaries

		addFallingLeaf(1.2f, -2.0f);
		addFallingLeaf(2.2f, -2.0f);
		addFallingLeaf(3.2f, -2.0f);
		addFallingLeaf(4.2f, -2.0f);
		addFallingLeaf(5.2f, -2.0f);
		addFallingLeaf(6.2f, -2.0f);
		addFallingLeaf(7.2f, -2.0f);
		addFallingLeaf(8.2f, -2.0f);
		addFallingLeaf(9.2f, -2.0f);
		addFallingLeaf(10.2f, -2.0f);

		Apple apple = new Apple(5.7f, 12.6f);
		apples.add(apple);
		apple = new Apple(1.8f, 12.3f);
		apples.add(apple);
		apple = new Apple(8.7f, 11.5f);
		apples.add(apple);
	}

	public void addLeaf(float x, float y) {
		Leaf leaf = new Leaf(x, y);
		leaves.add(leaf);
	}

	public void addFallingLeaf(float x, float y) {
		FallingLeaf fallingLeaf = new FallingLeaf(x, y);
		fallingLeaves.add(fallingLeaf);
	}

	// accelXorY comes about because Tablet portrait Y axis is 
	// Phone portrait X axis
	// see GameScreen.updateRunning() for more info
	public void update(float deltaTime, float accelXorY) {
		updateWind();
		updateCannon(deltaTime, accelXorY);
		updateNet(deltaTime);
		updateNetted(deltaTime);

		if ((gameState >= GAME_STATE_PECKING)
				&& (gameState <= GAME_STATE_NET_LAUNCHED))
			treeIsShaking = true;
		else
			treeIsShaking = false;

		updateFallingLeaf(deltaTime);
		updateLeaves(deltaTime);
		updateApples(deltaTime);

		updateWoodpeckers(deltaTime);
		updateBirdFlying(deltaTime);
	}

	private void updateWind() {
		double currentTime = System.nanoTime();
		double timeSoFar = currentTime - windStartTime;

		timeSoFar /= 1000000000.0f; // convert to seconds
		if (timeSoFar > 5.0f) {
			if (maxGameDifficulty)
				windSpeed = -1.2f * windSpeed;
			else
				windSpeed = -1.0f * windSpeed;

			if (windSpeed > 5.0f)
				windSpeed = 5.0f;
			else if (windSpeed < -5.0f)
				windSpeed = -5.0f;
			windStartTime = currentTime;
		}
	}

	private void updateCannon(float deltaTime, float accel) {
		cannon.velocity.y = 0.0f;

		if (gameState == GAME_STATE_ACQUIRE3)
			cannon.velocity.x = 0.0f; // no movement during firing
		else if (haveBallast) // cannon not affected by wind
			cannon.velocity.x = -accel / 10 * Cannon.CANNON_VELOCITY;
		else
			cannon.velocity.x = windSpeed - accel / 10 * Cannon.CANNON_VELOCITY;

		if ((cannon.position.x > 4.35f) && (cannon.position.x < 5.35f)) {
			cannonAligned = true;
		} else {
			cannonAligned = false;
		}

		cannon.update(deltaTime);
		heightSoFar = Math.max(cannon.position.y, heightSoFar);
	}

	private void updateFallingLeaf(float deltaTime) {
		int len = fallingLeaves.size();
		for (int i = 0; i < len; i++) {
			FallingLeaf fallingLeaf = fallingLeaves.get(i);
			fallingLeaf.update(deltaTime);
		}
	}

	private void updateLeaves(float deltaTime) {

		int len = leaves.size();
		for (int i = 0; i < len; i++) {
			Leaf leaf = leaves.get(i);
			FallingLeaf fallingLeaf = fallingLeaves.get(i % 10);

			if (leaf.velocity.y < -0.5f) // if leaf is falling
			{
				leaf.velocity.x = 0.0f;
				fallingLeaf.velocity.x = windSpeed;
			}

			if (leaf.position.y < -1.5f) {
				; // minimize delay for leaves already on ground
			} else if (leaf.position.y < -1.0) {
				leaf.position.y = -2.0f;
				leaf.velocity.y = 0.0f;
				fallingLeaf.position.y = -2.0f;
				fallingLeaf.velocity.y = 0.0f;
			} else if (i == 0) // first leaf is self-starting for drop
			{
				fallingLeaf.position.x = leaf.position.x;
				fallingLeaf.position.y = leaf.position.y;
				leaf.position.x = -2.0f;
				leaf.velocity.y = -2.5f;
				fallingLeaf.velocity.y = -2.5f;
			} else if (treeIsShaking) {
				float leafDropThreshold;

				if (World.maxGameDifficulty)
					leafDropThreshold = 10.3f;
				else
					leafDropThreshold = 6.0f;

				Leaf oldLeaf = leaves.get(i - 1);
				if ((oldLeaf.position.y < leafDropThreshold)
						&& (leaf.velocity.y == 0)) {
					fallingLeaf.position.x = leaf.position.x;
					fallingLeaf.position.y = leaf.position.y;
					leaf.velocity.y = -2.5f;
					fallingLeaf.velocity.y = -2.5f;
					fallingLeafCount++;
					leaf.position.x = -2.0f;
					if (i == len - 1) {
						treeStillHasLeaves = false;
						state = WORLD_STATE_GAME_OVER;
					}
				} else if (leaf.position.y > 9.5f) // only the leaves on tree
													// shake
				{
					if (leavesShakeRight)
						leaf.velocity.x = 0.7f;
					else
						leaf.velocity.x = -0.7f;
				}
				if (i == len - 1) // use last leaf to toggle directions
				{
					if (leaf.position.x > -1.37f)
						leavesShakeRight = false;
					else if (leaf.position.x < -1.43f)
						leavesShakeRight = true;
				}
			}
			leaf.update(deltaTime);
		}
	}

	private void updateApples(float deltaTime) {
		int len = apples.size();
		for (int i = 0; i < len; i++) {
			Apple apple = apples.get(i);

			switch (apple.status) {
			case Apple.APPLE_ON_TREE:
				if ((birdNum == i + 2) && // no apple drop for first two birds
						(fallingLeafCount > thisBirdsInitialLeafCount + 8)) {
					apple.status = Apple.APPLE_FALLING;
					apple.velocity.y = -4.0f;
					thisBirdsInitialLeafCount += 1024; // deactivate for safety
				}
				if ((treeIsShaking) && (treeStillHasLeaves)) {
					if (leavesShakeRight) // from updateLeaves above
						apple.velocity.x = 1.0f;
					else
						apple.velocity.x = -1.0f;
				} else
					apple.velocity.x = 0.0f;
				break;
			case Apple.APPLE_FALLING:
				if (apple.position.y < 0.4) {
					apple.status = Apple.APPLE_OFFSCREEN;
					apple.velocity.y = 0.0f;
				} else if (apple.position.y < 1.4f) {
					float apx = apple.position.x;
					float cpx = cannon.position.x;
					float tol = 0.7f;

					if ((apx > cpx - tol) && (apx < cpx + tol)) {
						apple.status = Apple.APPLE_HIT_CANNON;
						apple.position.y = 1.4f; // apple loaded on cannon
						apple.velocity.y = 0.0f;
						apple.ballastStartTime = System.nanoTime();
						haveBallast = true;
						listener.highJump();
						score += 100;
					}
				}
				break;
			case Apple.APPLE_HIT_CANNON:
				double currentTime = System.nanoTime();
				// power up time in seconds
				double calmTime = (currentTime - apple.ballastStartTime) / 1000000000.0f;
				if (calmTime > 6.0f) {
					haveBallast = false;
					apple.position.y = -7.0f;
					apple.status = Apple.APPLE_OFFSCREEN;
				}
				apple.position.x = cannon.position.x;
				break;
			default:
				break;
			}
			apple.update(deltaTime);
		}
	}

	private void updateWoodpeckers(float deltaTime) {
		woodpecker.update(deltaTime);
	}

	private void updateNet(float deltaTime) {
		net.update(deltaTime);
	}

	private void updateNetted(float deltaTime) {
		netted.update(deltaTime);
	}

	private void updateBirdFlying(float deltaTime) {

		switch (gameState) {
		case GAME_STATE_OFFSCREEN:
			netted.velocity.y = 0.0f;
			birdFlying.velocity.x = 1.5f;
			birdFlying.velocity.y = 1.5f;
			if (birdFlying.position.x > 0.0f) {
				birdFlying.position.y = 7.0f;
				birdFlying.velocity.y = 0.8f;
				birdFlying.velocity.x = 2.0f;
				woodpecker.position.x = -1.0f; // done with so put offscreen
				woodpecker.position.y = -1.0f;
				gameState = GAME_STATE_FLYING;
			}
			break;

		case GAME_STATE_FLYING:
			if (birdFlying.position.x > 4.2f) {
				birdFlying.velocity.x = 1.0f;
				birdFlying.velocity.y = 1.0f;
				gameState = GAME_STATE_LANDING;
			}
			break;

		case GAME_STATE_LANDING:
			if (birdFlying.position.x > 4.35f) {
				birdFlying.velocity.x = 0.0f;
				birdFlying.velocity.y = 0.0f;
				birdFlying.position.x = -8.0f;
				birdFlying.position.y = 5.2f;
				woodpecker.position.x = 4.85f;
				woodpecker.position.y = 9.0f;
				net.position.y = 1.6f;
				net.position.x = -2.0f;
				thisBirdsInitialLeafCount = fallingLeafCount;
				gameState = GAME_STATE_PECKING;
			}
			break;

		case GAME_STATE_PECKING:
			if (cannonAligned) {
				net.velocity.y = -0.2f;
				if (net.position.y < 1.4f) {
					VibrateStrength(33);
					gameState = GAME_STATE_ACQUIRE1;
				}
			} else {
				net.position.y = 1.6f; // lost alignment, so reset
			}
			break;

		case GAME_STATE_ACQUIRE1:
			if (!cannonAligned) {
				net.position.y = 1.6f; // lost alignment, so reset
				VibrateStrength(0);
				gameState = GAME_STATE_PECKING;
			} else if (net.position.y < 1.2f) {
				VibrateStrength(66);
				gameState = GAME_STATE_ACQUIRE2;
			}
			break;

		case GAME_STATE_ACQUIRE2:
			if (!cannonAligned) {
				net.position.y = 1.6f; // lost alignment, so reset
				VibrateStrength(0);
				gameState = GAME_STATE_PECKING;
			} else if (net.position.y < 1.0f) {
				VibrateStrength(100);
				gameState = GAME_STATE_ACQUIRE3;
			}
			break;

		case GAME_STATE_ACQUIRE3:
			if (net.position.y < 0.9f) {
				net.velocity.y = 10.0f;
				net.position.y = 1.0f;
				net.position.x = cannon.position.x;
				thisBirdsInitialLeafCount += 1024; // deactivate for safety
				listener.netFire();
				VibrateStrength(0);
				gameState = GAME_STATE_NET_LAUNCHED;
			}
			break;

		case GAME_STATE_NET_LAUNCHED:
			if (net.position.y > 8.5f) {
				net.velocity.y = 0.0f;
				net.position.y = 17.0f;
				woodpecker.position.y = 17.0f;
				netted.position.y = 9.0f;
				netted.velocity.y = -6.0f;
				listener.birdCry();
				score += 1000;
				gameState = GAME_STATE_NETTED;
			}
			break;

		case GAME_STATE_NETTED:
			if (netted.position.y < 0.0f) {
				netted.velocity.y = -0.3f; // add delay between birds
				if (birdNum++ > 3) // Game over logic
				{
					listener.jump();
					int len = apples.size();
					for (int i = 0; i < len; i++) {
						Apple apple = apples.get(i);
						if (apple.position.y > 10)
							score += 650; // bonus for each apple still on tree
					}
					score += (55 * (87 - fallingLeafCount)); // bonus score for
					state = WORLD_STATE_WIN_SEQUENCE; // remaining leaves
				}
				gameState = GAME_STATE_NETTED_SLOWDOWN;
			}
			break;

		case GAME_STATE_NETTED_SLOWDOWN:
			if (netted.position.y < -1.0f) {
				netted.velocity.y = 0.0f;
				gameState = GAME_STATE_OFFSCREEN;
			}
			break;
		default:
			throw new UnsupportedOperationException("Update Bird Flying bad case");
		}
		birdFlying.update(deltaTime);
	}

	public static void VibrateStrength(int level) {
		try {
			switch (level) {
			case 33:
				Treeshake.mLauncher.play(Launcher.ENGINE3_33);
				break;
			case 66:
				Treeshake.mLauncher.play(Launcher.ENGINE3_66);
				break;
			case 100:
				Treeshake.mLauncher.play(Launcher.ENGINE3_100);
				break;
			default:
				Treeshake.mLauncher.stop();
				break;
			}
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
	}
}
