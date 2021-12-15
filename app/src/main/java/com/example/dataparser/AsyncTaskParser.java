package com.example.dataparser;

import android.os.AsyncTask;
import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ListView;
import android.widget.TextView;

public class AsyncTaskParser extends AsyncTask<String, Void, JSONArray> {
    JSONArray d_jsonArray;

    // The TextView where we will show results
    private WeakReference<TextView> mTextView;
    private WeakReference<ListView> mListView;

    // Constructor that provides a reference to the TextView from the MainActivity
    AsyncTaskParser(TextView tv) {
       mTextView = new WeakReference<>(tv);
    }

    @Override
    protected JSONArray doInBackground(String... string)  {


        /**
        필드명	설명	타입
        market	업비트에서 제공중인 시장 정보	String
        korean_name	거래 대상 암호화폐 한글명	String
        english_name	거래 대상 암호화폐 영문명	String
        market_warning	유의 종목 여부
        NONE (해당 사항 없음), CAUTION(투자유의)	String

         isDetails boolean =false,true
         유의종목 필드과 같은 상세 정보 노출 여부(선택 파라미터)
         */

        Response response = null;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.upbit.com/v1/market/all?isDetails=false")
                .get()
                .addHeader("Accept", "application/json")
                .build();

        try {

            response = client.newCall(request).execute();
            String s = response.body().string();
           d_jsonArray = new JSONArray(s);

            for (int i =0; i < d_jsonArray.length(); i++){
                JSONObject jdata = d_jsonArray.getJSONObject(i);
                String market = jdata.getString("market");
                String korean_name = jdata.getString("korean_name");
                String english_name = jdata.getString("english_name");

            }
           //  Log.d("마켓" ,"이름: "+ korean_name);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return d_jsonArray;
    }
    @Override
    protected void onPostExecute(JSONArray result) {

    }

    //mTextView.get().setText(result);
}
