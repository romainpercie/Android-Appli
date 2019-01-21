package com.example.romain.majeureinfo.light;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romain.majeureinfo.R;
import com.example.romain.majeureinfo.RoomContextRule;
import com.example.romain.majeureinfo.room.RoomContextState;

import java.util.ArrayList;
import java.util.List;

public class LightActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RoomContextState room;
    private List<String> LIGHTID = new ArrayList<String>();
    private String lightId;
    private LightContextHTTPManager lightContextHTTPManager = new LightContextHTTPManager(this);
    final ArrayList<RoomContextRule> rules = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        //Get Intent information
        this.room = new RoomContextState(getIntent().getStringExtra("name"), getIntent().getIntExtra("roomId", 0));

        //Set Title
        TextView text = (TextView) findViewById(R.id.textView1);
        String msg = this.room.getName()+" #";
        text.setText(msg);

        //Customize ActionBar
        ActionBar toolbar = this.getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(false);

        //Spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        LIGHTID.add("Select ID");
        lightContextHTTPManager.retrieveAllLightInRoom(room.getId());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, LIGHTID);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        //Button
        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lightId != null) {
                    lightContextHTTPManager.updateLightStatus(lightId);
                    ImageView image = (ImageView) findViewById(R.id.imageView1);
                    if (image.getContentDescription().equals("ON")) {
                        image.setImageResource(R.drawable.ic_bulb_off);
                        image.setContentDescription("OFF");
                    } else {
                        image.setImageResource(R.drawable.ic_bulb_on);
                        image.setContentDescription("ON");
                    }
                }
            }
        });

        //Rules
        rules.add(new RoomContextRule() {

            @Override
            public void apply(LightContextState lightContextState) {
                super.apply(lightContextState);
                if (condition(lightContextState))
                    Toast.makeText(getBaseContext(), this +" applies: silent mode switched on!", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected boolean condition(LightContextState lightContextState) {
                return Integer.parseInt(lightContextState.getLevel()) > 100;
            }

            @Override
            protected void action() {
                ((AudioManager) getApplicationContext().getSystemService(
                        Context.AUDIO_SERVICE))
                        .setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
            public String toString() {
                return "Rule 1";
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        //Test Rule 1
        rules.get(0).apply(new LightContextState("1","ON","200"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        if(item.equals("Select ID")){
            lightId = null;
            TextView text = (TextView) findViewById(R.id.textViewLightValue);
            text.setText("");
            ImageView image = (ImageView) findViewById(R.id.imageView1);
            image.setImageResource(R.drawable.ic_bulb_on);
            image.setContentDescription("ON");
        }else {
            lightId = item;
            lightContextHTTPManager.retrieveLightContextState(lightId);
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public List<String> getLIGHTID() {
        return LIGHTID;
    }
}
