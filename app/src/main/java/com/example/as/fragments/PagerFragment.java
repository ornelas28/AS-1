package com.example.as.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.as.R;
import com.example.as.classes.adapters.SARAdapter;
import com.example.as.classes.adapters.ViewPagerAdapter;
import com.example.as.classes.database.ConstantsDataBase;
import com.example.as.classes.database.RISData;
import com.example.as.classes.database.SARData;

import static com.example.as.classes.database.ConstantsDataBase.*;

public class PagerFragment extends Fragment {

    private final String args;
    private SARData sarData;
    private RISData risData;
    private final String code;
    private boolean stateAdmin = false;

    public PagerFragment(String args, boolean stateAdmin) {
        this.args = args;
        this.stateAdmin = stateAdmin;
        this.code = "";
    }

    public PagerFragment(String args, SARData sarData, String code, boolean stateAdmin) {
        this.args=args;
        this.sarData=sarData;
        this.code=code;
        this.stateAdmin = stateAdmin;
    }

    public PagerFragment(String args, RISData risData, String code, boolean stateAdmin) {
        this.args=args;
        this.risData=risData;
        this.code=code;
        this.stateAdmin = stateAdmin;
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
            if(code.equals(NEW)){
                adapter.addFragment(new Sar1Fragment(OLD, args, sarData, stateAdmin));
                adapter.addFragment(new Sar2Fragment(OLD, args, sarData, stateAdmin));
            }
            if (code.equals("")){
                adapter.addFragment(new Sar1Fragment("", args, stateAdmin));
                adapter.addFragment(new Sar2Fragment("", args, stateAdmin));
            }
        }
        if (args.equals(RIS)) {
            if(code.equals(NEW)){
                adapter.addFragment(new Ris1Fragment(OLD, args, risData, stateAdmin));
                adapter.addFragment(new Ris2Fragment(OLD, args, risData, stateAdmin));
            }
            if (code.equals("")){
                adapter.addFragment(new Ris1Fragment("", args, stateAdmin));
                adapter.addFragment(new Ris2Fragment("", args, stateAdmin));
            }
        }
        viewPager.setAdapter(adapter);
    }
}