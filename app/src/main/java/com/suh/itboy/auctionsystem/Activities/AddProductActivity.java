package com.suh.itboy.auctionsystem.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.suh.itboy.auctionsystem.Adapters.Database.ProductDBAdapter;
import com.suh.itboy.auctionsystem.Provider.ProductProvider;
import com.suh.itboy.auctionsystem.R;
import com.suh.itboy.auctionsystem.Utils.App;

public class AddProductActivity extends AppCompatActivity {
    private static final int IMAGE_REQUEST_CODE = 100;
    ImageView product_image;
    EditText product_title;
    EditText product_description;
    EditText product_price;
    Bitmap bitmapImage = null;
    String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        product_image = (ImageView) findViewById(R.id.product_image);
        product_title = (EditText) findViewById(R.id.product_title);
        product_description = (EditText) findViewById(R.id.product_description);
        product_price = (EditText) findViewById(R.id.product_price);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ChangeProductImage(View view) {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {

                Uri selectedImage = data.getData();
                String[] imagePathColumn = {MediaStore.Images.Media.DATA};
                Cursor imageCursor = getContentResolver().query(selectedImage, imagePathColumn, null, null, null);
                imageCursor.moveToFirst();

                imagePath = imageCursor.getString(imageCursor.getColumnIndex(imagePathColumn[0]));
                imageCursor.close();
                bitmapImage = BitmapFactory.decodeFile(imagePath);
                product_image.setImageBitmap(bitmapImage);


            } else {
                Toast.makeText(AddProductActivity.this, "NO Data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private long insertProduct(String title, String description, long price) {
        ContentValues values = new ContentValues();
        values.put(ProductDBAdapter.COLUMN_TITLE, title);
        values.put(ProductDBAdapter.COLUMN_DESCRIPTION, description);
        values.put(ProductDBAdapter.COLUMN_PRICE, price);
//        values.put(ProductDBAdapter.COLUMN_IMAGE, imagePath);

        return Long.parseLong(getContentResolver().insert(ProductProvider.CONTENT_URI, values).getLastPathSegment());
    }

    private int insertProductImage(long id, String imagePath) {
        ContentValues values = new ContentValues();
        values.put(ProductDBAdapter.COLUMN_IMAGE, imagePath);

        return getContentResolver().update(ProductProvider.CONTENT_URI, values, ProductDBAdapter.ROW_ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void addProduct(View view) {
        ProgressDialog progressDialog = new ProgressDialog(AddProductActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();


        long id = insertProduct(
                product_title.getText().toString(),
                product_description.getText().toString(),
                Integer.parseInt(product_price.getText().toString())
        );

        if (id > 0) {
            setResult(Activity.RESULT_OK);
        } else {
            setResult(Activity.RESULT_CANCELED);
        }

        if (imagePath.length() > 0 && bitmapImage != null && id > 0) {
            String imageInternalPath = Long.toString(id) + "." + App.getExtension(imagePath);

            if (App.writeImageToInternal(getApplicationContext(), bitmapImage, imageInternalPath)) {
                if (!(insertProductImage(id, imageInternalPath) > 0)) {
                    Toast.makeText(AddProductActivity.this, "Unable to save image path in database", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(AddProductActivity.this, "Image inserted into internal storage", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddProductActivity.this, "Unable to save image in internal storage", Toast.LENGTH_SHORT).show();
            }
        }

        progressDialog.cancel();
        finish();
    }

    public void cancelProduct(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
