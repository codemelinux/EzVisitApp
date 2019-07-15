package com.codemelinux.ezvisit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ViewHistoryAdapter extends FirestoreRecyclerAdapter <VisitorClass, ViewHistoryAdapter.visitorViewHolder> {


    public ViewHistoryAdapter(FirestoreRecyclerOptions<VisitorClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(visitorViewHolder viewHolder, int i, VisitorClass model) {

        viewHolder.visitorName.setText(    "Name      : "+ model.getName());
        viewHolder.visitorMobileNo.setText("Mobile No  : "+ model.getMobileNo());
        viewHolder.visitorIcno.setText(    "IC No           : "+ model.getIcNo());
//        viewHolder.visitorVehno.setText(   "VEH No       : "+ model.getVehNo());
        //viewHolder.visitorUnitno.setText(  "Unit No       : "+ model.getUnitNo());
        ///viewHolder.time.setText(           "Time           : "+ model.getTime());
        viewHolder.date.setText(           "Date            : "+ model.getDate());
       // Glide.with(viewHolder.itemView.getContext()).load(model.getPhotoUrl()).into(viewHolder.qrImage);

    }

    @NonNull
    @Override
    public visitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false);


        return new visitorViewHolder(v);
    }

    class visitorViewHolder extends RecyclerView.ViewHolder {

        TextView visitorName,visitorMobileNo, visitorIcno, visitorVehno, visitorUnitno,time,date;
        ImageView qrImage;

        public visitorViewHolder(@NonNull View itemView) {
            super(itemView);

            visitorName = (TextView) itemView.findViewById(R.id.name);
            visitorMobileNo = (TextView) itemView.findViewById(R.id.mobileNo);
            visitorIcno = (TextView) itemView.findViewById(R.id.icNo);
           // visitorVehno = (TextView) itemView.findViewById(R.id.vehNo);
           // visitorUnitno = (TextView) itemView.findViewById(R.id.unitNo);
            time = (TextView) itemView.findViewById(R.id.time);
            date= (TextView) itemView.findViewById(R.id.date);
            qrImage = (ImageView) itemView.findViewById(R.id.qr_image);
        }
    }
}
