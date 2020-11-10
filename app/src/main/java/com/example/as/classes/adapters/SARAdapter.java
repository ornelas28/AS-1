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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.as.R;
import com.example.as.classes.database.ConstantsDataBase;
import com.example.as.classes.database.SARData;
import com.example.as.classes.database.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SARAdapter extends RecyclerView.Adapter<SARAdapter.SARHolder> implements Filterable{
    private Context context;
    private List<SARData> sarDataList;
    private List<SARData> sarDataListFilter;

    public SARAdapter(Context context, List<SARData> sarDataList, List<SARData> sarDataListFilter, OnSarEditListener onSarEditListener) {
        this.context = context;
        this.sarDataList = sarDataList;
        this.sarDataListFilter = sarDataListFilter;
        this.onSarEditListener = onSarEditListener;
    }

    private SARAdapter.OnSarEditListener onSarEditListener;

    public SARAdapter(Context context, List<SARData> sarDataList, OnSARListener onSARListener) {
        this.context = context;
        this.sarDataList = sarDataList;
        this.onSARListener = onSARListener;
        this.sarDataListFilter = sarDataList;
    }

    private SARAdapter.OnSARListener onSARListener;

    @NonNull
    @Override
    public SARHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout=LayoutInflater.from(context).inflate(R.layout.item_sar, parent, false);
        return new SARHolder(layout, onSARListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SARHolder holder, int position) {
        holder.delegation.setText(sarDataList.get(position).getDelegation());
        holder.date.setText((sarDataList.get(position).getDate()));
        holder.service.setText(sarDataList.get(position).getService());
        holder.type_patient.setText(sarDataList.get(position).getTypePatient());
        FirebaseDatabase.getInstance().getReference(ConstantsDataBase.USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot keynode: snapshot.getChildren()){
                    UserData userData=keynode.getValue(UserData.class);
                    if (userData.getEmail().equals(sarDataList.get(position).getId())){
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
        return sarDataList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key = charSequence.toString();
                if (key.isEmpty()){
                    sarDataListFilter=sarDataList;
                }else{
                    List<SARData>filter=new ArrayList<>();
                    for (SARData row : sarDataList){
                        if (row.getDelegation().toLowerCase().contains(key.toLowerCase())){
                            filter.add(row);
                        }
                    }
                    sarDataListFilter= filter;
                }
                FilterResults filterResults= new FilterResults();
                filterResults.values=sarDataListFilter;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                sarDataListFilter=(List<SARData>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    //En esta clase se contendra los elementos
    public static class SARHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView date,delegation,service,type_patient,name;
        private ImageView iv;
        private LinearLayout linearLayout;
        private SARAdapter.OnSARListener onSARListener;
        public SARHolder(@NonNull View itemView, SARAdapter.OnSARListener onSARListener) {
            super(itemView);
            date= itemView.findViewById(R.id.text_date);
            delegation=itemView.findViewById(R.id.text_delegation);
            service=itemView.findViewById(R.id.text_service);
            type_patient=itemView.findViewById(R.id.text_typePatient);
            name=itemView.findViewById(R.id.text_name);
            linearLayout=itemView.findViewById(R.id.linear_sar);
            linearLayout.setOnClickListener(this);
            this.onSARListener=onSARListener;
        }

        public SARHolder(@NonNull View itemView, SARAdapter.OnSarEditListener onSarEditListener) {
            super(itemView);
            date= itemView.findViewById(R.id.text_date);
            delegation=itemView.findViewById(R.id.text_delegation);
            service=itemView.findViewById(R.id.text_service);
            type_patient=itemView.findViewById(R.id.text_typePatient);
            name=itemView.findViewById(R.id.text_name);
            iv=itemView.findViewById(R.id.image_edit);
            iv.setOnClickListener(view -> {
                onSarEditListener.OnSarEditClick(getAdapterPosition());
            });
            this.onSARListener=onSARListener;
        }

        @Override
        public void onClick(View view) {
            onSARListener.onSARClick(getAdapterPosition());
        }
    }

    public interface OnSARListener {
        void onSARClick(int position);
    }

    public interface OnSarEditListener{
        void OnSarEditClick(int position);
    }


}
