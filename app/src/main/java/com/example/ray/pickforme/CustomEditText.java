package com.example.ray.pickforme;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;
import android.widget.EditText;

/**
 * Created by Jesse on 12/27/2015.
 */
public class CustomEditText extends EditText {

    public CustomEditText(Context context)
    {
        super(context);
        this.setBackgroundColor(Color.BLACK);
        //this.setTextColor(getResources().getColor(R.color.black_overlay));
        //this.setTextColor(getResources().getColor(android.R.color.black));
        this.setTextAppearance(context, android.R.style.TextAppearance_Large);
        this.setPadding(0, 10, 0, 10);
        //this.setWidth(300);
        this.setHint("x???x");
        this.setGravity(Gravity.CENTER);
        //this.layout(0, 10, 0, 10);
    }
}