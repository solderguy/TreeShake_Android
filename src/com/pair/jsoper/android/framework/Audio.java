/**
 * @author Mario Zechner
 * 
 * Gaming framework provided from book "Beginning Android Games"
 */

package com.pair.jsoper.android.framework;

public interface Audio {
	public Music newMusic(String filename);

	public Sound newSound(String filename);
}
