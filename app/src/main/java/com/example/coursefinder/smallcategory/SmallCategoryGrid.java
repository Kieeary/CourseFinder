package com.example.coursefinder.smallcategory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.coursefinder.R;

public class SmallCategoryGrid extends BaseAdapter{
    private Context mContext;
    private final String[] buttonStr;
    private int[] count;

public SmallCategoryGrid(Context c,String[] buttonStr, int[] count ) {
        mContext = c;
        this.buttonStr = buttonStr;
        this.count = count;
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

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        int length=count[position];
        RadioGroup btnGroup = new RadioGroup(mContext.getApplicationContext());
        btnGroup.setOrientation(LinearLayout.HORIZONTAL);
        RadioButton[] btnArr=new RadioButton[length];
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.small_category_grid_single, null);

            LinearLayout layout = (LinearLayout)grid.findViewById(R.id.gridLayout);
            for(int i=0; i<length; i++){
                btnArr[i] = new RadioButton(mContext.getApplicationContext());
                btnArr[i].setText("test" + i);
                btnArr[i].setId(position+i);
                btnArr[i].setButtonDrawable(mContext.getResources().getDrawable(R.drawable.selector_radio_button));
                btnArr[i].setTextColor(mContext.getResources().getColorStateList(R.drawable.selector_radio_text));
                btnGroup.addView(btnArr[i]);
            }

            layout.addView(btnGroup);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}