package com.example.ray.pickforme.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class NativelyCustomTextViewDelete extends TextView {

    public NativelyCustomTextViewDelete(Context context) {
        super(context);
        setTypeface(OpenSansDelete.getInstance(context).getTypeFace());
    }

    public NativelyCustomTextViewDelete(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(OpenSansDelete.getInstance(context).getTypeFace());
    }

    public NativelyCustomTextViewDelete(Context context, AttributeSet attrs,
                                        int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(OpenSansDelete.getInstance(context).getTypeFace());
    }

}