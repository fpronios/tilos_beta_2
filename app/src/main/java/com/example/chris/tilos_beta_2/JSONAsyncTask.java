package com.example.chris.tilos_beta_2;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Filippos on 6/10/2017.
 */

public class JSONAsyncTask extends AsyncTask<String, Void, String> {
    public static final String REQUEST_METHOD = "PUT";
    public static final int READ_TIMEOUT = 10000;//15000;
    public static final int CONNECTION_TIMEOUT = 10000;//15000;



    @Override
    protected String doInBackground(String... params) {
        String stringUrl = params[0];
        String result;
        String inputLine;
        try {
            //Create a URL object holding our url
            URL myUrl = new URL(stringUrl);

            //Create a connection
            HttpURLConnection connection = (HttpURLConnection)
                    myUrl.openConnection();

            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestProperty("Content-type","application/json");
            //connection.setRequestProperty("/ -d  \'{\"_rev\":\"2-3f53a46ac762ca016f4da931320e19d8\",\"status\":1}\'");
            //connection.setRequestProperty("-d","/ -d  \'{\"_rev\":\"2-3f53a46ac762ca016f4da931320e19d8\",\"status\":1}\'");


            //Connect to our url
            //connection.connect();

            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes("\'{\"_rev\":\"2-3f53a46ac762ca016f4da931320e19d8\",\"status\":1}\'");
            wr.flush();
            wr.close();

            Log.d("Not -skipped HTTP: ",stringUrl);
            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
            //Log.e("Skipped not", result);
        } catch (SocketTimeoutException e){
            Log.e("TIMEOUT", "Timeout Caught");

            result = "TimeOut";
        } catch (IOException e) {
            e.printStackTrace();
            result = null;

        }
      // Log.e("Skipped not", result);



        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }
}