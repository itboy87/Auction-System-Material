package com.suh.itboy.auctionsystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.suh.itboy.auctionsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itboy on 8/9/2015.
 */
public class ProductGridAdapter extends BaseAdapter {
    private Context context;
    private List<Integer> mImageList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    public ProductGridAdapter(Context context) {
        this.context = context;
        /*mImageList.add(R.drawable.product1);
        mImageList.add(R.drawable.product2);
        mImageList.add(R.drawable.product3);
        mImageList.add(R.drawable.product4);
        mImageList.add(R.drawable.product5);
        mImageList.add(R.drawable.product6);*/

        mTitleList.add("Product 1");
        mTitleList.add("Product 2");
        mTitleList.add("Product 3");
        mTitleList.add("Product 4");
        mTitleList.add("Product 5");
        mTitleList.add("Product 6");
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.product_grid_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       /* ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView title = (TextView) convertView.findViewById(R.id.title);*/


        Glide.with(this.context).load(mImageList.get(position)).centerCrop().into(viewHolder.image);
        //image.setImageResource(mImageList.get(position));
        viewHolder.title.setText(mTitleList.get(position));

        return convertView;
    }

    public static class ViewHolder {
        public ImageView image;
        public TextView title;
    }
}
