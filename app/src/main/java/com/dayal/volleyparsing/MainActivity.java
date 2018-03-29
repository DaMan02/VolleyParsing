package com.dayal.volleyparsing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private final static String STRING_URL= "https://justpaste.it/1id4b";
    private final static String ARRAY_URL="http://mysafeinfo.com/api/data?list=englishmonarchs&format=json";
    private final static String EQ_URL=" http://www.json-generator.com/api/json/get/cekVOwHqsy?indent=2";
    private RequestQueue queue;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue= Volley.newRequestQueue(this);
        textView=(TextView)findViewById(R.id.textView);
         // getArrayObject(ARRAY_URL);
        //getStringObject(STRING_URL);
        getJsonObject(EQ_URL);

    }

    public void getStringObject(String url){

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 Log.w("my string",response);
                textView.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Error !");
            }
        });
        queue.add(stringRequest);
    }

    public void getJsonObject(String url){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject metadata=response.getJSONObject("metadata");
                    Log.w("response","Metadata: ");
                    Log.w("response","generated:" + metadata.getString("generated"));
                    Log.w("response","title:" + metadata.getString("title"));
                    // JSON Array
                    JSONArray features=response.getJSONArray("features");
                     // get each object
                    for(int i=0;i<features.length();i++){
                        JSONObject obj=features.getJSONObject(i).getJSONObject("properties");
                        Log.w("response","Properties:");
                        Log.w("response","mag: " + obj.getString("mag"));
                        Log.w("response","place: " + obj.getString("place"));

                    }

                } catch (JSONException e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }
    public void getArrayObject(String url){
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 for(int i=0;i<response.length();i++){             // retrieving i-th {obj} of JSON array[]
                     try {
                         JSONObject jObj=response.getJSONObject(i);
                         Log.w("response", "Name: " + jObj.getString("nm"));

                     } catch (JSONException e) {

                     }
                 }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
   queue.add(arrayRequest);
        }
}
