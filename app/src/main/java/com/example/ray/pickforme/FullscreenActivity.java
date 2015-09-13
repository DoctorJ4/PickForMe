package com.example.ray.pickforme;

import com.example.ray.pickforme.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
    List<Integer> editBoxIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        editBoxIdList.add(R.id.editText0);
        editBoxIdList.add(R.id.editText1);
        editBoxIdList.add(R.id.editText2);
        editBoxIdList.add(R.id.editText3);
        editBoxIdList.add(R.id.editText4);
        editBoxIdList.add(R.id.editText5);
        editBoxIdList.add(R.id.editText6);
        editBoxIdList.add(R.id.editText7);
        editBoxIdList.add(R.id.editText8);
        editBoxIdList.add(R.id.editText9);
        //String [] categories = {"2", "3", "4", "5", "6", "7", "8", "9", "10"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.meal_items, categories);
        //numberList = (ExpandableListView)findViewById(R.id.numberList);
        //numberList.setAdapter(adapter);

    }

    public void PickOne (View view)
    {

        TextView result = (TextView) findViewById(R.id.Result);
        //int num = Integer.getInteger(numberList.getSelectedItem().toString());

        int num = editBoxIdList.size();
        Random rand = new Random();

        EditText picked;
        Log.d("I made it!", String.valueOf(1));
        do {
            picked = (EditText) findViewById(editBoxIdList.get(rand.nextInt(num)));
            Log.d("I made it!", String.valueOf(2));
            Log.d("I made it!", String.valueOf(picked.getText().length() < 1));
            //Log.d("I made it!", String.valueOf(picked.getHint() != null));
        }while((picked.getText().length() < 1));
        Log.d("I made it!", String.valueOf(3));
        Log.d("I made it!", String.valueOf(picked.getText()));
        result.setText(picked.getText());

    }



}
