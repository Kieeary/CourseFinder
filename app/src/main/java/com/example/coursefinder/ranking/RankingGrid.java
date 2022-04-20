package com.example.coursefinder.ranking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coursefinder.R;

public class RankingGrid extends BaseAdapter{
    private Context mContext;
    private final String[] description;
    private final int[] courseImage;
    private final int[] detailImage;

    public RankingGrid(Context c,String[] description, int[] courseImage, int[] detailImage ) {
        mContext = c;
        this.description = description;
        this.courseImage = courseImage;
        this.detailImage = detailImage;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return description.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.activity_ranking_grid_single, null);
            TextView rankText = (TextView) grid.findViewById(R.id.rank_text);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView representImage = (ImageView)grid.findViewById(R.id.grid_image);
            ImageView lensImage = (ImageView)grid.findViewById(R.id.detail_image);

            rankText.setText(Integer.toString(position+1));
            textView.setText(description[position]);
            representImage.setImageResource(courseImage[position]);
            lensImage.setImageResource(detailImage[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}