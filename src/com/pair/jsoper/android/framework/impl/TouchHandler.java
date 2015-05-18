/**
 * @author Mario Zechner
 * 
 * Gaming framework provided from book "Beginning Android Games"
 */

package com.pair.jsoper.android.framework.impl;

import java.util.List;

import android.view.View.OnTouchListener;

import com.pair.jsoper.android.framework.Input.TouchEvent;

public interface TouchHandler extends OnTouchListener {
	public boolean isTouchDown(int pointer);

	public int getTouchX(int pointer);

	public int getTouchY(int pointer);

	public List<TouchEvent> getTouchEvents();
}
