package com.example.carteira.fragments;

import android.animation.LayoutTransition;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.carteira.R;

public class DuvidasFragment extends Fragment {
    TextView details1, details2, details3, details4, details5;
    LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_duvidas, container, false);

        details1 = view.findViewById(R.id.details);
        details2 = view.findViewById(R.id.details2);
        details3 = view.findViewById(R.id.details3);
        details4 = view.findViewById(R.id.details4);
        details5 = view.findViewById(R.id.details5);

        layout = view.findViewById(R.id.layout1);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);


        CardView cardView1 = view.findViewById(R.id.card1);
        CardView cardView2 = view.findViewById(R.id.card2);
        CardView cardView3 = view.findViewById(R.id.card3);
        CardView cardView4 = view.findViewById(R.id.card4);
        CardView cardView5 = view.findViewById(R.id.card5);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, details1);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, details2);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, details3);
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, details4);
            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, details5);
            }
        });

        return view;
    }

    public void expand(View view, TextView details) {
        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        details.setVisibility(View.GONE == details.getVisibility() ? View.VISIBLE : View.GONE );
        TransitionManager.endTransitions(layout);
    }
}