package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Result3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result3);

        ListView listView3;
        Button next3;

        String [] schNames = {"영화관"};

        int[] image3 = {R.drawable.map};
        String[] placeName3 = {"가게이름"};

        listView3 = (ListView)findViewById(R.id.listView3);
        next3 = (Button)findViewById(R.id.next3);

// 오류가 나서 일단 주석처리
//        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("TAG", placeLists.indexOf(resultAdapter3.getItem(i))+" ");
//                if(placeLists.indexOf(resultAdapter3.getItem(i)) == -1){
//                    placeLists.add(resultAdapter3.getItem(i));
//                }else {
//                    placeLists.remove(resultAdapter3.getItem(i));
//                    Log.d("TAG", "제거됨");
//                }
//
//            }
//        });

        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Result3.this, MainCategory.class);
                startActivity(intent);
            }
        });

    }

}
