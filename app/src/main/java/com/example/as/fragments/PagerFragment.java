package com.example.as.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.as.R;
import com.example.as.classes.adapters.ViewPagerAdapter;

import static com.example.as.classes.database.ConstantsDataBase.*;

public class PagerFragment extends Fragment {

    private String args;

    public PagerFragment(String args) {
        this.args = args;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewPager viewPager = getView().findViewById(R.id.pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        if (args.equals(SAR)) {
            adapter.addFragment(new Sar1Fragment(1, args));
            adapter.addFragment(new Sar2Fragment(args));
        }
        viewPager.setAdapter(adapter);
    }
}