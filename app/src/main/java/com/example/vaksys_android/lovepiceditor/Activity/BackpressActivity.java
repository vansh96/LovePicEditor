package com.example.vaksys_android.lovepiceditor.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.example.vaksys_android.lovepiceditor.Adapter.MoreAppCoustomRecylerAdapter;
import com.example.vaksys_android.lovepiceditor.Adapter.TopAppsRecylerAdapter;
import com.example.vaksys_android.lovepiceditor.Model.ApiResponse;
import com.example.vaksys_android.lovepiceditor.Model.ApplistResponse;
import com.example.vaksys_android.lovepiceditor.R;
import com.example.vaksys_android.lovepiceditor.Utility.ApiClient;
import com.example.vaksys_android.lovepiceditor.Utility.ApiInterface;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackpressActivity extends AppCompatActivity {

    private Button yesbtn;
    private Button nobtn;
    InterstitialAd mInterstitialAd;
    private Call<ApiResponse> apiResponseCall;
    private ApiInterface apiInterface1;
    private String appLink;
    private ProgressDialog pDialog;
    private ApiClient apiclient2;
    public RecyclerView topappRecyclerview, moreappRecyclerview;
    private MoreAppCoustomRecylerAdapter moreAppCoustomRecylerAdapter;
    private TopAppsRecylerAdapter topAppsRecylerAdapter;
    private Call<ApplistResponse> appListResponseCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_hub);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.center_name);


        topappRecyclerview = (RecyclerView) findViewById(R.id.rec_topapps);
        moreappRecyclerview = (RecyclerView) findViewById(R.id.rec_moreapps);


        pDialog = new ProgressDialog(this);
        pDialog = pDialog.show(BackpressActivity.this, null, "Showing Ads...", false, false);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        long delayInMillis = 10000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                pDialog.dismiss();

            }
        }, delayInMillis);
        apiInterface1 = apiclient2.getClient().create(ApiInterface.class);

        apiInterface1 = ApiClient.getClient().create(ApiInterface.class);

        initilizeadd();
        Topapps();
        MoreApps();


    }
    @Override
    public void onBackPressed()
    {
        backDialog();



    }
    public void backDialog() {
        final Dialog dialog = new Dialog(BackpressActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.back_dialog);
        yesbtn = (Button) dialog. findViewById(R.id.yes_button);
        nobtn = (Button) dialog.findViewById(R.id.no_button);
        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.exit(0);
                finish();
            }
        });

        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView text1= (TextView)dialog. findViewById(R.id.txt_nm);
        text1.setText("Are you sure you want to exit?");
        NativeExpressAdView adView = (NativeExpressAdView)dialog. findViewById(R.id.back_native_adView);
        AdRequest adRequest =new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        dialog.show();



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {

            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Topapps() {
        topappRecyclerview = (RecyclerView) findViewById(R.id.rec_topapps);
        LinearLayoutManager verticalLayoutManagaer = new LinearLayoutManager(BackpressActivity.this, LinearLayoutManager.HORIZONTAL, false);
        topappRecyclerview.setLayoutManager(verticalLayoutManagaer);
        topappRecyclerview.setItemAnimator(new DefaultItemAnimator());
        // apiInterface = apiServices.getClient().create(ApiInterface.class);
        appListResponseCall = apiInterface1.TOP_APPS_CALL();
        appListResponseCall.enqueue(new Callback<ApplistResponse>() {
            @Override
            public void onResponse(Call<ApplistResponse> call, Response<ApplistResponse> response) {
                Log.d("response code", String.valueOf(response.code()));
                if (!response.body().isError()) {
                    pDialog.dismiss();
                    List<ApplistResponse.AppList> data = response.body().getRow();
                    topAppsRecylerAdapter = new TopAppsRecylerAdapter(BackpressActivity.this, data);
                    topappRecyclerview.setAdapter(topAppsRecylerAdapter);
                } else {
                    Toast.makeText(BackpressActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ApplistResponse> call, Throwable t) {
                Toast.makeText(BackpressActivity.this, "Failure Connection..!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void MoreApps()
    {
        moreappRecyclerview = (RecyclerView) findViewById(R.id.rec_moreapps);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(BackpressActivity.this, GridLayoutManager.VERTICAL);
        int numberOfColumns = 3;
        moreappRecyclerview.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        moreappRecyclerview.setItemAnimator(new DefaultItemAnimator());


        appListResponseCall = apiInterface1.MORE_APPS_CALL();
        appListResponseCall.enqueue(new Callback<ApplistResponse>() {
            @Override
            public void onResponse(Call<ApplistResponse> call, Response<ApplistResponse> response) {
                if (!response.body().isError()) {
                    pDialog.dismiss();
                    List<ApplistResponse.AppList> data = response.body().getRow();
                    moreAppCoustomRecylerAdapter = new MoreAppCoustomRecylerAdapter(BackpressActivity.this, data);
                    moreappRecyclerview.setAdapter(moreAppCoustomRecylerAdapter);
                } else {
                    Toast.makeText(BackpressActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApplistResponse> call, Throwable t) {

                Toast.makeText(BackpressActivity.this, "Failure Connection..!", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void initilizeadd() {

        mInterstitialAd = new InterstitialAd(BackpressActivity.this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });

    }
    private void showInterstitial()
    {
        if (mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        }
    }
}
