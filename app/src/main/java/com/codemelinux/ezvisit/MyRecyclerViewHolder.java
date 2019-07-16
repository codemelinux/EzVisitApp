package com.codemelinux.ezvisit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView visitorName,visitorMobileNo, visitorIcno, ndate, timeIn, exitTime;
    public ImageView qrImage;

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);

        visitorName = (TextView) itemView.findViewById(R.id.name);
        visitorMobileNo = (TextView) itemView.findViewById(R.id.mobileNo);
        //visitorIcno = (TextView) itemView.findViewById(R.id.icNo);
        ndate = (TextView) itemView.findViewById(R.id.ndate);
        timeIn = (TextView) itemView.findViewById(R.id.timeIn);
        exitTime = (TextView) itemView.findViewById(R.id.timeOut);
       // qrImage = (ImageView) itemView.findViewById(R.id.qr_image);
    }
}
