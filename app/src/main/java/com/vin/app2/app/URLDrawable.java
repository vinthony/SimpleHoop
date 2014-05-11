package com.vin.app2.app;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class URLDrawable extends BitmapDrawable {

	protected Drawable drawable;

	@Override
	public void draw(Canvas canvas) {
		
		if (drawable != null) {
			drawable.draw(canvas);
		}
	}
}
