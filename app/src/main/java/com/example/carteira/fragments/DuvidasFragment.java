package com.example.carteira.fragments;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.carteira.R;

public class DuvidasFragment extends Fragment {
    TextView details;
    LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_duvidas, container, false);

        details = view.findViewById(R.id.details);
        layout = view.findViewById(R.id.layout1);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        return view;
    }


    public void expand(View view) {
        int v = (details.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager. beginDelayedTransition(layout, new AutoTransition());
        details.setVisibility(v);
    }
}