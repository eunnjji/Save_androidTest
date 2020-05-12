package com.ejprac.SaveTest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class second extends Fragment {
    MainActivity mainActivity;
    public second(MainActivity ma) {
        this.mainActivity = ma;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.second_frag, container, false);



        return rootview;
    }
}
