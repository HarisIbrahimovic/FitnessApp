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

import com.example.myfitness.Model.Exercise;
import com.example.myfitness.R;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private Context context;
    private List<Exercise> exerciseList;
    private TouchListner touchListner;

    public ExerciseAdapter(Context context, TouchListner touchListner) {
        this.context = context;
        this.touchListner = touchListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exercise_item,parent,false);
        return new ViewHolder(view,touchListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.exerciseName.setText(exercise.getExerciseName());
        holder.exerciseReps.setText(exercise.getReps()+"");
        holder.exerciseRest.setText(exercise.getRest()+"s");
        holder.exerciseWeight.setText(exercise.getWeight()+"kg");
        holder.exerciseSets.setText(exercise.getSets()+"");
        holder.deleteButton.setOnClickListener(v -> {
            touchListner.deleteNode(holder.getAdapterPosition());
        });
        checkPicutre(holder,position);
    }


    private void checkPicutre(ViewHolder holder, int position ) {
        if((position+1)%8==0) holder.image.setImageResource(R.drawable.dbls);
        else if((position+1)%7==0) holder.image.setImageResource(R.drawable.dbls);
        else if((position+1)%6==0) holder.image.setImageResource(R.drawable.chest);
        else if((position+1)%5==0) holder.image.setImageResource(R.drawable.leg1);
        else if((position+1)%4==0) holder.image.setImageResource(R.drawable.arm2);
        else if((position+1)%3==0) holder.image.setImageResource(R.drawable.leg2);
        else if((position+1)%2==0) holder.image.setImageResource(R.drawable.pullup);
        else holder.image.setImageResource(R.drawable.arm);
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(exerciseList==null)return 0;
        return exerciseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image = itemView.findViewById(R.id.imageView2);
        private TextView exerciseName = itemView.findViewById(R.id.textView3);
        private TextView exerciseReps = itemView.findViewById(R.id.repsExcName);
        private TextView exerciseWeight = itemView.findViewById(R.id.weightExsItem);
        private TextView exerciseSets = itemView.findViewById(R.id.setsExsItem);
        private TextView exerciseRest = itemView.findViewById(R.id.restExsItem);
        private ImageButton deleteButton = itemView.findViewById(R.id.deleteExericse);

        public ViewHolder(@NonNull View itemView, TouchListner listner) {
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
