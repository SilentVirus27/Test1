package com.example.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerVievCategoryAdapter extends RecyclerView.Adapter<RecyclerVievCategoryAdapter.ViewHolder>{
    Context context;
    ArrayList<ViewCategoryStructure>arrayCatList;

    RecyclerVievCategoryAdapter(Context context, ArrayList<ViewCategoryStructure>arrayCatList){
        this.context=context;
        this.arrayCatList=arrayCatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View catView=LayoutInflater.from(parent.getContext()).inflate(R.layout.accountcategory_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(catView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCategory.setText(arrayCatList.get(position).category);
    }

    @Override
    public int getItemCount() {
        return arrayCatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategory=itemView.findViewById(R.id.catTextRow);
        }
    }
}
