package com.example.vaksys_android.lovepiceditor.Extras.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.example.vaksys_android.lovepiceditor.Extras.utils.FontProvider;
import com.example.vaksys_android.lovepiceditor.Extras.viewmodel.TextLayer;


public class TextEntity extends MotionEntity
{
    private final TextPaint textPaint;
    private final FontProvider fontProvider;
    private final TextLayer textLayer;
    @Nullable
    private Bitmap bitmap;


    public TextEntity(@NonNull TextLayer textLayer,
                      @IntRange(from = 1) int canvasWidth,
                      @IntRange(from = 1) int canvasHeight,
                      @NonNull FontProvider fontProvider, TextLayer textLayer1) {
        super(textLayer, canvasWidth, canvasHeight);
        this.fontProvider = fontProvider;
        this.textLayer = textLayer1;

        this.textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        updateEntity(false);
    }

    private void updateEntity(boolean moveToPreviousCenter) {


        PointF oldCenter = absoluteCenter();

        Bitmap newBmp = createBitmap(getLayer(), bitmap);
        if (bitmap != null && bitmap != newBmp && !bitmap.isRecycled()) {
            bitmap.recycle();
        }

        this.bitmap = newBmp;

        float width = bitmap.getWidth();
        float height = bitmap.getHeight();

        @SuppressWarnings("UnnecessaryLocalVariable")
        float widthAspect = 1.0F * canvasWidth / width;

        this.holyScale = widthAspect;

        srcPoints[0] = 0;
        srcPoints[1] = 0;
        srcPoints[2] = width;
        srcPoints[3] = 0;
        srcPoints[4] = width;
        srcPoints[5] = height;
        srcPoints[6] = 0;
        srcPoints[7] = height;
        srcPoints[8] = 0;
        srcPoints[8] = 0;

        if (moveToPreviousCenter) {
            moveCenterTo(oldCenter);
        }
    }

    @NonNull
    private Bitmap createBitmap(@NonNull TextLayer textLayer, @Nullable Bitmap reuseBmp) {

        int boundsWidth = canvasWidth;

        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textLayer.getFont().getSize() * canvasWidth);
        textPaint.setColor(textLayer.getFont().getColor());
        textPaint.setTypeface(fontProvider.getTypeface(textLayer.getFont().getTypeface()));

        StaticLayout sl = new StaticLayout(
                textLayer.getText(),
                textPaint,
                boundsWidth,
                Layout.Alignment.ALIGN_CENTER,
                1,
                1,
                true);

        int boundsHeight = sl.getHeight();

        int bmpHeight = (int) (canvasHeight * Math.max(TextLayer.Limits.MIN_BITMAP_HEIGHT,
                1.0F * boundsHeight / canvasHeight));

        Bitmap bmp;
        if (reuseBmp != null && reuseBmp.getWidth() == boundsWidth
                && reuseBmp.getHeight() == bmpHeight) {

            bmp = reuseBmp;
            bmp.eraseColor(Color.TRANSPARENT);
        } else {
            bmp = Bitmap.createBitmap(boundsWidth, bmpHeight, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bmp);
        canvas.save();

        if (boundsHeight < bmpHeight) {
            float textYCoordinate = (bmpHeight - boundsHeight) / 2;
            canvas.translate(0, textYCoordinate);
        }
        sl.draw(canvas);
        canvas.restore();

        return bmp;
    }

    @Override
    @NonNull
    public TextLayer getLayer() {
        return (TextLayer) layer;
    }

    @Override
    protected void drawContent(@NonNull Canvas canvas, @Nullable Paint drawingPaint) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, matrix, drawingPaint);
        }
    }

    @Override
    public int getWidth() {
        return bitmap != null ? bitmap.getWidth() : 0;
    }

    @Override
    public int getHeight() {
        return bitmap != null ? bitmap.getHeight() : 0;
    }

    public void updateEntity() {
        updateEntity(true);
    }

    @Override
    public void release() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}