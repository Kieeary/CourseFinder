package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Result2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);

        ListView listView2;
        Button next2;

        String [] schNames = {"카페"};

        int[] image2 = {R.drawable.map};
        String[] placeName2 = {"가게이름"};


        listView2 = (ListView)findViewById(R.id.listView2);
        next2 = (Button)findViewById(R.id.next2);


// 오류가 나서 일단 주석처리
//        ResultAdapter resultAdapter2 = new ResultAdapter(Result2.this, image2, orderschResults.get(2));
//        listView2.setAdapter(resultAdapter2);
//
//        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if(placeLists.indexOf(resultAdapter2.getItem(i)) == -1){
//                    placeLists.add(resultAdapter2.getItem(i));
//                }else {
//                    placeLists.remove(resultAdapter2.getItem(i));
//                    Log.d("TAG", "제거됨");
//                }
//                Toast.makeText(Result2.this, i+1 + "번째 코스 저장!", Toast.LENGTH_SHORT).show();
//            }
//        });

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Result2.this, Result3.class);
                startActivity(intent);
            }
        });
    }
}