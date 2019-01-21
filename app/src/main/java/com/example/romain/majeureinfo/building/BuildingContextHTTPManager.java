package com.example.romain.majeureinfo.building;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class BuildingContextHTTPManager {

    private BuildingActivity buildingActivity;

    public BuildingContextHTTPManager(BuildingActivity buildingActivity){
        this.buildingActivity = buildingActivity;
    }

    public void retrieveBuildingContextState(){
        //Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(buildingActivity);
        String url ="https://romain-app.cleverapps.io/api/buildings";

        JsonArrayRequest contextRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response){
                        try {
                            for( int i=0; i<response.length(); i++) {
                                String name = response.getJSONObject(i).get("name").toString();
                                int id = Integer.parseInt(response.getJSONObject(i).get("id").toString());
                                BuildingContextState building = new BuildingContextState(name, id);
                                buildingActivity.addButton(building);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(buildingActivity.getBaseContext(), "Don't work", Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(contextRequest);
    }


}
