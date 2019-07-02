package com.codemelinux.ezvisit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ViewHistory extends Fragment {


    private RecyclerView mVisitRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<VisitorClass, visitorViewHolder> mFirebaseAdapter;
    String mUsername;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        View view = inflater.inflate(R.layout.view_history, container,false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("History");

        mVisitRecyclerView = (RecyclerView) view.findViewById(R.id.visitRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(true);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        String userID = user.getUid();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
            return view;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                //mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<VisitorClass, visitorViewHolder>(
                VisitorClass.class,
                R.layout.history_list,
                visitorViewHolder.class,
                mFirebaseDatabaseReference.child("users/"+ userID +"/" + "Visitors")) {

            @Override
            protected void populateViewHolder(visitorViewHolder viewHolder, VisitorClass model, int position) {
                viewHolder.visitorName.setText(    "Name      : "+ model.getName());
                viewHolder.visitorMobileNo.setText("Mobile No  : "+ model.getMobileNo());
                viewHolder.visitorIcno.setText(    "IC No           : "+ model.getIcNo());
                viewHolder.visitorVehno.setText(   "VEH No       : "+ model.getVehNo());
                viewHolder.visitorUnitno.setText(  "Unit No       : "+ model.getUnitNo());
                viewHolder.time.setText(           "Time           : "+ model.getTime());
                viewHolder.date.setText(           "Date            : "+ model.getDate());
                Glide.with(getActivity()).load(model.getPhotoUrl()).into(viewHolder.qrImage);
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int roomCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 || (positionStart >= (roomCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    mVisitRecyclerView.scrollToPosition(positionStart);
                }
            }
        });
        ////// populates the cardview ////
        mVisitRecyclerView.setLayoutManager(mLinearLayoutManager);
        mVisitRecyclerView.setAdapter(mFirebaseAdapter);

        return view;
    }


    public static class visitorViewHolder extends RecyclerView.ViewHolder {
        TextView visitorName,visitorMobileNo, visitorIcno, visitorVehno, visitorUnitno,time,date;
        ImageView qrImage;

        //ImageView roomPhoto;

        public visitorViewHolder(View v) {
            super(v);
            visitorName = (TextView) itemView.findViewById(R.id.name);
            visitorMobileNo = (TextView) itemView.findViewById(R.id.mobileNo);
            visitorIcno = (TextView) itemView.findViewById(R.id.icNo);
            visitorVehno = (TextView) itemView.findViewById(R.id.vehNo);
            visitorUnitno = (TextView) itemView.findViewById(R.id.unitNo);
            time = (TextView) itemView.findViewById(R.id.time);
            date= (TextView) itemView.findViewById(R.id.date);
            qrImage = (ImageView) itemView.findViewById(R.id.qr_image);
        }
    }




}
