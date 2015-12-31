package com.example.ray.pickforme;

import com.example.ray.pickforme.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.ray.pickforme.R.drawable.beats2;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainActivity extends Activity {
    private DBHelper dbHelper;
    private List<PickList> PickLists = new ArrayList<>();
    private boolean deleteToggle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        PickLists.addAll(dbHelper.getPickListsNameIdSize());
        createPickList(deleteToggle);
    }

    public void NewList(View view)
    {
        Intent act = new Intent(getApplicationContext(), ListViewActivity.class);
        act.putExtra("Name", "New");
        act.putExtra("ID", "-1");
        act.putExtra("Size", "0");
        startActivity(act);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void DeleteList(View view)
    {

        if(!deleteToggle){
            deleteToggle = true;
            final RelativeLayout back = (RelativeLayout)findViewById(R.id.BackgroundMain);
            back.setBackground( this.getResources().getDrawable(R.drawable.beats3, null) ); //TODO: CREATE THEMES FOR THIS TO CHANGE CORRECTLY

        }
        else{
            deleteToggle = false;
            final RelativeLayout back = (RelativeLayout)findViewById(R.id.BackgroundMain);
            back.setBackground( this.getResources().getDrawable(beats2, null) );
        }
        createPickList(deleteToggle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PickLists.clear();
        PickLists.addAll(dbHelper.getPickListsNameIdSize());
        createPickList(deleteToggle);
    }

    private void createPickList(final boolean deleteFlag){
        if(!deleteFlag) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_items, getListNames());
            final ListView planTag = (ListView) findViewById(R.id.PickLists);
            planTag.setAdapter(adapter);

            planTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent act = new Intent(getApplicationContext(), ListViewActivity.class);
                    act.putExtra("Name", PickLists.get(position).Name);
                    act.putExtra("ID", PickLists.get(position).ID);
                    act.putExtra("Size", PickLists.get(position).Size);
                    startActivity(act);
                }
            });
        }
        else{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.delete_list_items, getListNames());
            final ListView planTag = (ListView) findViewById(R.id.PickLists);
            planTag.setAdapter(adapter);
            planTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    makeSure(position);
                }
            });
        }
    }

    private void makeSure(final int position){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteList(PickLists.get(position).ID);
                        onResume();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }

    private List<String> getListNames(){
        List<String> names = new ArrayList<>();

        for(int i = 0; i < PickLists.size(); i++)
        {
            names.add(PickLists.get(i).Name);
        }
        return names;
    }
}
