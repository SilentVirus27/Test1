package com.example.test1;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


public class IncomeExpenseFragment extends Fragment {

    private String date;
    private EditText etDate,etAmount,etNote;
    DatePickerDialog.OnDateSetListener setListener;

    private FirebaseAuth mAuth;
    RadioButton transaction_type_selected,payment_type_selected;
    RadioGroup tranRadioGroup , paymentRadioGroup;
    int selectedId,paymentSelectedID;
    Button confirm_add;
    String subTransaction_type,subPayment_type,subCategory,subAmount,subNote,subDate,subUid;
    public String selectedPaymentType,selectedCategory;

    //Initialize spinner
    Spinner paymentType,paymentCategory;
    ArrayList<String> paymentTypeList=new ArrayList<>();
    ArrayList<String> arrayCatListOnline=new ArrayList<>();
    ArrayList<String> arrayCatListCash=new ArrayList<>();

    public IncomeExpenseFragment() {
        // Required empty public constructor
    }


    public static IncomeExpenseFragment newInstance(String param1, String param2) {
        IncomeExpenseFragment fragment = new IncomeExpenseFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_income_expense, container, false);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser fuser = mAuth.getCurrentUser();
        DatabaseReference catRef= FirebaseDatabase.getInstance().getReference("Category").child(fuser.getUid());
        DatabaseReference userRef= FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        DatabaseReference transactionRef= FirebaseDatabase.getInstance().getReference("Transaction").child(fuser.getUid());
        paymentType =(Spinner) viewRoot.findViewById(R.id.showPaymentMode);
        paymentCategory =(Spinner) viewRoot.findViewById(R.id.showCategoryToSelect);
        etAmount=viewRoot.findViewById(R.id.amount);
        etNote=viewRoot.findViewById(R.id.note);
        confirm_add=viewRoot.findViewById(R.id.confirm_add);
        etDate = viewRoot.findViewById(R.id.et_date);

        //******** Default Load ***********************************
        LoadData(catRef);
        //******** Radio button set ***********************************
        tranRadioGroup=(RadioGroup) viewRoot.findViewById(R.id.typeRadioGroupIE);

        //******** Date Set ***************************************
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        String defaultDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        etDate.setText(defaultDate);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month=month+1;
                        date=dayOfMonth+"/"+month+"/"+year;
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //******** Dependant Spinner **************************

        paymentTypeList.add("Online");
        paymentTypeList.add("Cash");
        ArrayAdapter<String> paymentTypeAdapter = new ArrayAdapter<String>(this.getActivity(),R.layout.spinner_style, paymentTypeList);
        ArrayAdapter<String> OnlineAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_style, arrayCatListOnline);
        ArrayAdapter<String> CashAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_style, arrayCatListCash);
        paymentType.setAdapter(paymentTypeAdapter);
        paymentTypeAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        paymentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedPaymentType = paymentTypeList.get(position).trim().toString();
                if(selectedPaymentType.equals(paymentTypeList.get(0))){
                    paymentCategory.setAdapter(OnlineAdapter);
                    OnlineAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
                }else if(selectedPaymentType.equals(paymentTypeList.get(1))) {
                    paymentCategory.setAdapter(CashAdapter);
                    CashAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
                }else {Toast.makeText(getContext(), "Choose Account Type To View Category", Toast.LENGTH_SHORT).show();}

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //******** Submit Data **************************
        confirm_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = tranRadioGroup.getCheckedRadioButtonId();
                transaction_type_selected = (RadioButton) viewRoot.findViewById(selectedId);

                String transactionId = UUID.randomUUID().toString();

                subTransaction_type=transaction_type_selected.getText().toString().trim();
                subPayment_type = (String) paymentType.getItemAtPosition(paymentType.getSelectedItemPosition());
                subCategory=(String) paymentCategory.getItemAtPosition(paymentCategory.getSelectedItemPosition());
                subAmount = etAmount.getText().toString().trim();
                subNote =etNote.getText().toString().trim();
                subDate = etDate.getText().toString().trim();
                subUid=fuser.getUid();
                if (TextUtils.isEmpty(subAmount)) {
                    etAmount.setError("Please Enter Amount");
                    return;
                }
                if (TextUtils.isEmpty(subNote)) {
                    etNote.setError("Please Enter Note");
                    return;
                }
                if (TextUtils.isEmpty(subDate)) {
                    etDate.setError("Please Select Date");
                    return;
                }
                ExpenseTransaction transaction = new ExpenseTransaction(transactionId,subTransaction_type, subPayment_type, subCategory, subAmount, subNote, subDate,subUid);
                transactionRef.child(transactionId).setValue(transaction).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Transaction Successful 🤩 ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Transaction Fail 😢 ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                /*if(subTransaction_type.equals("Expense")) {
                    ExpenseTransaction transaction = new ExpenseTransaction(transactionId,subTransaction_type, subPayment_type, subCategory, subAmount, subNote, subDate);
                    transactionRef.child("Expenses").child(transactionId).setValue(transaction).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Transaction Successful 🤩 ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Transaction Fail 😢 ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    IncomeTransaction transaction=new IncomeTransaction(transactionId,subTransaction_type, subPayment_type, subAmount, subNote, subDate);
                    transactionRef.child("Income").child(transactionId).setValue(transaction).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Transaction Successful 🤩 ", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), "Transaction Fail 😢 ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }*/
            }
        });



        //return view
        return viewRoot;
    }

    private void LoadData(DatabaseReference catRef) {
        catRef.child("Online").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String cat = ds.getValue(String.class);
                        if (arrayCatListOnline.contains(cat)){
                            //Nothing to Do Just Pass it to next iteration
                        }else{
                            arrayCatListOnline.add(cat);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        catRef.child("Cash").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String cat = ds.getValue(String.class);
                        if (arrayCatListCash.contains(cat)){
                            //Nothing to Do Just Pass it to next iteration
                        }else{
                            arrayCatListCash.add(cat);
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