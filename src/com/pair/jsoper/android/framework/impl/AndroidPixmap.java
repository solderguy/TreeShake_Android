/**
 * @author Mario Zechner
 * 
 * Gaming framework provided from book "Beginning Android Games"
 */

package com.pair.jsoper.android.framework.impl;

import android.graphics.Bitmap;

import com.pair.jsoper.android.framework.Graphics.PixmapFormat;
import com.pair.jsoper.android.framework.Pixmap;

public class AndroidPixmap implements Pixmap {
	Bitmap bitmap;
	PixmapFormat format;

	public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
		this.bitmap = bitmap;
		this.format = format;
	}

	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}

	@Override
	public int getHeight() {
		return bitmap.getHeight();
	}

	@Override
	public PixmapFormat getFormat() {
		return format;
	}

	@Override
	public void dispose() {
		bitmap.recycle();
	}
}
