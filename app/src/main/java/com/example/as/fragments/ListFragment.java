package com.example.as.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.as.R;
import com.example.as.classes.database.ConstantsDataBase;

public class ListFragment extends Fragment {

    private final String args;

    public ListFragment(String args) {
        this.args = args;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        TextView textTitle =  view.findViewById(R.id.text_title);
        Button buttonAdd = view.findViewById(R.id.button_add);
        RecyclerView listOld = view.findViewById(R.id.list_old);
        RecyclerView listNew = view.findViewById(R.id.list_new);

        if (args.equals(ConstantsDataBase.SAR)) {
            textTitle.setText(R.string.reporte_de_servicio_de_alto_riesgo_sar);
            buttonAdd.setText("Agregar nuevo SAR");
        } else {
            buttonAdd.setText("Agregar nuevo RIS");
        }

        buttonAdd.setOnClickListener(v -> {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content, new PagerFragment(args)).commit();
        });
    }
}