package com.example.dataparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {


    // Key for saving the state of the TextView
    private static final String TEXT_STATE = "currentText";

    // The TextView where we will show results
    private TextView mTextView;
    private ListView mListView;

    private ArrayList<Object> arrayList;
    /*
    import java.util.ArrayList;
    ArrayList<Integer> integers1 = new ArrayList<Integer>(); // 타입 지정
    ArrayList<Integer> integers2 = new ArrayList<>(); // 타입 생략 가능
    ArrayList<Integer> integers3 = new ArrayList<>(10); // 초기 용량(Capacity) 설정
    ArrayList<Integer> integers4 = new ArrayList<>(integers1); // 다른 Collection값으로 초기화
    ArrayList<Integer> integers5 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)); // Arrays.asList()

    .add("Black");  데이터 추가
    .set(0, "Blue"); 인덱스 0에 Black를 Blue로 변경
    .remove("White"); White 삭제
    .size() 크기 확인
    .clear(); 내용 전체 삭제
     String removedColor = colors.remove(0); 데이터 인덱스 0번을 리턴
     boolean contains = colors.contains("Black"); 데이터 존재 여부 확인 =>있으면 true,없으면 false
     int index = colors.indexOf("Blue"); 값이 존재하는 경우 인덱스를 리턴,값이 존재하지 않을 경우 -1을 리턴
    */
    private ArrayAdapter<Object> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView1);
        mListView = findViewById(R.id.listview_list);

        // Restore TextView if there is a savedInstanceState bundle.
        if (savedInstanceState != null) {
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }

    }
    /**
     * Handles the onClick for the "Start Task" button. Launches the AsyncTask
     * which performs work off of the UI thread.
     *
     * @param view The view (Button) that was clicked.
     */
    public void startTask(View view) {

        // Put a message in the text view.
        mTextView.setText(R.string.napping);


/*
        AsyncTask<String, Void, JSONArray> mAsyncTaskParser_JsonData = new AsyncTaskParser(mTextView,mListView).execute();
        arrayList = new ArrayList<>();
        try {
            JSONObject jdata = null;
            for (int i =0; i < mAsyncTaskParser_JsonData.get().length(); i++){

                    jdata = mAsyncTaskParser_JsonData.get().getJSONObject(i);
                    arrayList.add(jdata.getString("korean_name"));
            }
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
            mListView.setAdapter(arrayAdapter);
            Log.d("====================",""+ arrayList.toString());

        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
*/
        new NewsApi().execute();



    }

    /**
     * Saves the contents of the TextView to restore on configuration change.
     *
     * @param outState The bundle in which the state of the activity is saved when
     *                 it is spontaneously destroyed.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the state of the TextView
        outState.putString(TEXT_STATE, mTextView.getText().toString());



    }
}