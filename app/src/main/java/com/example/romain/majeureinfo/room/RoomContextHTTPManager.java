package com.example.romain.majeureinfo.room;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class RoomContextHTTPManager {

    private RoomActivity roomActivity;

    public RoomContextHTTPManager(RoomActivity roomActivity){
        this.roomActivity = roomActivity;
    }

    public void retrieveRoomContextState(final int buildingId){
        //Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(roomActivity);
        String url ="https://romain-app.cleverapps.io/api/rooms";

        final JsonArrayRequest contextRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response){
                        try {
                            for( int i=0; i<response.length(); i++) {
                                String name = response.getJSONObject(i).get("name").toString();
                                int id = Integer.parseInt(response.getJSONObject(i).get("id").toString());
                                if( Integer.parseInt(response.getJSONObject(i).get("buildingId").toString()) == buildingId){
                                    RoomContextState room = new RoomContextState(name,id);
                                    roomActivity.addButton(room);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(roomActivity.getBaseContext(), "Don't work", Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(contextRequest);
    }
}
