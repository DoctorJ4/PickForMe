package com.example.ray.pickforme.util;

import android.content.Context;
import android.graphics.Typeface;

public class OpenSans {

    private static OpenSans instance;
    private static Typeface typeface;

    public static OpenSans getInstance(Context context) {
        synchronized (OpenSans.class) {
            if (instance == null) {
                instance = new OpenSans();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "Deutsch.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}