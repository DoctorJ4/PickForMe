package com.example.ray.pickforme;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListViewActivity extends Activity {
    List<Integer> editBoxIdList = new ArrayList<>();
    DBHelper dbHelper;
    PickList list = new PickList();
    LinearLayout listLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        dbHelper = new DBHelper(this);
        list = dbHelper.getPickList(extras.getInt("ID"), extras.getString("Name"), extras.getInt("Size"));
        setContentView(R.layout.activity_listview);
        setTitle(list.Name);

        listLayout = (LinearLayout)findViewById(R.id.scrollList);
        CustomEditText newBox;

        if(list.Size < 0){
            list.Name = "New List";
            list.Size = 10;
            for(int i = 0; i < list.Size; i++)
                list.Contents.add("");
        }

        for(int i = 0; i < list.Size; i++) {
            Log.d("Test here contents", list.Contents.get(i));
            newBox = new CustomEditText(this);
            newBox.setText(list.Contents.get(i));
            newBox.setId(newBox.generateViewId());
            editBoxIdList.add(newBox.getId());
            listLayout.addView(newBox);
        }

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

    public void SaveList(View view)
    {
        if(!fieldsCheck())
        {
            Toast.makeText(this, "You need to supply me with more than one thing to pick from.", Toast.LENGTH_SHORT).show();
        }
        else {
            List<String> saveList = new ArrayList<>();
            EditText picked;
            String temp;
            for (int i = 0; i < editBoxIdList.size(); i++) {
                picked = (EditText) findViewById(editBoxIdList.get(i));
                if (picked.getText().length() > 0) {
                    saveList.add(picked.getText().toString());
                }
            }
            dbHelper.addList("New List!!!", saveList);
            Toast.makeText(this, "List Saved", Toast.LENGTH_SHORT).show();
        }
    }

}
