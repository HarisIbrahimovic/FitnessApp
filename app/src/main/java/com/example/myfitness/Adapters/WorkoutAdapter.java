package com.example.myfitness.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfitness.Model.Workout;
import com.example.myfitness.R;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {
    private List<Workout> workoutList;
    private Context context;
    private TouchListner touchListner;


    public WorkoutAdapter(Context context, TouchListner touchListner) {
        this.context = context;
        this.touchListner = touchListner;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.workout_item,parent,false);
        return new ViewHolder(view,touchListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout workout = workoutList.get(position);
        holder.workoutName.setText(workout.getName());
        checkPicutre(holder,position);
        holder.deleteButton.setOnClickListener(v -> touchListner.deleteNode(holder.getAdapterPosition()));

    }

    private void checkPicutre(ViewHolder holder, int position ) {
        if((position+1)%6==0){
            holder.image.setImageResource(R.drawable.cardiopic);
        } else if((position+1)%5==0){
            holder.image.setImageResource(R.drawable.masina);
        }
        else if((position+1)%4==0){
            holder.image.setImageResource(R.drawable.latmasina);
        }
        else if((position+1)%3==0){
          holder.image.setImageResource(R.drawable.rings);
        }
        else if((position+1)%2==0){
            holder.image.setImageResource(R.drawable.benc);
        }
        else{
            holder.image.setImageResource(R.drawable.gympic1);
        }
    }

    @Override
    public int getItemCount() {
        if(workoutList==null)return 0;
        return workoutList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image = itemView.findViewById(R.id.imageView2);
        private TextView workoutName = itemView.findViewById(R.id.textView3);
        private ImageButton deleteButton = itemView.findViewById(R.id.deleteImage);
        public ViewHolder(@NonNull View itemView,TouchListner listner) {
            super(itemView);
            touchListner = listner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            touchListner.onNoteClick(getAdapterPosition());
        }
    }
    public interface TouchListner{
        void onNoteClick(int poistion);
        void deleteNode(int position);
    }

}
