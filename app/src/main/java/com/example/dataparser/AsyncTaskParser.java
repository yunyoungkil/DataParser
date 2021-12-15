package com.example.dataparser;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;




public class AsyncTaskParser extends AsyncTask<String, Void, JSONArray> {
    JSONArray d_jsonArray;
    ArrayList<String> arrayList;
    // The TextView where we will show results
    private WeakReference<TextView> mTextView;

    // Constructor that provides a reference to the TextView from the MainActivity
    AsyncTaskParser(TextView tv) {
       mTextView = new WeakReference<>(tv);
    }

    @Override
    protected JSONArray doInBackground(String... string) {

        Response response = null;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.upbit.com/v1/market/all?isDetails=false")
                .get()
                .addHeader("Accept", "application/json")
                .build();


        try {
            arrayList = new ArrayList<>();
            response = client.newCall(request).execute();

            String s = response.body().string();
           //Log.d("데이터",""+s);
           d_jsonArray = new JSONArray(s);
           // Log.d("번호:"+ d_jsonArray.length(),""+d_jsonArray.getJSONObject(10));

            //SONObject_name = market,korean_name,english_name

            for (int i =0; i<d_jsonArray.length(); i++){
                JSONObject jdata = d_jsonArray.getJSONObject(i);
                Log.d("마켓: " +jdata.getString("korean_name"),"이름: "+jdata.getString("market"));

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return d_jsonArray;
    }
    @Override
    protected void onPostExecute(JSONArray result) {

    }

    //mTextView.get().setText(result);
}
