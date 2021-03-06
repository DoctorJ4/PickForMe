package com.example.ray.pickforme.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class NativelyCustomTextView extends TextView {

    public NativelyCustomTextView(Context context) {
        super(context);
        setTypeface(OpenSans.getInstance(context).getTypeFace());
    }

    public NativelyCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(OpenSans.getInstance(context).getTypeFace());
    }

    public NativelyCustomTextView(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(OpenSans.getInstance(context).getTypeFace());
    }

}