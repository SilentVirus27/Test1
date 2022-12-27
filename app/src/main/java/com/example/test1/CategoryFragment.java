package com.example.test1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryFragment extends Fragment {
    private ProgressBar signupProgress;
    private FirebaseAuth mAuth;
    EditText et_category;
    RadioButton acc_type_selected;
    RadioGroup radioGroup;
    int selectedId;
    Button confirm_add;
    String category,acc_type;
    public String item;
    View viewRoot;
    String availability="notAvailable";
    RecyclerView recyclerViewOnline,recyclerViewCash;
    ArrayList<ViewCategoryStructure>arrayCatListOnline=new ArrayList<>();
    ArrayList<ViewCategoryStructure>arrayCatListCash=new ArrayList<>();



    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        viewRoot =  inflater.inflate(R.layout.fragment_category, container, false);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser fuser = mAuth.getCurrentUser();
        DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference("Category").child(fuser.getUid());

        //************************************************************************
        //Add Category
        confirm_add=(Button) viewRoot.findViewById(R.id.confirm_add_category);
        confirm_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_category=(EditText) viewRoot.findViewById(R.id.et_category);
                radioGroup=(RadioGroup) viewRoot.findViewById(R.id.typeRadioGroup);
                selectedId = radioGroup.getCheckedRadioButtonId();
                acc_type_selected = (RadioButton) viewRoot.findViewById(selectedId);
                acc_type=acc_type_selected.getText().toString().trim();
                category=et_category.getText().toString().trim();
                if (TextUtils.isEmpty(category)) {
                    et_category.setError("Please Enter Name");
                    return;
                }

                if (acc_type.equals("Online")){
                    rootRef.child("Online").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChildren()){
                                List<String> catOnList = new ArrayList<>();
                                for(DataSnapshot ds : snapshot.getChildren()) {
                                    String cat = ds.getValue(String.class);
                                    if(cat.equals(category)){
                                        availability="Available";
                                    }
                                    catOnList.add(cat);
                                }
                                if(availability.equals("Available")){
                                    Toast.makeText(getContext(), "Category Already Exists!", Toast.LENGTH_LONG).show();
                                    availability="notAvailable";
                                }else {
                                    catOnList.add(category);
                                }
                                rootRef.child("Online").setValue(catOnList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            if(availability.equals("notAvailable")) {
                                                Toast.makeText(getContext(), "Category Added Successful!", Toast.LENGTH_LONG).show();
                                                arrayCatListCash.clear();
                                                arrayCatListOnline.clear();
                                                LoadData(rootRef);
                                            }
                                        }else {
                                            try {
                                                throw task.getException();
                                            } catch (Exception e) {
                                                Log.e("Error", e.getMessage());
                                            }
                                            Toast.makeText(getContext(), "Failed! Please Try Again!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else{
                                List<String> catFList=new ArrayList<String>();
                                catFList.add(category);
                                rootRef.child("Online").setValue(catFList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getContext(), "Category Added Successful!", Toast.LENGTH_LONG).show();
                                            arrayCatListCash.clear();
                                            arrayCatListOnline.clear();
                                            LoadData(rootRef);
                                        }else {
                                            try {
                                                throw task.getException();
                                            } catch (Exception e) {
                                                Log.e("Error", e.getMessage());
                                            }
                                            Toast.makeText(getContext(), "Failed! Please Try Again!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Getting Post failed, log a message
                            Log.w("loadDatabase:onCancelled", error.toException());
                        }
                    });
                }else{
                    rootRef.child("Cash").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChildren()){
                                List<String> catCashList = new ArrayList<>();
                                for(DataSnapshot ds : snapshot.getChildren()) {
                                    String cat = ds.getValue(String.class);
                                    if(cat.equals(category)){
                                        availability="Available";
                                    }
                                    catCashList.add(cat);
                                }
                                if(availability.equals("Available")){
                                    Toast.makeText(getContext(), "Category Already Exists!", Toast.LENGTH_LONG).show();
                                    availability="notAvailable";
                                }else {
                                    catCashList.add(category);
                                }

                                rootRef.child("Cash").setValue(catCashList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            if(availability.equals("notAvailable")) {
                                                Toast.makeText(getContext(), "Category Added Successful!", Toast.LENGTH_SHORT).show();
                                                arrayCatListCash.clear();
                                                arrayCatListOnline.clear();
                                                LoadData(rootRef);

                                            }
                                        }else {
                                            try {
                                                throw task.getException();
                                            } catch (Exception e) {
                                                Log.e("Error", e.getMessage());
                                            }
                                            Toast.makeText(getContext(), "Failed! Please Try Again!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else{
                                List<String> catCList=new ArrayList<String>();
                                catCList.add(category);
                                rootRef.child("Cash").setValue(catCList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getContext(), "Category Added Successful!", Toast.LENGTH_SHORT).show();
                                        }else {
                                            try {
                                                throw task.getException();
                                            } catch (Exception e) {
                                                Log.e("Error", e.getMessage());
                                            }
                                            Toast.makeText(getContext(), "Failed! Please Try Again!", Toast.LENGTH_LONG).show();
                                            arrayCatListCash.clear();
                                            arrayCatListOnline.clear();
                                            LoadData(rootRef);
                                        }
                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Getting Post failed, log a message
                            Log.w("loadDatabase:onCancelled", error.toException());
                        }
                    });
                }
            }
        });
        //*************************************************************************
        //Load Cat Data At Start Default
        LoadData(rootRef);
        //*************************************************************************
        //Spinner to select account
        final Spinner spinner = (Spinner) viewRoot.findViewById(R.id.viewCatSelect);
        List<String> SelectAccountOption = new ArrayList<String>();
        SelectAccountOption.add("Online");
        SelectAccountOption.add("Cash");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(),R.layout.spinner_style, SelectAccountOption);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // On selecting a spinner item
                item = SelectAccountOption.get(position).trim().toString();
                //View Different Account category
                recyclerViewOnline=(RecyclerView) viewRoot.findViewById(R.id.viewCategoryInAccountSelectedOnline);
                recyclerViewCash=(RecyclerView) viewRoot.findViewById(R.id.viewCategoryInAccountSelectedOnline);
                recyclerViewOnline.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerViewCash.setLayoutManager(new LinearLayoutManager(getContext()));

                if(item.equals(SelectAccountOption.get(0))){
                    recyclerViewCash.setVisibility(View.GONE);
                    recyclerViewOnline.setVisibility(View.VISIBLE);
                    RecyclerVievCategoryAdapter adapterOnline=new RecyclerVievCategoryAdapter(getContext(),arrayCatListOnline);
                    recyclerViewOnline.setAdapter(adapterOnline);
                }else if(item.equals(SelectAccountOption.get(1))) {
                    recyclerViewOnline.setVisibility(View.GONE);
                    recyclerViewCash.setVisibility(View.VISIBLE);
                    RecyclerVievCategoryAdapter adapterCash=new RecyclerVievCategoryAdapter(getContext(),arrayCatListCash);
                    recyclerViewCash.setAdapter(adapterCash);
                }else {Toast.makeText(getContext(), "Choose Account Type To View Category", Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(), "List Cleared", Toast.LENGTH_SHORT).show();
                arrayCatListCash.clear();
                arrayCatListOnline.clear();
            }
        });

        return viewRoot;
    }

    private void LoadData(DatabaseReference rootRef) {
        rootRef.child("Online").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String cat = ds.getValue(String.class);
                        if (arrayCatListOnline.contains(new ViewCategoryStructure(cat))){
                            //Nothing to Do Just Pass it to next iteration
                        }else{
                            arrayCatListOnline.add(new ViewCategoryStructure(cat));
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        rootRef.child("Cash").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String cat = ds.getValue(String.class);
                        if (arrayCatListCash.contains(new ViewCategoryStructure(cat))){
                            //Nothing to Do Just Pass it to next iteration
                        }else{
                            arrayCatListCash.add(new ViewCategoryStructure(cat));
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}