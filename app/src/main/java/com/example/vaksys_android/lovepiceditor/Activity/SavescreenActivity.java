package com.example.vaksys_android.lovepiceditor.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.example.vaksys_android.lovepiceditor.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class SavescreenActivity extends AppCompatActivity implements View.OnClickListener
{
    LinearLayout savelayout,sharelayout,rateus;
    ImageView image;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    Context context = this;
    FileOutputStream fout1;
    private Bitmap mBitmap;
    int reques = 99;
    private AdView adView;
    private ImageView backbutton,homebtn;
    LinearLayout relbackgroundchange;
    private int STORAGE_PERMISSION_CODE = 23;
    private Bitmap bmp;
    String applink = "Look my photo using this \n Diwali Photo Frame app by #dreamyinfotech \nhttps://goo.gl/ZMB1c9";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savescreen);
        savelayout = (LinearLayout) findViewById(R.id.savelayout);
        sharelayout = (LinearLayout) findViewById(R.id.sharelayout);
        /*rateus = (LinearLayout) findViewById(R.id.rateuslayout);*/
        requestStoragePermission();
        SharedPreferences preferences = context.getSharedPreferences("Hello", Context.MODE_PRIVATE);
        String save = preferences.getString("picture", "");
        byte[] byteArray = Base64.decode(save, Base64.DEFAULT);
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        image = (ImageView) findViewById(R.id.save_image);
        relbackgroundchange = (LinearLayout) findViewById(R.id.mylayout);
        image.setImageBitmap(bmp);
        savelayout.setOnClickListener(this);
        backbutton= (ImageView) findViewById(R.id.back_btn);
        homebtn= (ImageView) findViewById(R.id.homebutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SavescreenActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(SavescreenActivity.this,StartActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        adView = (AdView) findViewById(R.id.ad_view_save);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
        sharelayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Love Photo Frame by #dreamyinfotech\nhttps://goo.gl/ZMB1c9");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
       /* rateus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/ZMB1c9"));
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/ZMB1c9"));
                    startActivity(intent);
                }

            }
        });*/

    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.savelayout:
                checkPermission();
                break;
        }
    }
    public void save()
    {
        Toast.makeText(getApplicationContext(), "Image Saved Successfully", Toast.LENGTH_SHORT).show();
        File mainFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "Diwali Photo Frame");
        if (!mainFolder.exists() && !mainFolder.isDirectory()) {
            mainFolder.mkdirs();
            mainFolder.setExecutable(true);
            mainFolder.setReadable(true);
            mainFolder.setWritable(true);
            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{mainFolder.toString()}, null, null);
        }
        SimpleDateFormat sf = new SimpleDateFormat("ddMMyyyyhhmmss");
        image.invalidate();
        image.buildDrawingCache();
        bitmap1 = image.getDrawingCache();
        bitmap2 = Bitmap.createScaledBitmap(bitmap1, image.getWidth(), image.getHeight(), false);
        File file = new File(Environment.getExternalStorageDirectory() + "/Diwali Photo Frame", "Diwali Photo Frame_" + sf.format(Calendar.getInstance().getTime()) + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Diwali Photo Frame" + sf.format(Calendar.getInstance().getTime()));
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Diwali Photo Frame" + sf.format(Calendar.getInstance().getTime()));
        contentValues.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        contentValues.put(MediaStore.Images.ImageColumns.BUCKET_ID, file.toString().toLowerCase(Locale.US).hashCode());
        contentValues.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, file.getName().toLowerCase(Locale.US));
        contentValues.put("_data", file.getAbsolutePath());
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(SavescreenActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SavescreenActivity.this, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, reques);

        } else {
            save();
        }
    }

    private void checkPermissionShare() {
        int permissionCheck = ContextCompat.checkSelfPermission(SavescreenActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SavescreenActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, reques);

        } else {
            shareFile();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    save();
                    shareFile();

                } else {
                    Toast.makeText(SavescreenActivity.this, "Permission denied to Write your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private boolean requestStoragePermission()
    {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        return false;
    }


    private void shareFile()
    {
        Intent intentinsta = new Intent(Intent.ACTION_SEND);
        File fileinsta = new File(Environment.getExternalStorageDirectory() + File.separator + "temporaryy_file.jpg");
        try {

            FileOutputStream fout1 = new FileOutputStream(fileinsta);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fout1);
            fout1.flush();
            fout1.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        intentinsta.setType("image/*");
        intentinsta.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporaryy_file.jpg"));
        intentinsta.putExtra(Intent.EXTRA_TEXT, applink);
        startActivity(Intent.createChooser(intentinsta, "Share Image"));
    }

    @Override
    public void onBackPressed()
    {
        SavescreenActivity.this.finish();
    }
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
