/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.pair.jsoper.android.framework.FileIO;

public class Settings {
	public static boolean soundEnabled = true;
	public final static int[] highscores = new int[] { 0, 0, 0, 0, 0 };
	public final static int[] easyHighscores = new int[] { 0, 0, 0, 0, 0 };
	public final static String file = ".superjumper";

	public static void load(FileIO files) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(files.readFile(file)));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			for (int i = 0; i < 5; i++) {
				highscores[i] = Integer.parseInt(in.readLine());
			}
			for (int i = 0; i < 5; i++) {
				easyHighscores[i] = Integer.parseInt(in.readLine());
				// Log.d("easyHigh",""+easyHighscores[i]);
			}
		} catch (IOException e) {
			// :( It's ok we have defaults
		} catch (NumberFormatException e) {
			// :/ It's ok, defaults save our day
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	public static void save(FileIO files) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					files.writeFile(file)));
			out.write(Boolean.toString(soundEnabled));
			out.write("\n");
			for (int i = 0; i < 5; i++) {
				out.write(Integer.toString(highscores[i]));
				out.write("\n");
			}
			for (int i = 0; i < 5; i++) {
				out.write(Integer.toString(easyHighscores[i]));
				out.write("\n");
			}

		} catch (IOException e) {
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}

	public static void addScore(int score) {
		//Log.d("maxGameDifficulty", "" + World.maxGameDifficulty);
		//Log.d("score", "" + score);
		for (int i = 0; i < 5; i++) {
			if (World.maxGameDifficulty) {
				if (highscores[i] < score) {
					for (int j = 4; j > i; j--)
						highscores[j] = highscores[j - 1];
					highscores[i] = score;
					break;
				}
			} else {
				if (easyHighscores[i] < score) {
					for (int j = 4; j > i; j--)
						easyHighscores[j] = easyHighscores[j - 1];
					easyHighscores[i] = score;
					break;
				}
			}
		}

	}
}
