package com.example.socius.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.socius.R;
import com.example.socius.models.ModelGroups;

public class AdapterGroups extends RecyclerView.Adapter<AdapterGroups.MyHolder>{

    Context context;
    List<ModelGroups> postList;

    public AdapterGroups(Context context, List<ModelGroups> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_row,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String Name = postList.get(position).getName();
        String Hour = postList.get(position).getDailyHour();
        String Minute = postList.get(position).getDailyMinute();
        String Total = postList.get(position).getTotalTime();

        holder.pName.setText(Name);
        holder.pHour.setText(Hour);
        holder.pMinute.setText(Minute);


        holder.pTotal.setText(Total);



    }

    @Override
    public int getItemCount() {
        return postList.size();}


        class MyHolder extends RecyclerView.ViewHolder {

            TextView pName, pHour, pMinute, pTotal;


            public MyHolder(@NonNull View itemView) {
                super(itemView);


                pName = itemView.findViewById(R.id.row_name);
                pHour = itemView.findViewById(R.id.row_day_hour);
                pMinute = itemView.findViewById(R.id.row_day_minute);
                pTotal = itemView.findViewById(R.id.row_total_time);




            }
        }

}
