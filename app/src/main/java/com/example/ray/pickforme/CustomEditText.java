package com.example.ray.pickforme;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;

/**
 * Created by Jesse on 12/27/2015.
 */
public class CustomEditText extends EditText {

    public CustomEditText(Context context)
    {
        super(context);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setShadowLayer(4, 2, 2, Color.DKGRAY);
        //this.setTextColor(getResources().getColor(R.color.black_overlay));
        this.setTextColor(getResources().getColor(android.R.color.white));
        //this.setTextAppearance(context, android.R.style.TextAppearance_Large);
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        this.setPadding(0, 4, 0, 4);
        //this.setWidth(300);
        this.setHint("x???x");
        this.setGravity(Gravity.CENTER);
        //this.layout(0, 10, 0, 10);
    }
}
