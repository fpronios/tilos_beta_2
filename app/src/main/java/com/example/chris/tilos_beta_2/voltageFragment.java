package com.example.chris.tilos_beta_2;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AndroidException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.System.currentTimeMillis;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link voltageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link voltageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class voltageFragment extends Fragment implements
        OnChartGestureListener,
        OnChartValueSelectedListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final ArrayList<Entry> ret = new ArrayList<Entry>();
    final ArrayList<Entry> ret2 = new ArrayList<Entry>();
    final ArrayList<Entry> ret3 = new ArrayList<Entry>();
    LineData dat;
    String url2 = "http://192.168.4.1:5984/meas/_design/last/_view/new-view?limit=100&descending=true";
    private boolean isInFront;
    private RequestQueue requestQueue;
    private View myFragmentView, myView;
    private Button button, button2;
    private LineChart lchart, lchart2, lchart3;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public voltageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static voltageFragment newInstance(String param1, String param2) {
        voltageFragment fragment = new voltageFragment();
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_voltage, (ViewGroup) getView(), false);
    }

    public void main_voltage_draw() {
        lchart = (LineChart) getView().findViewById(R.id.chart1);
        lchart2 = (LineChart) getView().findViewById(R.id.chart2);
        lchart3 = (LineChart) getView().findViewById(R.id.chart3);
        //lchart =  (LineChart) myFragmentView.findViewById(R.id.lchart);
        lchart.setOnChartGestureListener(this);
        lchart.setOnChartValueSelectedListener(this);

        //lchart.setVisibleXRangeMaximum(10);
        lchart.setData(setData2(ret, Color.RED));
        lchart.invalidate();

        // get the legend (only possible after setting data)
        Description description = new Description();
        description.setText("L1-N Voltage");
        lchart.setDescription(description);

        Legend l = lchart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        /*                                          */
        lchart2.setOnChartGestureListener(this);
        lchart2.setOnChartValueSelectedListener(this);
        //lchart2.setVisibleXRangeMaximum(10);
        lchart2.setData(setData2(ret2, Color.GREEN));
        lchart2.invalidate();
        //setData(buff);
        // get the legend (only possible after setting data)
        Description description2 = new Description();
        description2.setText("L2-N Voltage");
        lchart2.setDescription(description2);

        l = lchart2.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);


        /*                                          */
        lchart3.setOnChartGestureListener(this);
        lchart3.setOnChartValueSelectedListener(this);
        //lchart3.setVisibleXRangeMaximum(10);
        lchart3.setData(setData2(ret3, Color.BLUE));
        lchart3.invalidate();
        //setData(buff);
        // get the legend (only possible after setting data)
        Description description3 = new Description();
        description3.setText("L3-N Voltage");
        lchart3.setDescription(description3);

        l = lchart3.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
    }


    private ArrayList<Entry> getData(String url, final int Vtype) {
        ret.clear();
        ret2.clear();
        ret3.clear();
        if (isInFront) {
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {

                    Gson gson = new GsonBuilder().create();

                    Measured measured = gson.fromJson(s, Measured.class);


                    List<Row> rows = measured.getRows();
                    Row row;
                    List<Value> vals;
                    Value valloc;

                    Date cdate = new Date(currentTimeMillis());
                    SimpleDateFormat simpleDateFormat =
                            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:");

                    for (int i = 0; i < rows.size(); i++) {
                        row = rows.get(i);
                        vals = row.getValue();
                        valloc = vals.get(0);
                        float val = valloc.getV();
                        //Log.e("+++++++++++++++++++++", (Integer.toString(i))+" :  "+ val);
                        ret.add(new Entry(i, val));
                        val = valloc.getV1();
                        ret2.add(new Entry(i, val));
                        val = valloc.getV2();
                        ret3.add(new Entry(i, val));
                    }


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });


            addToRequestQueue(request, "autoGetRequest");
            Log.e("JSON", "Volley ended");
        }

        return ret;

    }

    private LineData setData2(ArrayList<Entry> to_draw, int clr) {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = to_draw;// yax;//getData(url2,5);

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "L1-N Voltage (V): ");
        set1.setFillAlpha(110);
        set1.setFillColor(clr);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        //LineData data = new LineData(xVals, dataSets);
        LineData data = new LineData(dataSets);

        //setDat(data);
        // set data
        return data;

    }


    // This is used to store x-axis values
    private ArrayList<String> setXAxisValues() {
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("10");
        xVals.add("20");
        xVals.add("30");
        xVals.add("30.5");
        xVals.add("40");

        return xVals;
    }

    // This is used to store Y-axis values
    private ArrayList<Entry> setYAxisValues() {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(60, 0));
        yVals.add(new Entry(48, 1));
        yVals.add(new Entry(70.5f, 2));
        yVals.add(new Entry(100, 3));
        yVals.add(new Entry(180.9f, 4));

        return yVals;
    }

    @Override
    public void onChartGestureStart(MotionEvent me,
                                    ChartTouchListener.ChartGesture
                                            lastPerformedGesture) {

        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me,
                                  ChartTouchListener.ChartGesture
                                          lastPerformedGesture) {

        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            // or highlightTouch(null) for callback to onNothingSelected(...)
            lchart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2,
                             float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: "
                + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_voltage, container, false);
        //myFragmentView = inflater.inflate(R.layout.fragment_voltage, container, false);
        myView = (LineChart) v.findViewById(R.id.chart1);
        button = (Button) v.findViewById(R.id.button);
        button2 = (Button) v.findViewById(R.id.button2);
        //onViewCreated(myFragmentView,savedInstanceState);
        getData(url2, 1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                getData(url2, 1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                main_voltage_draw();
            }
        });
        return v;
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view.findViewById(R.id.chart1).setOnClickListener((View.OnClickListener) this);
        //Log.e("View", "onViewCreated");
        // or
        //getActivity().findViewById(R.id.yourId).setOnClickListener(this);
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
