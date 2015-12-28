package com.example.ray.pickforme;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListViewActivity extends Activity {
    List<Integer> editBoxIdList = new ArrayList<>();
    LinearLayout newListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String name = extras.getString("Name");
        int ID = extras.getInt("ID");
        int size = extras.getInt("Size");
        setContentView(R.layout.activity_listview);
        setTitle(name);

        newListLayout = (LinearLayout)findViewById(R.id.editListLayout);
        //EditText newBox = new EditText(new ContextThemeWrapper(this, R.layout.editTextBox), null, 0);// getBaseContext(), null, R.style.EditTextViewStyle);
        //EditText newBox = (EditText)findViewById(R.layout.editTextBox);
        //newBox.setTextAppearance(this, R.style.EditTextViewStyle);
        //newListLayout.setOnClickListener(null);
        //newListLayout.addView(newBox);

        List<String> listTemp = new ArrayList<>();
        listTemp.add("one");
        listTemp.add("six");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.newlistbox, listTemp);
        final ListView planTag = (ListView) findViewById(R.id.ListBoxes);
        planTag.setAdapter(adapter);

        /*
        editBoxIdList.add(R.id.editText0);
        editBoxIdList.add(R.id.editText1);
        editBoxIdList.add(R.id.editText2);
        editBoxIdList.add(R.id.editText3);
        editBoxIdList.add(R.id.editText4);
        editBoxIdList.add(R.id.editText5);
        editBoxIdList.add(R.id.editText6);
        editBoxIdList.add(R.id.editText7);
        editBoxIdList.add(R.id.editText8);
        editBoxIdList.add(R.id.editText9);*/

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
