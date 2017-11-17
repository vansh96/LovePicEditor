package com.example.vaksys_android.lovepiceditor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vaksys_android.lovepiceditor.Model.ApplistResponse;
import com.example.vaksys_android.lovepiceditor.R;

import java.util.List;

/**
 * Created by vaksys-42 on 1/8/17.
 */

public class TopAppsRecylerAdapter extends RecyclerView.Adapter<TopAppsRecylerAdapter.MyviewHolder>
{
    Context context;
    List<ApplistResponse.AppList> row;

    public TopAppsRecylerAdapter(Context appHubActivity, List<ApplistResponse.AppList> data) {
        this.context = appHubActivity;
        this.row = data;
    }
    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.topapps_recyler_adapter,parent,false);
        return  new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder,final int position)
    {
        Glide.with(context).load(row.get(position).getImage()).into(holder.image);
        holder.txtname.setText(row.get(position).getName());
        holder.btndownolad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(row.get(position).getLink()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (android.content.ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(row.get(position).getLink()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
        holder.linearLayouttopapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(row.get(position).getLink()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (android.content.ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(row.get(position).getLink()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return row.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder
    {
        private ImageView image;
        private TextView txtname;
        private Button btndownolad;
        private final LinearLayout linearLayouttopapps;

        public MyviewHolder(View itemView)
        {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.app_image);
            txtname= (TextView) itemView.findViewById(R.id.app_name);
            btndownolad= (Button) itemView.findViewById(R.id.btn_download);
            linearLayouttopapps= (LinearLayout) itemView.findViewById(R.id.lineartopsapp);
        }
    }
}
