package com.example.android_studio_tut1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{

    Context context;
    ArrayList<Announcement> announcementArrayList;

    public MyAdapter(Context context, ArrayList<Announcement> announcementArrayList)
    {
        this.context = context;
        this.announcementArrayList = announcementArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return announcementArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView authorTextView, titleTextView;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

        }
    }
}
