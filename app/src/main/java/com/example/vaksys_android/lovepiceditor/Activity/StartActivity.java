package com.example.vaksys_android.lovepiceditor.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vaksys_android.lovepiceditor.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import pl.droidsonroids.gif.GifImageView;

public class StartActivity extends AppCompatActivity implements  PopupMenu.OnMenuItemClickListener
{
    private ImageView shareImage,toolMenu;
    ImageView imgstart;
    private AdView adView;
    GifImageView gifImageView;
    ProgressDialog pDialog;
    private int STORAGE_PERMISSION_CODE = 23;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        shareImage = (ImageView) findViewById(R.id.share);
        imgstart = (ImageView) findViewById(R.id.img_play);
        gifImageView = (GifImageView) findViewById(R.id.gifimage);
        NativeExpressAdView adView= (NativeExpressAdView) findViewById(R.id.native_adView);
        adView.loadAd(new AdRequest.Builder().build());
        requestStoragePermission();
        pDialog = new ProgressDialog(StartActivity.this);

        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }
        if (getIntent().getBooleanExtra("close", false))
        {
            finish();
        }
        toolMenu = (ImageView) findViewById(R.id.menu);
        toolMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                PopupMenu popup = new PopupMenu(StartActivity.this, view);
                popup.setOnMenuItemClickListener(StartActivity.this);
                popup.inflate(R.menu.main);
                popup.show();
            }
        });
        shareImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent("android.intent.action.SEND");
                String applink = "Diwali Photo Frame App by #dreamyinfotech \nhttps://goo.gl/ZMB1c9";
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, applink);
                startActivity(Intent.createChooser(intent, "Share with"));
            }
        });
      gifImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    Intent intent = new Intent(StartActivity.this, AppHubActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(StartActivity.this, "Please Turn Your Internet Connection On..!!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    @Override
    public void onBackPressed() {

        if (isNetworkAvailable()) {
            Intent intent = new Intent(StartActivity.this, BackpressActivity.class);
            startActivity(intent);
            finish();


        } else {


            new android.app.AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            StartActivity.this.finish();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)

                    .show();

        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();

                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("imageUri", imageUri.toString());
                startActivity(intent);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }


    public void onSelectImageClick(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.rateus)
        {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/ZMB1c9"));
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException e) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/ZMB1c9"));
                startActivity(intent);
            }
            return true;
        }
        if (id == R.id.moreapp)
        {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=dreamyinfotech&hl=en"));
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException e) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=dreamyinfotech&hl=en"));
                startActivity(intent);
            }
            return true;
        }
        if (id == R.id.privacypolicy)
        {
            final Dialog dialog = new Dialog(StartActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.privacy_dialog);
            ImageView imageView = (ImageView) dialog.findViewById(R.id.img_close);
            imageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(StartActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }

        return;
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
