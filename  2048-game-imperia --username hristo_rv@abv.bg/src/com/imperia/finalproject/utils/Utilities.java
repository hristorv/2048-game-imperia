package com.imperia.finalproject.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Utilities {
	
	/**
	 * This method converts dp unit to equivalent pixels, depending on device
	 * density.
	 * 
	 * @param dp
	 *            A value in dp (density independent pixels) unit. Which we need
	 *            to convert into pixels
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A int value to represent px equivalent to dp depending on device
	 *         density
	 */
	public static int dpToPx(int dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		int px = Math.round(dp
				* (metrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}

	/**
	 * This method converts device specific pixels to density independent
	 * pixels.
	 * 
	 * @param px
	 *            A value in px (pixels) unit. Which we need to convert into db
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A int value to represent dp equivalent to px value
	 */
	public static int pxToDp(int px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		int dp = Math.round(px
				/ (metrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return dp;
	}

}
