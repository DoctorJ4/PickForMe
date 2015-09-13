package com.example.ray.pickforme;

import com.example.ray.pickforme.util.SystemUiHider;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
        if(!fieldsCheck())
        {
            Toast.makeText(this, "You need to supply me with more than one thing to pick from.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            TextView result = (TextView) findViewById(R.id.Result);
            int num = editBoxIdList.size();
            Random rand = new Random();
            EditText picked;

            do {
                picked = (EditText) findViewById(editBoxIdList.get(rand.nextInt(num)));
            } while ((picked.getText().length() < 1));
            result.setText(picked.getText());
        }
    }

    Boolean fieldsCheck()
    {
        EditText picked;
        int count = 0;
        for(int i = 0; i < editBoxIdList.size(); i++)
        {
            picked = (EditText) findViewById(editBoxIdList.get(i));
            if(picked.getText().length() > 0)
            {
                count++;
                if(count > 1)
                {return true;}
            }
        }
        return false;
    }



}
