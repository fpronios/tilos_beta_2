package com.example.chris.tilos_beta_2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.ektorp.android.util.EktorpAsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;




public class MainActivity extends AppCompatActivity {


    private static TextView txt, txt1, txt2, txt3, txt4;
    private final Handler handler = new Handler();
    Button btn;
    Switch sw1, sw2, sw3;
    String r1 = "http://192.168.1.8:5984/control/1";
    String r2 = "http://192.168.1.8:5984/control/2";
    String r3 = "http://192.168.1.8:5984/control/3";
    String url2 = "http://192.168.1.8:5984/meas/_design/last/_view/new-view?limit=1";
    String url3 = "http://192.168.1.8:5984/control/_design/check/_view/new-view";
    String result;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //ActivityCompat.requestPermissions(MainActivity.this,
        //       new String[]{Manifest.permission.CAMERA}, 1);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.txt);
        btn = (Button) findViewById(R.id.btn);

        txt1 = (TextView) findViewById(R.id.showV);
        txt2 = (TextView) findViewById(R.id.showV1);
        txt3 = (TextView) findViewById(R.id.showV2);
        txt4 = (TextView) findViewById(R.id.showV3);

        sw1 = (Switch) findViewById(R.id.load1c);
        sw2 = (Switch) findViewById(R.id.load2c);
        sw3 = (Switch) findViewById(R.id.load3c);


        sw1.setOnClickListener(new View.OnClickListener() {
            loadSwitch lsw1;
            String rev;
            int newstat;

            @Override
            public void onClick(View view) {
                StringRequest revReq1 = new StringRequest(Request.Method.GET, r1, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {

                        Gson gson = new GsonBuilder().create();

                        lsw1 = gson.fromJson(s, loadSwitch.class);

                        if (sw1.isChecked())
                            newstat = 1;
                        else
                            newstat = 0;

                        //txt.setText(s);


                        JSONObject js = new JSONObject();
                        try {
                            JSONObject jsonobject = new JSONObject();
                            jsonobject.put("_rev", lsw1.getRev());
                            jsonobject.put("status", newstat);
                            js = jsonobject;
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonObjectRequest jsonObjReq1 = new JsonObjectRequest(
                                Request.Method.PUT,r1+"/", js,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("JSON", response.toString());
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d("JSON", "Error: " + error.getMessage());
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-type", "application/json");
                                headers.put("Accept","application/json");
                                return headers;
                            }};
                        addToRequestQueue(jsonObjReq1, "Json");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        txt.setText("Some error occurred!!");
                    }
                });

                addToRequestQueue(revReq1, "getRequest");

        }});

        sw2.setOnClickListener(new View.OnClickListener() {
            loadSwitch lsw2;
            String rev;
            int newstat;

            @Override
            public void onClick(View view) {
                StringRequest revReq = new StringRequest(Request.Method.GET, r2, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {

                        Gson gson = new GsonBuilder().create();

                        lsw2 = gson.fromJson(s, loadSwitch.class);

                        Log.d("LSWQ      ", lsw2.getRev());
                        if (sw2.isChecked())
                            newstat = 1;
                        else
                            newstat = 0;

                        //txt.setText(s);


                        JSONObject js = new JSONObject();
                        try {
                            JSONObject jsonobject = new JSONObject();
                            jsonobject.put("_rev", lsw2.getRev());
                            jsonobject.put("status", newstat);
                            js = jsonobject;
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(
                                Request.Method.PUT,r2+"/", js,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("JSON", response.toString());
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d("JSON", "Error: " + error.getMessage());
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-type", "application/json");
                                headers.put("Accept","application/json");
                                return headers;
                            }};
                        addToRequestQueue(jsonObjReq2, "Json");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        txt.setText("Some error occurred!!");
                    }
                });

                addToRequestQueue(revReq, "getRequest");

            }
        });

        sw3.setOnClickListener(new View.OnClickListener() {
            loadSwitch lsw3;
            int newstat;

            @Override
            public void onClick(View view) {
                StringRequest revReq3 = new StringRequest(Request.Method.GET, r3, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {

                        Gson gson = new GsonBuilder().create();
                        lsw3 = gson.fromJson(s, loadSwitch.class);
                        if (sw3.isChecked())
                            newstat = 1;
                        else
                            newstat = 0;

                        //txt.setText(s);

                        JSONObject js = new JSONObject();
                        try {
                            JSONObject jsonobject = new JSONObject();
                            jsonobject.put("_rev", lsw3.getRev());
                            jsonobject.put("status", newstat);
                            js = jsonobject;
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonObjectRequest jsonObjReq3 = new JsonObjectRequest(
                                Request.Method.PUT,r3+"/", js,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("JSON", response.toString());
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d("JSON", "Error: " + error.getMessage());
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-type", "application/json");
                                headers.put("Accept","application/json");
                                return headers;
                            }};
                        addToRequestQueue(jsonObjReq3, "Json");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        txt.setText("Some error occurred!!");
                    }
                });
                addToRequestQueue(revReq3, "getRequest");
            }
        });

        doTheAutoRefresh();

    }

    /**
     * Create a getRequestQueue() method to return the instance of
     * RequestQueue.This kind of implementation ensures that
     * the variable is instatiated only once and the same
     * instance is used throughout the application
     **/
    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());

        return requestQueue;
    }

    /**
     * public method to add the Request to the the single
     * instance of RequestQueue created above.Setting a tag to every
     * request helps in grouping them. Tags act as identifier
     * for requests and can be used while cancelling them
     **/
    public void addToRequestQueue(Request request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);

    }

    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txt = (TextView) findViewById(R.id.txt);
                btn = (Button) findViewById(R.id.btn);

                txt1 = (TextView) findViewById(R.id.showV);
                txt2 = (TextView) findViewById(R.id.showV1);
                txt3 = (TextView) findViewById(R.id.showV2);
                txt4 = (TextView) findViewById(R.id.showV3);

                sw1 = (Switch) findViewById(R.id.load1c);
                sw2 = (Switch) findViewById(R.id.load2c);
                sw3 = (Switch) findViewById(R.id.load3c);

                StringRequest request = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String[] fva;
                        fva = new String[4];

                        Gson gson = new GsonBuilder().create();

                        Measured measured = gson.fromJson(s, Measured.class);

                        List<Row> rows = measured.getRows();
                        Row row = rows.get(0);

                        // txt.setText(s);
                        int val = row.getValue().get(0);
                        String tval = Integer.toString(val);

                        txt1.setText(tval);

                        val = row.getValue().get(1);
                        tval = Integer.toString(val);
                        txt2.setText(tval);

                        val = row.getValue().get(2);
                        tval = Integer.toString(val);
                        txt3.setText(tval);

                        val = row.getValue().get(3);
                        tval = Integer.toString(val);
                        txt4.setText(tval);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        txt.setText("Some error occurred!!");
                    }
                });

                addToRequestQueue(request, "autoGetRequest");


                StringRequest request2 = new StringRequest(Request.Method.GET, url3, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        Gson gson = new GsonBuilder().create();
                        Control control = gson.fromJson(s, Control.class);
                        List<cRow> rows = control.getRows();
                        txt.setText(s);
                        cRow row1 = rows.get(0);
                        cRow row2 = rows.get(1);
                        cRow row3 = rows.get(2);

                        int val1 = row1.getValue();
                        int val2 = row2.getValue();
                        int val3 = row3.getValue();

                        if (val1 == 1) {
                            sw1.setChecked(true);
                        } else {
                            sw1.setChecked(false);
                        }
                        if (val2 == 1) {
                            sw2.setChecked(true);
                        } else {
                            sw2.setChecked(false);
                        }
                        if (val3 == 1) {
                            sw3.setChecked(true);
                        } else {
                            sw3.setChecked(false);
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        txt.setText("Some error occurred!!");
                    }
                });

                addToRequestQueue(request2, "autoGetRequest2");
                /*
                try {
                    final JSONAsyncTask httpAsync = new JSONAsyncTask();
                    result = httpAsync.execute(r1 + "/ -d  \'\"{\"_id\":\"1\",\"_rev\":\"2-f9cbfbdd9257c5221b2e6b77de3fcaa4\",\"status\":0}\"\'").get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (result=="TimeOut"){
                    //btn.setBackgroundColor(16711730);
                    btn.setBackgroundColor(Color.RED);
                    btn.setText("Connection Lost");
                }else{
                    //btn.setBackgroundColor(3322930);
                    btn.setBackgroundColor(Color.GREEN);
                    btn.setText("Connected");
                }
                */
                doTheAutoRefresh();
            }
        }, 3000);
    }

}

