package com.example.test1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class ProfileFragment extends Fragment  {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private TextView uname ,ucno,uemail;
    Button signout ,update;
    String name1,contact1,email1 ;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        signout=(Button) view.findViewById(R.id.signout);
        /*update=(Button) view.findViewById(R.id.update_profile);*/
        mAuth = FirebaseAuth.getInstance();
        uname = view.findViewById(R.id.p_et_name);
        ucno = view.findViewById(R.id.p_et_contactno);

        uemail=view.findViewById(R.id.p_et_email);


        databaseReference =FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, String> map  = (Map) snapshot.getValue();
                if(map!=null){
                    name1=map.get("name");
                    contact1=map.get("conatact");
                    email1=map.get("email");
                }

                uname.setText(name1);
                ucno.setText(contact1);
                uemail.setText(email1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Faild! to Get Data", Toast.LENGTH_SHORT).show();
            }
        });

        /*update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}