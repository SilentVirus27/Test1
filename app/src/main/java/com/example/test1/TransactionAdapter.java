package com.example.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    private Context context;
    private List<ViewTransactionStructure> expenseTransactionList;


    public TransactionAdapter(Context context, List<ViewTransactionStructure> expenseTransactionList) {
        this.context = context;
        this.expenseTransactionList=expenseTransactionList;

    }

    /*public void addExpense(ExpenseTransaction expenseTransaction){
        expenseTransactionList.add(expenseTransaction);
        notifyDataSetChanged();
    }*/

   /* public void addIncome(IncomeTransaction incomeTransaction){
        incomeTransactionList.add(incomeTransaction);
        notifyDataSetChanged();
    }*/

    /*public void cleareExpenseTransactionList(){
        expenseTransactionList.clear();
        notifyDataSetChanged();
    }*/

    /*public void cleareincomeTransactionList(){
        incomeTransactionList.clear();
        notifyDataSetChanged();
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_row,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(rootView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNote.setText(String.valueOf(expenseTransactionList.get(position).subNote));
        holder.tvCategory.setText(expenseTransactionList.get(position).subCategory);
        holder.tvDate.setText(expenseTransactionList.get(position).subDate);
        holder.tvPaymentMode.setText(expenseTransactionList.get(position).subPayment_type);
        holder.tvAmount.setText(String.valueOf(expenseTransactionList.get(position).subAmount));
        holder.tvTransaction_type.setText(String.valueOf(expenseTransactionList.get(position).subTransaction_type));
    }

    @Override
    public int getItemCount() {
        return expenseTransactionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTransaction_type,tvNote,tvCategory,tvDate,tvPaymentMode,tvAmount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNote=itemView.findViewById(R.id.rownote);
            tvCategory=itemView.findViewById(R.id.rowcategory);
            tvDate=itemView.findViewById(R.id.rowdate);
            tvPaymentMode=itemView.findViewById(R.id.rowpaymentMode);
            tvAmount=itemView.findViewById(R.id.rowamount);
            tvTransaction_type=itemView.findViewById(R.id.rowTransactionType);
        }
    }
}
