package com.example.myfitness.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myfitness.Model.DietDay;
import com.example.myfitness.R;

import java.util.List;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {

    private List<DietDay> dietDayList;
    private Context context;
    private TouchListener touchListener;

    public DaysAdapter(Context context, TouchListener touchListener) {
        this.context = context;
        this.touchListener = touchListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.day_item,parent,false);
        return new ViewHolder(view,touchListener);
    }

    public void setDietDayList(List<DietDay> dietDayList) {
        this.dietDayList = dietDayList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DietDay dietDay = dietDayList.get(position);
        holder.caloriesTextView.setText(dietDay.getCalories()+"");
        holder.carbsTextView.setText(dietDay.getCarbs()+"");
        holder.proteinTextView.setText(dietDay.getProtein()+"");
        holder.fatsTextView.setText(dietDay.getFats()+"");
        holder.dayNum.setText("Day 0"+(position+1)+".");
        checkPicutre(holder,position);
    }

    private void checkPicutre(ViewHolder holder, int position) {
        if((position+1)%6==0){
            holder.image.setImageResource(R.drawable.shake);
        } else if((position+1)%5==0){
            holder.image.setImageResource(R.drawable.cocoa);
        }
        else if((position+1)%4==0){
            holder.image.setImageResource(R.drawable.almond);
        }
        else if((position+1)%3==0){
            holder.image.setImageResource(R.drawable.chilli);
        }
        else if((position+1)%2==0){
            holder.image.setImageResource(R.drawable.bacon);
        }
        else{
            holder.image.setImageResource(R.drawable.bananas);
        }
    }

    @Override
    public int getItemCount() {
        if(dietDayList==null)return 0;
        return dietDayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView caloriesTextView = itemView.findViewById(R.id.caloriesItem);
        private TextView fatsTextView = itemView.findViewById(R.id.fatsItem);
        private TextView proteinTextView = itemView.findViewById(R.id.proteinItem);
        private TextView carbsTextView = itemView.findViewById(R.id.carbsItem);
        private TextView dayNum = itemView.findViewById(R.id.dayNumItem);
        private ImageView image = itemView.findViewById(R.id.imageNutritionItem);

        public ViewHolder(@NonNull View itemView,TouchListener listener) {
            super(itemView);
            touchListener = listener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            touchListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface TouchListener{
        void onNoteClick(int position);
    }
}
