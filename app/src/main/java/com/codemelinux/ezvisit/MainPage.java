package com.codemelinux.ezvisit;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainPage extends Fragment {
    Button gen_btn,viewHistoryBtn;
    TextView email;
    UserLocalStore userLocalStore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        View view = inflater.inflate(R.layout.main_page, container,false);

        view.setBackgroundColor(Color.WHITE);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Home");

        gen_btn = (Button) view.findViewById(R.id.gen_btn);
        viewHistoryBtn = (Button) view.findViewById(R.id.viewHistory_btn);
        email = (TextView) view.findViewById(R.id.showEmail);

        userLocalStore = new UserLocalStore(getActivity());

        Users user = userLocalStore.getLoggedInUser();
        email.setText("Email : "+user.email);


        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Generate_qr_code();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        viewHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ViewHistory();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }



}
