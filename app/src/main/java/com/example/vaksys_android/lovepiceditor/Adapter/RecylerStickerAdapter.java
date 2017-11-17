package com.example.vaksys_android.lovepiceditor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vaksys_android.lovepiceditor.Activity.MainActivity;
import com.example.vaksys_android.lovepiceditor.R;

import java.util.ArrayList;

/**
 * Created by vaksys-42 on 11/8/17.
 */

public class RecylerStickerAdapter extends RecyclerView.Adapter<RecylerStickerAdapter.MyviewHolder>
{
    Context editImage;
    Context context;
    private ArrayList<Integer> stickerImageList;


    public RecylerStickerAdapter(MainActivity EditImage, ArrayList<Integer> stickerImageList) {

        this.context = editImage;
        this.stickerImageList =stickerImageList ;

    }
    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewGroup group = (ViewGroup) layoutInflater.inflate(R.layout.recycler_sticker_adapter, parent, false);
        MyviewHolder myviewHolder= new MyviewHolder(group);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position)
    {
        final Integer name = stickerImageList.get(position);
        holder.recyclerstickerImgItem.setBackgroundResource(name);
    }

    @Override
    public int getItemCount() {
        return stickerImageList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final ImageView recyclerstickerImgItem;
        private RecylerStickerAdapter.ItemClickListener mListener;

        public MyviewHolder(View itemView)
        {
            super(itemView);
            recyclerstickerImgItem = (ImageView) itemView.findViewById(R.id.recycler_sticker_item_img);
            itemView.setOnClickListener(this);
        }

        public void setClickListener(RecylerStickerAdapter.ItemClickListener listener)
        {
            this.mListener = listener;
        }


        @Override
        public void onClick(View v) {

        }
    }

    public interface ItemClickListener
    {
        void onClickItem(int pos);
    }
}
