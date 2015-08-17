package com.suh.itboy.auctionsystem.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.suh.itboy.auctionsystem.Adapters.Database.ProductDBAdapter;
import com.suh.itboy.auctionsystem.R;

/**
 * Created by itboy on 8/14/2015.
 */
public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.product_grid_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView product_image = (ImageView) view.findViewById(R.id.product_image);
        TextView product_title = (TextView) view.findViewById(R.id.product_title);
        TextView product_price = (TextView) view.findViewById(R.id.product_price);

        product_title.setText(cursor.getString(cursor.getColumnIndex(ProductDBAdapter.COLUMN_TITLE)));
        product_title.setTypeface(null, Typeface.BOLD);
        int price = cursor.getInt(cursor.getColumnIndex(ProductDBAdapter.COLUMN_PRICE));

        if (price == -1) {
            product_price.setText(R.string.price_not_available);
        } else {
            product_price.setText(String.valueOf(price) + " $");
        }
        String imagePath = cursor.getString(cursor.getColumnIndex(ProductDBAdapter.COLUMN_IMAGE));
        if (imagePath != null && imagePath.length() > 0) {

//            Glide.with(context).load(context.getFileStreamPath(imagePath)).into(image);
            product_image.setImageDrawable(
                    Drawable.createFromPath(
                            context.getFileStreamPath(imagePath).toString()
                    )
            );
        } else {
            product_image.setImageResource(R.drawable.product_placeholder);
        }
//        Get Random image
//        image.setImageResource(mImageList.get(App.getRandom(0, mImageList.size() - 1)));
    }
}
