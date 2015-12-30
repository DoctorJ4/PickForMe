package com.example.ray.pickforme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    Context thisContext = this;

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
            list.Size = 100;
            for(int i = 0; i < list.Size; i++)
                list.Contents.add("");
        }

        if(list.Size < 100)
        {
            for(int i = list.Size; i < 100; i++) {
                list.Contents.add("");
            }
            list.Size = 100;
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

    public void OverwriteList(View view)
    {
        getNameAndOverwrite();
    }

    public void SaveList(View view)
    {
        if(!fieldsCheck())
        {
            Toast.makeText(this, "You need to supply me with more than one thing to pick from.", Toast.LENGTH_SHORT).show();
        }
        else {
            getNameAndAdd();
        }
    }

    private void getNameAndAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String getNewName = input.getText().toString();
                List<String> saveList = new ArrayList<>();
                EditText picked;
                for (int i = 0; i < editBoxIdList.size(); i++) {
                    picked = (EditText) findViewById(editBoxIdList.get(i));
                    if (picked.getText().length() > 0) {
                        saveList.add(picked.getText().toString());
                    }
                }
                list.ID = dbHelper.addList(getNewName, saveList);
                list.Name = getNewName;
                setTitle(getNewName);
                Toast.makeText(thisContext, "List Saved", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void getNameAndOverwrite(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(this);
        input.setText(list.Name);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String getNewName = input.getText().toString();
                List<String> saveList = new ArrayList<>();
                EditText picked;
                for (int i = 0; i < editBoxIdList.size(); i++) {
                    picked = (EditText) findViewById(editBoxIdList.get(i));
                    if (picked.getText().length() > 0) {
                        saveList.add(picked.getText().toString());
                    }
                }
                list.ID = dbHelper.overwriteList(list.ID, getNewName, saveList);
                list.Name = getNewName;
                setTitle(getNewName);
                Toast.makeText(thisContext, "List Overwritten", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
