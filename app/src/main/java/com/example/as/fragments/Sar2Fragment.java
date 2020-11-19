package com.example.as.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.as.R;

public class Sar2Fragment extends Fragment {

    private String args;
    private Boolean stateAdmin;

    public Sar2Fragment(String args, Boolean stateAdmin) {
        this.args = args;
        this.stateAdmin= stateAdmin;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sar2, container, false);
    }
}