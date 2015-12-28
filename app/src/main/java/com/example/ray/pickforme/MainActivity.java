package com.example.ray.pickforme;

import com.example.ray.pickforme.util.SystemUiHider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainActivity extends Activity {
    private DBHelper dbHelper;
    private List<PickList> PickLists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        PickLists.addAll(dbHelper.getPickListsNameIdSize());
        createPickList();
    }

    public void NewList(View view)
    {
        Intent act = new Intent(getApplicationContext(), ListViewActivity.class);
        act.putExtra("Name", "New");
        act.putExtra("ID", "-1");
        act.putExtra("Size", "0");
        startActivity(act);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        PickLists.clear();
        PickLists.addAll(dbHelper.getPickListsNameIdSize());
        createPickList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void createPickList(){
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

    private List<String> getListNames(){
        List<String> names = new ArrayList<>();

        for(int i = 0; i < PickLists.size(); i++)
        {
            names.add(PickLists.get(i).Name);
        }
        return names;
    }
}
