package com.suh.itboy.auctionsystem.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.suh.itboy.auctionsystem.Adapters.Database.ProductDBAdapter;
import com.suh.itboy.auctionsystem.Provider.ProductProvider;
import com.suh.itboy.auctionsystem.R;
import com.suh.itboy.auctionsystem.Utils.App;
import com.suh.itboy.auctionsystem.Utils.Validate;

import java.util.Objects;

public class ProductEditorActivity extends AppCompatActivity {
    private static final int IMAGE_REQUEST_CODE = 100;
    ImageView product_image;
    EditText product_title;
    EditText product_description;
    EditText product_price;
    Bitmap bitmapImage = null;
    String imagePath = "";

    String action = Intent.ACTION_INSERT;
    String productFilter;
    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        setSupportActionBar((Toolbar) findViewById(R.id.tool_bar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        product_image = (ImageView) findViewById(R.id.product_image);
        product_title = (EditText) findViewById(R.id.product_title);
        product_description = (EditText) findViewById(R.id.product_description);
        product_price = (EditText) findViewById(R.id.product_price);
        Uri uri = getIntent().getParcelableExtra(ProductProvider.PRODUCT_EDIT_TYPE);
        if (uri == null) {
            setTitle("Add New Product");
            action = Intent.ACTION_INSERT;
        } else {
            action = Intent.ACTION_EDIT;
            productId = uri.getLastPathSegment();
            productFilter = ProductDBAdapter.ROW_ID + "=" + productId;

            ((Button) findViewById(R.id.add_button)).setText("Update");

            Cursor cursor = getContentResolver().query(uri, null, productFilter, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                product_title.setText(cursor.getString(cursor.getColumnIndex(ProductDBAdapter.COLUMN_TITLE)));
                product_description.setText(cursor.getString(cursor.getColumnIndex(ProductDBAdapter.COLUMN_DESCRIPTION)));
                int price = cursor.getInt(cursor.getColumnIndex(ProductDBAdapter.COLUMN_PRICE));
                if (price == -1) {
                    product_price.setText("");
                } else {
                    product_price.setText(String.valueOf(price));
                }

                String imagePath = cursor.getString(cursor.getColumnIndex(ProductDBAdapter.COLUMN_IMAGE));
                cursor.close();
                if (imagePath != null && imagePath.length() > 0) {

//            Glide.with(context).load(context.getFileStreamPath(imagePath)).into(image);
                    product_image.setImageDrawable(
                            Drawable.createFromPath(
                                    getFileStreamPath(imagePath).toString()
                            )
                    );
                } else {
                    product_image.setImageResource(R.drawable.product_placeholder);
                }
            } else {
                Toast.makeText(ProductEditorActivity.this, "Error Getting Data From Database!", Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (Objects.equals(action, Intent.ACTION_EDIT))
            getMenuInflater().inflate(R.menu.menu_product_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            deleteProduct();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteProduct() {
        getContentResolver().delete(ProductProvider.CONTENT_URI, productFilter, null);
        if (!Validate.isEmpty(imagePath)) {
            deleteFile(getFileStreamPath(imagePath).toString());
        }

        setResult(Activity.RESULT_OK);
        finish();
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
                if (imageCursor != null) {
                    imageCursor.moveToFirst();
                    imagePath = imageCursor.getString(imageCursor.getColumnIndex(imagePathColumn[0]));
                    imageCursor.close();

                    bitmapImage = BitmapFactory.decodeFile(imagePath);
                    product_image.setImageBitmap(bitmapImage);
                } else {
                    Toast.makeText(ProductEditorActivity.this, "Unable to get image from database!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ProductEditorActivity.this, "NO Data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private long insertProduct(ContentValues productValues) {
        Uri uri = getContentResolver().insert(ProductProvider.CONTENT_URI, productValues);
        long id = 0;
        if (uri != null) {
            id = Long.parseLong(uri.getLastPathSegment());
        }
        return id;
    }

    @NonNull
    private ContentValues getProductContentValues(String title, String description, long price) {
        ContentValues values = new ContentValues();
        values.put(ProductDBAdapter.COLUMN_TITLE, title);
        values.put(ProductDBAdapter.COLUMN_DESCRIPTION, description);
        values.put(ProductDBAdapter.COLUMN_PRICE, price);
        return values;
    }

    private int insertProductImage(String imagePath, String productFilter) {
        ContentValues values = new ContentValues();
        values.put(ProductDBAdapter.COLUMN_IMAGE, imagePath);

        return getContentResolver().update(ProductProvider.CONTENT_URI, values, productFilter, null);
    }

    public void addProduct(View view) {
        if (!ValidateValues()) {
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(ProductEditorActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        long id = 0;

        int price = (Validate.isEmpty(product_price.getText().toString())) ? -1 : Integer.parseInt(product_price.getText().toString());
        ContentValues productValues = getProductContentValues(
                product_title.getText().toString(),
                product_description.getText().toString(),
                price
        );

        switch (action) {

            case Intent.ACTION_INSERT:
                id = insertProduct(productValues);
                productId = String.valueOf(id);
                break;

            case Intent.ACTION_EDIT:
                getContentResolver().update(ProductProvider.CONTENT_URI, productValues, productFilter, null);
                id = Integer.parseInt(productId);
                break;

            default:
                Toast.makeText(ProductEditorActivity.this, "Incorrect Action: " + action, Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_CANCELED);
                finish();

        }

        if (id > 0) {
            setResult(Activity.RESULT_OK);
        } else {
            setResult(Activity.RESULT_CANCELED);
        }

        if (imagePath.length() > 0 && bitmapImage != null && id > 0) {
            String imageInternalPath = productId + "." + App.getExtension(imagePath);

            if (App.writeImageToInternal(getApplicationContext(), bitmapImage, imageInternalPath)) {
                if (!(insertProductImage(imageInternalPath, productFilter) > 0)) {
                    Toast.makeText(ProductEditorActivity.this, "Unable to save image path in database", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(ProductEditorActivity.this, "Image inserted into internal storage", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProductEditorActivity.this, "Unable to save image in internal storage", Toast.LENGTH_SHORT).show();
            }
        }

        progressDialog.cancel();
        finish();
    }

    private boolean ValidateValues() {

        if (Validate.isEmpty(product_title.getText().toString())) {
            product_title.setError("Enter product title!");
            return false;
        }

        String price = product_price.getText().toString();
        if (!Validate.isEmpty(price) && !Validate.isInt(price)) {
            product_price.setError("Enter Price in numeric only!");
            return false;
        }

        return true;
    }

    public void cancelProduct(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
