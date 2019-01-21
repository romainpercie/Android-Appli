package com.example.romain.majeureinfo.light;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.romain.majeureinfo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LightContextHTTPManager {

    private LightActivity lightActivity;


    public LightContextHTTPManager(LightActivity lightActivity){
        this.lightActivity = lightActivity;
    }

    public void retrieveLightContextState(final String lightId){
        final RequestQueue queue = Volley.newRequestQueue(lightActivity);
        String url ="https://romain-app.cleverapps.io/api/lights/"+ lightId;

        final JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            String level = response.get("level").toString();
                            String status = response.get("status").toString();
                            TextView text = (TextView) lightActivity.findViewById(R.id.textViewLightValue);
                            text.setText(level);
                            ImageView image = (ImageView) lightActivity.findViewById(R.id.imageView1);
                            if(status.equals("ON")){
                                image.setImageResource(R.drawable.ic_bulb_on);
                                image.setContentDescription("ON");
                            }
                            else{
                                image.setImageResource(R.drawable.ic_bulb_off);
                                image.setContentDescription("OFF");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(lightActivity.getBaseContext(), "Don't work", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(contextRequest);
    }

    public void updateLightStatus(String lightId){
        final RequestQueue queue = Volley.newRequestQueue(lightActivity);
        String url ="https://romain-app.cleverapps.io/api/lights/"+ lightId +"/switch";

        final JsonObjectRequest putRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Toast.makeText(lightActivity.getBaseContext(), "ok", Toast.LENGTH_SHORT).show();
                    }
                },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(lightActivity.getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
        queue.add(putRequest);
    }

    public void retrieveAllLightInRoom(final int roomId){
        final RequestQueue queue = Volley.newRequestQueue(lightActivity);
        String url ="https://romain-app.cleverapps.io/api/lights";

        final JsonArrayRequest contextRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response){
                        try {
                            for( int i=0; i<response.length(); i++){
                                String id = response.getJSONObject(i).get("id").toString();
                                if( Integer.parseInt(response.getJSONObject(i).get("roomId").toString()) == roomId){
                                    lightActivity.getLIGHTID().add(id);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(lightActivity.getBaseContext(), "Select ID please", Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(contextRequest);
    }
}
