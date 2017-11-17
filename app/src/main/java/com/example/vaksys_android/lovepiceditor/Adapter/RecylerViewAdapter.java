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
 * Created by vaksys-42 on 8/8/17.
 */

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.MyviewHolder> {
    Context editImage;
    Context context;
    private ArrayList<Integer> frameImageList;

    public RecylerViewAdapter(MainActivity EditImage, ArrayList<Integer> frameImageList) {

        this.context = editImage;
        this.frameImageList = frameImageList;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewGroup group = (ViewGroup) layoutInflater.inflate(R.layout.recycler_adapter, parent, false);
        MyviewHolder myViewHolder = new MyviewHolder(group);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        final Integer name = frameImageList.get(position);
        holder.recyclerImgItem.setBackgroundResource(name);
    }

    @Override
    public int getItemCount()
    {
        return frameImageList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView recyclerImgItem;
        private ItemClickListener mListener;

        public MyviewHolder(View itemView)
        {
            super(itemView);
            recyclerImgItem = (ImageView) itemView.findViewById(R.id.recycler_item_img);
            itemView.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener listener)
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

