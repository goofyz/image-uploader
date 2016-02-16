package com.thirtysparks.imageuploader.app.util;

import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by Ryan on 16/2/2016.
 */
public class Util {


    public static int convertDipToPix(DisplayMetrics displayMetrics, float dip) {
        return convertUnits(displayMetrics, dip, TypedValue.COMPLEX_UNIT_DIP);
    }

    public static int convertUnits(DisplayMetrics displayMetrics, float val, int unit) {
        int value = (int) TypedValue.applyDimension(unit, val, displayMetrics);
        return value;
    }
}
