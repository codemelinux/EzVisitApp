package com.codemelinux.ezvisit;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class ViewHistory extends Fragment {


    private RecyclerView mVisitRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    UserLocalStore userLocalStore;
    ArrayList<VisitClassModel> visitClassModelArrayList;
    MyRecyclerViewAdapter adapter;


    private CollectionReference colRef;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        View view = inflater.inflate(R.layout.view_history, container,false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("History");
        view.setBackgroundColor(Color.BLACK);

        userLocalStore = new UserLocalStore(getActivity());
        visitClassModelArrayList = new ArrayList<>();
        mVisitRecyclerView = (RecyclerView) view.findViewById(R.id.visitRecyclerView);
        mVisitRecyclerView.setHasFixedSize(true);
        mVisitRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadDataFromFirestore();







        return view;
    }
    private void loadDataFromFirestore(){
        final String user = userLocalStore.getLoggedInUser().getEmail();

        db.collection("visits")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        String timeIn = " ";
                        for (QueryDocumentSnapshot querySnapshots: task.getResult()){

                            if (user.equals(querySnapshots.getString("email"))){
                                //timeIn = querySnapshots.getString("timeEnter");

                                VisitClassModel visitClassModel = new VisitClassModel(
                                        querySnapshots.getString("name"),
                                        querySnapshots.getString("mobileNo"),
                                        querySnapshots.getString("icNo"),
                                        querySnapshots.getString("date"),
                                        querySnapshots.getString("timeEnter"),
                                        querySnapshots.getString("timeExit"));

                                visitClassModelArrayList.add(visitClassModel);
                            }
                            adapter = new MyRecyclerViewAdapter(ViewHistory.this, visitClassModelArrayList);
                            mVisitRecyclerView.setAdapter(adapter);

                        }


                    }
                });

    }


    /*
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }



        final String user = userLocalStore.getLoggedInUser().getEmail();
        docRef = db.collection("visits");

        Query query = docRef.orderBy("date", Query.Direction.DESCENDING).limit(10);

        FirestoreRecyclerOptions<VisitorClass> options = new FirestoreRecyclerOptions.Builder<VisitorClass>()
                .setQuery(query,VisitorClass.class)
                .build();

        adapter = new ViewHistoryAdapter(options);
        RecyclerView recyclerView = view.findViewById(R.id.visitRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        */
}