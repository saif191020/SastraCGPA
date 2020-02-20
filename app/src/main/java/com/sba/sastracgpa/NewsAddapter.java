package com.sba.sastracgpa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAddapter extends RecyclerView.Adapter<NewsAddapter.NewsViewHolder> {
    Context context;
    String[] Titles;
    String[] Links;
    String[] Dates;
    int itemCount;

    public NewsAddapter(Context context,
                        String[] titles,
                        String[] Links,
                        String[] Dates,
                        int itemCount) {
        this.context = context;
        this.Titles = titles;
        this.Links = Links;
        this.Dates=Dates;
        this.itemCount =itemCount;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.newsrows,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        holder.newshead.setText(Titles[position]);
        holder.newsdate.setText(Dates[position]);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,WebpageActivity.class);
                intent.putExtra("DATAS_",Links[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView newshead,newsdate;
        RelativeLayout relativeLayout;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout =itemView.findViewById(R.id.News_links);
            newshead=itemView.findViewById(R.id.newsHeadLine);
            newsdate=itemView.findViewById(R.id.newsdate);

        }
    }
}
