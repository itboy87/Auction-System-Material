package com.suh.itboy.auctionsystem.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.suh.itboy.auctionsystem.Adapters.Database.ProductDBAdapter;
import com.suh.itboy.auctionsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itboy on 8/14/2015.
 */
public class ProductCursorAdapter extends CursorAdapter {
    private List<Integer> mImageList = new ArrayList<>();

    public ProductCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

        mImageList.add(R.drawable.product1);
        mImageList.add(R.drawable.product2);
        mImageList.add(R.drawable.product3);
        mImageList.add(R.drawable.product4);
        mImageList.add(R.drawable.product5);
        mImageList.add(R.drawable.product6);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.product_grid_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView image = (ImageView) view.findViewById(R.id.image);
        TextView title = (TextView) view.findViewById(R.id.title);

        title.setText(cursor.getString(cursor.getColumnIndex(ProductDBAdapter.COLUMN_TITLE)));

        String imagePath = cursor.getString(cursor.getColumnIndex(ProductDBAdapter.COLUMN_IMAGE));
        if (imagePath != null && imagePath.length() > 0) {

//            Glide.with(context).load(context.getFileStreamPath(imagePath)).into(image);
            image.setImageDrawable(
                    Drawable.createFromPath(
                            context.getFileStreamPath(imagePath).toString()
                    )
            );
        }

//        image.setImageResource(mImageList.get(App.getRandom(0, mImageList.size() - 1)));
    }
}
