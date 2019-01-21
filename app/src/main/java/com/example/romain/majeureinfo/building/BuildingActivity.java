package com.example.romain.majeureinfo.building;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.romain.majeureinfo.R;
import com.example.romain.majeureinfo.room.RoomActivity;

import static android.view.Gravity.*;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class BuildingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        final BuildingContextHTTPManager buildingContextHTTPManager = new BuildingContextHTTPManager(this);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
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
                                                                buildingContextHTTPManager.retrieveBuildingContextState();                                                            }
                                                        }, 3000);
                                                    }
                                                });
        buildingContextHTTPManager.retrieveBuildingContextState();
    }



    protected void addButton(final BuildingContextState building) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout2);
        Button button = new Button(this);
        button.setHeight(WRAP_CONTENT);
        button.setWidth(WRAP_CONTENT);
        button.setGravity(CENTER_VERTICAL|CENTER_HORIZONTAL);
        button.setText(building.getName());
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(BuildingActivity.this, RoomActivity.class);
                intent.putExtra("buildingId", building.getId());
                intent.putExtra("name", building.getName());
                startActivity(intent);
            }
        });
        layout.addView(button);
    }
}
