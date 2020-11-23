package com.example.as.classes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as.R;
import com.example.as.classes.database.ConstantsDataBase;
import com.example.as.classes.database.RISData;
import com.example.as.classes.database.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RISAdapter extends RecyclerView.Adapter<RISAdapter.SARHolder> implements Filterable{

    private final Context context;
    private final List<RISData> risDataList;
    private List<RISData> risDataListFilter;

    public RISAdapter(Context context, List<RISData> risDataList, List<RISData> risDataListFilter, OnRisEditListener onRisEditListener) {
        this.context = context;
        this.risDataList = risDataList;
        this.risDataListFilter = risDataListFilter;
    }


    public RISAdapter(Context context, List<RISData> risDataList, OnRisListener onRISListener) {
        this.context = context;
        this.risDataList = risDataList;
        this.onRISListener = onRISListener;
        this.risDataListFilter = risDataList;
    }

    private OnRisListener onRISListener;

    @NonNull
    @Override
    public SARHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout=LayoutInflater.from(context).inflate(R.layout.item_sar, parent, false);
        return new SARHolder(layout, onRISListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SARHolder holder, int position) {
        holder.delegation.setText(risDataList.get(position).getDelegation());
        holder.date.setText((risDataList.get(position).getDate()));
        holder.service.setText(risDataList.get(position).getEntity());
        holder.type_patient.setText("# Implicados" + risDataList.get(position).getImplicate());
        FirebaseDatabase.getInstance().getReference(ConstantsDataBase.USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot keynode: snapshot.getChildren()){
                    UserData userData=keynode.getValue(UserData.class);
                    if (userData.getEmail().equals(risDataList.get(position).getId())){
                        holder.name.setText(userData.getName() + "" + userData.getLastName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return risDataList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key = charSequence.toString();
                if (key.isEmpty()){
                    risDataListFilter = risDataList;
                }else{
                    List<RISData>filter=new ArrayList<>();
                    for (RISData row : risDataList){
                        if (row.getDelegation().toLowerCase().contains(key.toLowerCase())){
                            filter.add(row);
                        }
                    }
                    risDataListFilter = filter;
                }
                FilterResults filterResults= new FilterResults();
                filterResults.values= risDataListFilter;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                risDataListFilter =(List<RISData>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    //En esta clase se contendra los elementos
    public static class SARHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView date;
        private final TextView delegation;
        private final TextView service;
        private final TextView type_patient;
        private final TextView name;
        private OnRisListener onRISListener;
        public SARHolder(@NonNull View itemView, OnRisListener onRISListener) {
            super(itemView);
            LinearLayout linearLayout = itemView.findViewById(R.id.linear_sar);
            date= itemView.findViewById(R.id.text_date);
            delegation=itemView.findViewById(R.id.text_delegation);
            service=itemView.findViewById(R.id.text_service);
            type_patient=itemView.findViewById(R.id.text_typePatient);
            name=itemView.findViewById(R.id.text_name);
            this.onRISListener = onRISListener;
            linearLayout.setOnClickListener(this);
        }

        public SARHolder(@NonNull View itemView, OnRisEditListener onRisEditListener) {
            super(itemView);
            ImageView iv = itemView.findViewById(R.id.image_edit);
            date= itemView.findViewById(R.id.text_date);
            delegation=itemView.findViewById(R.id.text_delegation);
            service=itemView.findViewById(R.id.text_service);
            type_patient=itemView.findViewById(R.id.text_typePatient);
            name=itemView.findViewById(R.id.text_name);
            this.onRISListener = onRISListener;
            iv.setOnClickListener(view -> onRisEditListener.OnRisEditClick(getAdapterPosition()));
        }

        @Override
        public void onClick(View view) {
            onRISListener.onRisClick(getAdapterPosition());
        }
    }

    public interface OnRisListener {
        void onRisClick(int position);
    }

    public interface OnRisEditListener {
        void OnRisEditClick(int position);
    }


}
