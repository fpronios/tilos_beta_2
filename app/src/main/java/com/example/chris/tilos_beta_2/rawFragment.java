package com.example.chris.tilos_beta_2;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link rawFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link rawFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class rawFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static TextView txt;
    private final Handler handler = new Handler();
    String urlraw = "http://192.168.4.1:5984/test/_design/last/_view/new-view?limit=1&descending=true";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private boolean isInFront;
    private RequestQueue requestQueue;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public rawFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static rawFragment newInstance(String param1, String param2) {
        rawFragment fragment = new rawFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_raw, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
        View v = getView();

        txt = (TextView) v.findViewById(R.id.fragment_raw1);


        doTheAutoRefresh();

        // Inflate the layout for this fragment

    }

    private void doTheAutoRefresh() {

        //final View v = View.inflate(getContext(), R.layout.control_activity,null);
        final View v = getView();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isInFront) {
                    txt = (TextView) v.findViewById(R.id.fragment_raw1);

                    StringRequest request2 = new StringRequest(Request.Method.GET, urlraw, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            String show = s;
                            show = show.replaceAll("(?<=[,])", "\n");
                            show = show.replaceAll("(?<=[{])", "\n");
                            txt.setText(show);

                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            txt.setText("Some error occurred!!");
                            if (volleyError.networkResponse.statusCode == 504 || volleyError.networkResponse.statusCode == 408) {
                                txt.setText("Request Error");
                            }
                        }
                    });

                    addToRequestQueue(request2, "autoGetRequest2");

                    doTheAutoRefresh();
                }
            }
        }, 3000);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Active", "OnResume");
        isInFront = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isInFront = false;
    }

    /**
     * Create a getRequestQueue() method to return the instance of
     * RequestQueue.This kind of implementation ensures that
     * the variable is instatiated only once and the same
     * instance is used throughout the application
     **/
    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
