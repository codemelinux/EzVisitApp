package com.codemelinux.ezvisit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {

    ViewHistory viewHistory;
    ArrayList<VisitClassModel> visitClassModelArrayList;


    public MyRecyclerViewAdapter(ViewHistory viewHistory, ArrayList<VisitClassModel> visitClassModelArrayList){
        this.viewHistory = viewHistory;
        this.visitClassModelArrayList = visitClassModelArrayList;
    }
    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewHistory.getContext());
        View view = layoutInflater.inflate(R.layout.history_list,parent, false);

        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder viewHolder, int position) {

        viewHolder.visitorName.setText(    "Name      : "+ visitClassModelArrayList.get(position).getName());
        viewHolder.visitorMobileNo.setText("Mobile No  : "+ visitClassModelArrayList.get(position).getMobileNo());
        //viewHolder.visitorIcno.setText(    "IC No           : "+ visitClassModelArrayList.get(position).getIcNo());
        viewHolder.ndate.setText(           "Date            : "+ visitClassModelArrayList.get(position).getNDate());
        viewHolder.timeIn.setText(           "Time In      : "+ visitClassModelArrayList.get(position).getTimeEnter());
        viewHolder.exitTime.setText(        "Exit Time   : "+ visitClassModelArrayList.get(position).getExitTime());
        // Glide.with(viewHolder.itemView.getContext()).load(model.getPhotoUrl()).into(viewHolder.qrImage);

    }

    @Override
    public int getItemCount() {
        return visitClassModelArrayList.size();
    }
}
