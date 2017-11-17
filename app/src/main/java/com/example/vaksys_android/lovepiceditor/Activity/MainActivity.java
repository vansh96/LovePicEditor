package com.example.vaksys_android.lovepiceditor.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.vaksys_android.lovepiceditor.Adapter.RecylerStickerAdapter;
import com.example.vaksys_android.lovepiceditor.Adapter.RecylerViewAdapter;
import com.example.vaksys_android.lovepiceditor.Extras.entity.MotionEntity;
import com.example.vaksys_android.lovepiceditor.Extras.entity.MotionView;
import com.example.vaksys_android.lovepiceditor.Extras.entity.TextEntity;
import com.example.vaksys_android.lovepiceditor.Extras.utils.FontProvider;
import com.example.vaksys_android.lovepiceditor.R;
import com.example.vaksys_android.lovepiceditor.View.StickerImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    static MainActivity editScreen;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private static final int NONEStk = 0;
    private static final int DRAGStk = 1;
    private static final int ZOOMStk = 2;
    SeekBar barOpacity_seekbar;
    RelativeLayout relativeLayout, relbackgroundchange, relativeLayouttextpanel, textlayout;
    FrameLayout frameeditimage;
    ImageView frameImage, stickerImage, imageselect,imgopocity;
    LinearLayout framelayot, fliplayout, opacitylayout, stickerLayout;
    RecylerViewAdapter recyclerViewAdapter;
    RecylerStickerAdapter recylerStickerAdapter;
    ArrayList<View> viewArrayList;
    ArrayList<Integer> stickerImageList, frameImageList, effectImageList;
    RecyclerView stickerRecyclerView, frameRecylerview;
    AdView adView;
    StickerImageView ivSticker, ivfreame, iveffect;
    ArrayList<StickerImageView> ivStickerArraylist, ivfreameArrayList, iveffectarraylist;
    private Bitmap mBitmap;

    private static MotionView motionView;
    private FontProvider fontProvider;
    int flag = 0, flag1 = 0, flag2 = 0, flag3 = 0;
    private LinearLayout mainMotionTextEntityEditPanel;
    int Reques = 99;
    private int currentBackgroundColor = 0xffffffff;
    private AdView mAdView;
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private Matrix matrixStk = new Matrix();
    private Matrix savedMatrixStk = new Matrix();
    private PointF startStk = new PointF();
    private PointF midStk = new PointF();
        int mode = NONE;
        float oldDist = 1f;
        float d = 0f;
        float newRot = 0f;
        float[] lastEvent = null;
        int modeStk = NONEStk;
        float oldDistStk = 1f;
        float dStk = 0f;
        float newRotStk = 0f;
        float[] lastEventStk = null;
        private View root;
    private ImageView backbtn,savebtn;
    public static MainActivity getInstance()
    {
        return editScreen;
    }

    private final MotionView.MotionViewCallback motionViewCallback = new MotionView.MotionViewCallback()
    {
        @Override
        public void onEntitySelected(@Nullable MotionEntity entity)
        {
            if (entity instanceof TextEntity)
            {
                mainMotionTextEntityEditPanel.setVisibility(View.VISIBLE);
                /*frameRecylerview.setVisibility(View.GONE);
                barOpacity_seekbar.setVisibility(View.GONE);
                stickerRecyclerView.setVisibility(View.GONE);*/


            }
            else
                {
                mainMotionTextEntityEditPanel.setVisibility(View.GONE);
                //barOpacity_seekbar.setVisibility(View.GONE);
                             }
        }

        @Override
        public void onEntityDoubleTap(@NonNull MotionEntity entity) {

        }

    };

    private void startTextEntityEditing()
    {
        TextEntity textEntity = currentTextEntity();

    }


    @Nullable
    private TextEntity currentTextEntity()
    {
        if (motionView != null && motionView.getSelectedEntity() instanceof TextEntity) {
            return ((TextEntity) motionView.getSelectedEntity());
        } else {
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        editScreen = this;
        frameRecylerview.setVisibility(View.VISIBLE);
        barOpacity_seekbar.setVisibility(View.GONE);

        motionView = (MotionView) findViewById(R.id.main_motion_view);
        motionView.setMotionViewCallback(motionViewCallback);
        final Bundle extras = getIntent().getExtras();


        //set frame in background
        frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f1));

        final Uri myUri = Uri.parse(extras.getString("imageUri"));
        imageselect.setImageURI(myUri);

        viewArrayList = new ArrayList<>();
        ivStickerArraylist = new ArrayList<>();
        ivfreameArrayList = new ArrayList<>();
        iveffectarraylist =new ArrayList<>();
        createfreame();
        createsticker();
        imageselect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        savedMatrix.set(matrix);
                        start.set(event.getX(), event.getY());
                        mode = DRAG;
                        lastEvent = null;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = spacing(event);
                        if (oldDist > 10f) {
                            savedMatrix.set(matrix);
                            midPoint(mid, event);
                            mode = ZOOM;
                        }
                        lastEvent = new float[4];
                        lastEvent[0] = event.getX(0);
                        lastEvent[1] = event.getX(1);
                        lastEvent[2] = event.getY(0);
                        lastEvent[3] = event.getY(1);
                        d = rotation(event);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                        lastEvent = null;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            matrix.set(savedMatrix);
                            float dx = event.getX() - start.x;
                            float dy = event.getY() - start.y;
                            matrix.postTranslate(dx, dy);
                        } else if (mode == ZOOM && event.getPointerCount() == 2)
                        {
                            float newDist = spacing(event);
                            matrix.set(savedMatrix);
                            if (newDist > 10f) {
                                float scale = newDist / oldDist;
                                matrix.postScale(scale, scale, mid.x, mid.y);
                            }
                            if (lastEvent != null) {
                                newRot = rotation(event);
                                float r = newRot - d;
                                matrix.postRotate(r, imageselect.getMeasuredWidth() / 2, imageselect.getMeasuredHeight() / 2);
                            }
                        }
                        break;
                }
                imageselect.setImageMatrix(matrix);
                return true;
            }
        });
        RecylerViewAdapter recycle_adapter = new RecylerViewAdapter(MainActivity.this, frameImageList);
        frameRecylerview.setAdapter(recycle_adapter);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        frameRecylerview.setLayoutManager(horizontalLayoutManagaer);

        frameRecylerview.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        if (position == 0) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f1));

                        }
                        if (position == 1) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f2));

                        }
                        if (position == 2) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f3));
                        }
                        if (position == 3) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f4));
                        }
                        if (position == 4) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f5));
                        }
                        if (position == 5) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f6));
                        }
                        if (position == 6) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f8));
                        }
                        if (position == 7) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f9));
                        }
                        if (position == 8) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f10));
                        }
                        if (position == 9) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f11));
                        }
                        if (position == 10) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f12));
                        }
                        if (position == 11) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f13));
                        }
                        if (position == 12) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f14));
                        }
                        if (position == 13) {
                            frameImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.f15));
                        }


                    }
                }));

        stickerRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (position == 0) {
                            ivSticker = new StickerImageView(MainActivity.this);
                            frameeditimage.addView(ivSticker);
                            ivStickerArraylist.add(ivSticker);
                            ivSticker.setBackgroundResource(R.drawable.st1);

                        }
                        if (position == 1) {
                            ivSticker = new StickerImageView(MainActivity.this);
                            frameeditimage.addView(ivSticker);
                            ivStickerArraylist.add(ivSticker);
                            ivSticker.setBackgroundResource(R.drawable.st2);

                        }
                        if (position == 2) {
                            ivSticker = new StickerImageView(MainActivity.this);
                            frameeditimage.addView(ivSticker);
                            ivStickerArraylist.add(ivSticker);
                            ivSticker.setBackgroundResource(R.drawable.st3);

                        }
                        if (position == 3) {
                            ivSticker = new StickerImageView(MainActivity.this);
                            frameeditimage.addView(ivSticker);
                            ivStickerArraylist.add(ivSticker);
                            ivSticker.setBackgroundResource(R.drawable.st4);

                        }
                        if (position == 4) {
                            ivSticker = new StickerImageView(MainActivity.this);
                            frameeditimage.addView(ivSticker);
                            ivStickerArraylist.add(ivSticker);
                            ivSticker.setBackgroundResource(R.drawable.st5);

                        }
                        if (position == 5) {
                            ivSticker = new StickerImageView(MainActivity.this);
                            frameeditimage.addView(ivSticker);
                            ivStickerArraylist.add(ivSticker);
                            ivSticker.setBackgroundResource(R.drawable.st6);

                        }
                        if (position == 6) {
                            ivSticker = new StickerImageView(MainActivity.this);
                            frameeditimage.addView(ivSticker);
                            ivStickerArraylist.add(ivSticker);
                            ivSticker.setBackgroundResource(R.drawable.st7);

                        }
                        if (position == 7) {
                            ivSticker = new StickerImageView(MainActivity.this);
                            frameeditimage.addView(ivSticker);
                            ivStickerArraylist.add(ivSticker);
                            ivSticker.setBackgroundResource(R.drawable.st8);

                        }
                        if (position == 8)
                        {
                            ivSticker = new StickerImageView(MainActivity.this);
                            frameeditimage.addView(ivSticker);
                            ivStickerArraylist.add(ivSticker);
                            ivSticker.setBackgroundResource(R.drawable.st9);

                        }

                    }
                }));
    }

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private float rotationStk(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float spacingStk(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPointStk(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private final OnItemClickListener mListener;
        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View childView = rv.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, rv.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }
    }


    private void createfreame() {
        frameImageList = new ArrayList<Integer>();
        frameImageList.add(R.drawable.f1);
        frameImageList.add(R.drawable.f2);
        frameImageList.add(R.drawable.f3);
        frameImageList.add(R.drawable.f4);
        frameImageList.add(R.drawable.f5);
        frameImageList.add(R.drawable.f6);
        frameImageList.add(R.drawable.f8);
        frameImageList.add(R.drawable.f9);
        frameImageList.add(R.drawable.f10);
        frameImageList.add(R.drawable.f11);
        frameImageList.add(R.drawable.f12);
        frameImageList.add(R.drawable.f13);
        frameImageList.add(R.drawable.f14);
        frameImageList.add(R.drawable.f15);


    }


    private void createsticker() {
        stickerImageList = new ArrayList<Integer>();
        stickerImageList.add(R.drawable.st1);
        stickerImageList.add(R.drawable.st2);
        stickerImageList.add(R.drawable.st3);
        stickerImageList.add(R.drawable.st4);
        stickerImageList.add(R.drawable.st5);
        stickerImageList.add(R.drawable.st6);
        stickerImageList.add(R.drawable.st7);
        stickerImageList.add(R.drawable.st8);
        stickerImageList.add(R.drawable.st9);

    }

    public void bottomTab() {

        fliplayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag3 == 0) {
                    flag = 1;
                    flag1 = 0;
                    flag2 = 0;
                    stickerRecyclerView.setVisibility(View.GONE);
                    frameRecylerview.setVisibility(View.GONE);
                    barOpacity_seekbar.setVisibility(View.GONE);
                    imageselect.setRotationY(imageselect.getRotationY() + 180f);
                }
                else if (flag3 == 1) {
                    flag = 0;
                    flag1 = 0;
                    flag2 = 0;
                    flag3=0;

                }


            }

        });

        framelayot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag1 == 0) {
                    flag1 = 1;
                    flag2 = 0;
                    flag3 = 0;
                    frameRecylerview.setVisibility(View.VISIBLE);
                    barOpacity_seekbar.setVisibility(View.GONE);
                    stickerRecyclerView.setVisibility(View.GONE);
                    motionView.unselectEntity();
                    RecylerViewAdapter recycle_adapter = new RecylerViewAdapter(MainActivity.this, frameImageList);
                    frameRecylerview.setAdapter(recycle_adapter);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    frameRecylerview.setLayoutManager(horizontalLayoutManagaer);

                } else if (flag1 == 1) {
                    flag1 = 0;
                    flag = 0;
                    flag3=0;
                    flag2 = 0;
                    frameRecylerview.setVisibility(View.GONE);
                }


            }
        });


        stickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 0) {
                    flag = 1;
                    flag1 = 0;
                    flag2 = 0;
                    flag3=0;
                    stickerRecyclerView.setVisibility(View.VISIBLE);
                    barOpacity_seekbar.setVisibility(View.GONE);
                    frameRecylerview.setVisibility(View.GONE);
                    motionView.unselectEntity();
                    RecylerStickerAdapter recycle_adapter = new RecylerStickerAdapter(MainActivity.this, stickerImageList);
                    stickerRecyclerView.setAdapter(recycle_adapter);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    stickerRecyclerView.setLayoutManager(horizontalLayoutManagaer);

                }
                else if (flag == 1)
                {
                    flag =1;
                    flag = 0;
                    flag2 = 0;
                    flag3=0;
                    stickerRecyclerView.setVisibility(View.GONE);
                }
            }
        });
        opacitylayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (flag2 == 0)
                {
                    flag2 = 1;
                    flag=0;
                    flag1=0;
                    flag3=0;
                    barOpacity_seekbar.setVisibility(View.VISIBLE);
                    stickerRecyclerView.setVisibility(View.GONE);
                    frameRecylerview.setVisibility(View.GONE);
                    motionView.unselectEntity();
                    barOpacity_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            frameeditimage.setAlpha((float)progress/255);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                }

                else if (flag2 == 1)
                {
                    flag3 = 0;
                    flag2=0;
                    flag=0;
                    flag1 = 0;
                    barOpacity_seekbar.setVisibility(View.GONE);
                }

            }

        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this,StartActivity.class);
                startActivity(i);
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                hideControls();
                hide();
                motionView.setVisibility(View.VISIBLE);
                relativeLayouttextpanel.setVisibility(View.VISIBLE);

                if (relbackgroundchange.getVisibility() == View.VISIBLE) {
                    relbackgroundchange.invalidate();
                    relbackgroundchange.buildDrawingCache();

                    Bitmap bitmap1 = relbackgroundchange.getDrawingCache();
                    Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap1, relbackgroundchange.getMeasuredWidth(), relbackgroundchange.getMeasuredHeight(), true);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    String s = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    SharedPreferences shared = getSharedPreferences("Hello", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("picture", s);
                    editor.commit();
                    startActivity(new Intent(MainActivity.this, SavescreenActivity.class));

                }
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        MainActivity.this.finish();
    }

    public void hideControls() {
        int iv_sicker_arraylist_size = ivStickerArraylist.size();
        if (!ivStickerArraylist.isEmpty()) {
            for (int i = 0; i <= iv_sicker_arraylist_size - 1; i++) {
                ivSticker = ivStickerArraylist.get(i);
                ivSticker.setControlItemsHidden(true);
                ivSticker.setControlsVisibility(false);
            }
        }
    }

    public void hide() {
        int iv_freame_arraylist_size = ivfreameArrayList.size();
        if (!ivfreameArrayList.isEmpty()) {
            for (int i = 0; i <= iv_freame_arraylist_size - 1; i++) {
                ivfreame = ivfreameArrayList.get(i);
                ivfreame.setControlItemsHidden(true);
                ivfreame.setControlsVisibility(false);
            }
        }
    }

    public void initialize()
    {
        stickerRecyclerView = (RecyclerView) findViewById(R.id.sticker_recycler_view);
        frameRecylerview = (RecyclerView) findViewById(R.id.frame_recycler_view);
        frameImage = (ImageView) findViewById(R.id.frame_image_view);
        stickerImage = (ImageView) findViewById(R.id.img_sticker);
        imageselect = (ImageView) findViewById(R.id.img_main_select);
        imgopocity= (ImageView) findViewById(R.id.img_oppocity);
        framelayot = (LinearLayout) findViewById(R.id.frame_layout);
        barOpacity_seekbar= (SeekBar) findViewById(R.id.seekbaropocity);
        fliplayout = (LinearLayout) findViewById(R.id.flip);
        opacitylayout = (LinearLayout) findViewById(R.id.opacity);
        stickerLayout = (LinearLayout) findViewById(R.id.stickers_layout);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_content_root);
        relbackgroundchange = (RelativeLayout) findViewById(R.id.rel_backgroundchange);
        relativeLayouttextpanel = (RelativeLayout) findViewById(R.id.relative_layout_text_panel);
        textlayout = (RelativeLayout) findViewById(R.id.textlayout);
        frameeditimage = (FrameLayout) findViewById(R.id.frame_edit_image_framelayout);
        backbtn = (ImageView) findViewById(R.id.backbtn);
        savebtn = (ImageView) findViewById(R.id.savebutton);

        bottomTab();

        adView = (AdView) findViewById(R.id.ad_view_editimg);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
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








