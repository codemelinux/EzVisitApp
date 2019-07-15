package com.codemelinux.ezvisit;

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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.UUID;


public class ViewHistory extends Fragment {


    private RecyclerView mVisitRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter<VisitorClass, ViewHistoryAdapter.visitorViewHolder> mFirebaseAdapter;
    String mUsername;
    UserLocalStore userLocalStore;
    private ViewHistoryAdapter adapter;

    private CollectionReference docRef;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        View view = inflater.inflate(R.layout.view_history, container,false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("History");


        mVisitRecyclerView = (RecyclerView) view.findViewById(R.id.visitRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(true);
        userLocalStore = new UserLocalStore(getActivity());

        final String user = userLocalStore.getLoggedInUser().getEmail();
        docRef = db.collection("users").document(user).collection("visits");



        Query query = docRef.orderBy("name", Query.Direction.DESCENDING).limit(1);

        FirestoreRecyclerOptions<VisitorClass> options = new FirestoreRecyclerOptions.Builder<VisitorClass>()
                .setQuery(query,VisitorClass.class)
                .build();

        adapter = new ViewHistoryAdapter(options);
        RecyclerView recyclerView = view.findViewById(R.id.visitRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);




        setUpRecyclerView();



        return view;
    }

    private void  setUpRecyclerView(){



    }

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
}