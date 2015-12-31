package com.example.ray.pickforme.util;

import android.content.Context;
import android.graphics.Typeface;

public class OpenSansDelete {

    private static OpenSansDelete instance;
    private static Typeface typeface;

    public static OpenSansDelete getInstance(Context context) {
        synchronized (OpenSansDelete.class) {
            if (instance == null) {
                instance = new OpenSansDelete();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "gothic_ultra_ot.otf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}