package com.example.coursefinder.playingRegister;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coursefinder.R;
import com.example.coursefinder.searchVo.ImageList;
import com.example.coursefinder.searchVo.PlaceList;

import java.util.ArrayList;

public class ResultAdapter extends BaseAdapter {

    Context context;
    int [] image;
    ArrayList<PlaceList> placeName = new ArrayList<PlaceList>();;

    public ResultAdapter(Context context, int [] image, ArrayList<PlaceList> placeName) {
        this.context = context;
        this.image = image;
        this.placeName = placeName;
    }

    LayoutInflater inflater;

    @Override
    public int getCount() {
        return placeName.size();
    }

    @Override
    public PlaceList getItem(int i) {
        return placeName.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.result_item, null);
        }

        ImageView courseimgIv = convertView.findViewById(R.id.courseImg);
        TextView courseNameTv = convertView.findViewById(R.id.courseName);

        courseimgIv.setImageResource(image[0]);
        courseNameTv.setText(placeName.get(i).getTitle().replaceAll("<b>", " ").replaceAll("</b>", " "));

        if(placeName.get(i).getImgLink()!= null){
            Glide.with(convertView).load(placeName.get(i).getImgLink()).into(courseimgIv);
        }

//        ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.detailBtn);
//
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
        return convertView;
    }
}


//package com.example.coursefinder.playingRegister;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.example.coursefinder.R;
//import com.example.coursefinder.searchVo.ImageList;
//import com.example.coursefinder.searchVo.PlaceList;
//
//import java.util.ArrayList;
//
//public class ResultAdapter extends BaseAdapter {
//
//    Context context;
//    int [] image;
//    ArrayList<PlaceList> placeName = new ArrayList<PlaceList>();;
//
//    public ResultAdapter(Context context, int [] image, ArrayList<PlaceList> placeName) {
//        this.context = context;
//        this.image = image;
//        this.placeName = placeName;
//    }
//
//    LayoutInflater inflater;
//
//    @Override
//    public int getCount() {
//        return placeName.size();
//    }
//
//    @Override
//    public PlaceList getItem(int i) {
//        return placeName.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View convertView, ViewGroup viewGroup) {
//        if (inflater == null) {
//            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.result_item, null);
//        }
//
//        ImageView courseimgIv = convertView.findViewById(R.id.courseImg);
//        TextView courseNameTv = convertView.findViewById(R.id.courseName);
//
//        courseimgIv.setImageResource(image[0]);
//        courseNameTv.setText(placeName.get(i).getTitle().replaceAll("<b>", " ").replaceAll("</b>", " "));
//        if(placeName.get(i).getImgLink()!= null){
//            Glide.with(convertView).load(placeName.get(i).getImgLink()).into(courseimgIv);
//        }
//
//
////        ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.detailBtn);
////
////        imageButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////            }
////        });
//        return convertView;
//    }
//}
//
