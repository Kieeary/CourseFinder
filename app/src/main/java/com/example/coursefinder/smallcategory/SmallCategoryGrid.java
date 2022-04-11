package com.example.coursefinder.smallcategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.coursefinder.R;

public class SmallCategoryGrid extends BaseAdapter{
    private Context mContext;
    private final String[] buttonStr;

public SmallCategoryGrid(Context c,String[] buttonStr ) {
        mContext = c;
        this.buttonStr = buttonStr;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return buttonStr.length;
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
            grid = inflater.inflate(R.layout.small_category_grid_single, null);
            Button button1 = (Button)grid.findViewById(R.id.button1);
            Button button2 = (Button)grid.findViewById(R.id.button2);
            button1.setText(buttonStr[position]);
            button2.setText(buttonStr[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}