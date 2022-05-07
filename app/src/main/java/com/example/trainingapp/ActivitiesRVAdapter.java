package com.example.trainingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ActivitiesRVAdapter extends RecyclerView.Adapter<ActivitiesRVAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<ActivityItem> activitiesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public ActivitiesRVAdapter(ArrayList<ActivityItem> activitiesArrayList, Context context) {
        this.activitiesArrayList = activitiesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ActivitiesRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitiesRVAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        ActivityItem AI = activitiesArrayList.get(position);
        holder.AInameTV.setText(AI.getName());
        holder.AIdurationTV.setText(String.format("%s minutes of this exercise.", String.valueOf(AI.getDuration())));
        holder.AIburnedCaloriesTV.setText(String.format("%s calories burned during the exercise.", String.valueOf(AI.getBurnedcalories())));
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return activitiesArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView AInameTV;
        private final TextView AIdurationTV;
        private final TextView AIburnedCaloriesTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            AInameTV = itemView.findViewById(R.id.AInameTV);
            AIdurationTV = itemView.findViewById(R.id.AIdurationTV);
            AIburnedCaloriesTV = itemView.findViewById(R.id.AIburnedCaloriesTV);
        }
    }
}
