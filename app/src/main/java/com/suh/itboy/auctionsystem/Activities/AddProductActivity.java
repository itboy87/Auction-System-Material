package com.suh.itboy.auctionsystem.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.suh.itboy.auctionsystem.R;

public class AddProductActivity extends AppCompatActivity {
    private static final int IMAGE_REQUEST_CODE = 100;
    ImageView product_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        product_image = (ImageView) findViewById(R.id.product_image);
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

                String imagePath = imageCursor.getString(imageCursor.getColumnIndex(imagePathColumn[0]));
                imageCursor.close();

                product_image.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            } else {
                Toast.makeText(AddProductActivity.this, "NO Data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
