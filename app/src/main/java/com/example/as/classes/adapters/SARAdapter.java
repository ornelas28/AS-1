package com.example.as.classes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.as.R;
import com.example.as.classes.database.SARData;

import java.util.ArrayList;
import java.util.List;

public class SARAdapter{
    private Context context;
    private List<SARData> sarDataList;


    public interface OnMenuListener {
        void onMenuClick(int position);
    }
}
