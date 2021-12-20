package com.example.dataparser;



import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class NewsApi extends AsyncTask<String, Void, JSONArray> {
    private String NaverApi_String;
    JSONArray jsonArray = new JSONArray();
   // JSONObject jsonObject = new JSONObject();
   //JsonParser jParser = new JsonParser();
    @Override
    protected JSONArray doInBackground(String... string) {
        NaverApi_String = NaverApi();
        try {
            JSONObject jObject = new JSONObject(NaverApi_String);

            JSONArray items = (JSONArray) jObject.get("items");
            for (int i =0; i < items.length(); i++) {
                JSONObject title = (JSONObject) items.get(i);

                Log.d(">>>>", "" + title.getString("pubDate"));
                Log.d(">>>>", "" + title.getString("title"));
                Log.d(">>>>", "" + title.getString("description"));
                Log.d(">>>>", "" + title.getString("originallink"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public String NaverApi() {

        String clientId = "Cph5fUnYyjEZPtJqagvL"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "Rf5E9FgyDu"; //애플리케이션 클라이언트 시크릿값"


        String text = null;
        String text_tab_opt = null;
        try {
            text = URLEncoder.encode("코인 전망", "UTF-8");

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }


        String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text;    // json 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);


        String responseBody = get(apiURL,requestHeaders);
        System.out.println(responseBody);
        return responseBody;

    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);

        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

}
