package com.example.test1;

import android.content.Context;
import android.hardware.lights.LightState;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    List<ViewTransactionStructure> viewTransactionStructures=new ArrayList<>();
    Button show;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewTransactionStructures.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_home, container, false);
        getDatabaseData();
        recyclerView=rootView.findViewById(R.id.dash_recyclerview);
        show=rootView.findViewById(R.id.view_transaction);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                TransactionAdapter transactionAdapter=new TransactionAdapter(getContext(),viewTransactionStructures);
                recyclerView.setAdapter(transactionAdapter);
            }
        });

        return rootView;
    }

    private void getDatabaseData() {
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser fuser = mAuth.getCurrentUser();
        DatabaseReference transactionRef= FirebaseDatabase.getInstance().getReference("Transaction").child(fuser.getUid());
        transactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    if(snapshot.hasChildren()){
                        if(snapshot.hasChild(ds.getKey())){
                            String transactionId=ds.child("transactionId").getValue(String.class);
                            String subUid = ds.child("subUid").getValue(String.class);
                            String subTransaction_type = ds.child("subTransaction_type").getValue(String.class);
                            String subPayment_type = ds.child("subPayment_type").getValue(String.class);
                            String subCategory = ds.child("subCategory").getValue(String.class);
                            String subAmount = ds.child("subAmount").getValue(String.class);
                            String subNote = ds.child("subNote").getValue(String.class);
                            String subDate = ds.child("subDate").getValue(String.class);
                            viewTransactionStructures.add(new ViewTransactionStructure(subTransaction_type,subPayment_type,subCategory,subAmount,subNote,subDate));
                            String msg=subTransaction_type+" "+subPayment_type+" "+subCategory+" "+subAmount+" "+subNote+" "+subDate+" "+transactionId+" "+subUid;
                            Log.w("Reading data",""+msg);
                        }
                    }else Log.w("Read Data","No Data Available");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}