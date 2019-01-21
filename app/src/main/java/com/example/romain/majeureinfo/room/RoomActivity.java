package com.example.romain.majeureinfo.room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.romain.majeureinfo.R;
import com.example.romain.majeureinfo.building.BuildingContextState;
import com.example.romain.majeureinfo.light.LightActivity;

import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.Gravity.CENTER_VERTICAL;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class RoomActivity extends AppCompatActivity {

    private BuildingContextState building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        final RoomContextHTTPManager roomContextHTTPManager = new RoomContextHTTPManager(this);

        ActionBar toolbar = this.getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(false);

        this.building = new BuildingContextState(getIntent().getStringExtra("name"), getIntent().getIntExtra("buildingId", 0));

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);

        TextView text = (TextView) findViewById(R.id.textView1);
        String msg = this.building.getName() + " #";
        text.setText(msg);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh, R.color.refresh1, R.color.refresh2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout2);
                        layout.removeAllViewsInLayout();
                        roomContextHTTPManager.retrieveRoomContextState(building.getId());                                                            }
                }, 3000);
            }
        });
        roomContextHTTPManager.retrieveRoomContextState(this.building.getId());

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

    protected void addButton(final RoomContextState room) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout2);
        Button button = new Button(this);
        button.setHeight(WRAP_CONTENT);
        button.setWidth(WRAP_CONTENT);
        button.setGravity(CENTER_VERTICAL|CENTER_HORIZONTAL);
        button.setText(room.getName());
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RoomActivity.this, LightActivity.class);
                intent.putExtra("name", room.getName());
                intent.putExtra("roomId", room.getId());
                startActivity(intent);
            }
        });
        layout.addView(button);
    }
}
