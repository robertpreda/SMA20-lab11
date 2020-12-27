package com.upt.cti.smartwallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import model.MonthlyExpenses;


public class MainActivity extends AppCompatActivity {

    private TextView tStatus;
    private EditText eSearch, eIncome, eExpenses;
    private Spinner monthSpinner;

    private DatabaseReference databaseReference;
    private ValueEventListener databaseListener;

    private final List<String> monthNames = new ArrayList<>();
    private final List<MonthlyExpenses> monthlyExpenses = new ArrayList<>();

    private ArrayAdapter<String> sAdapter;

    private String currentMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eIncome = findViewById(R.id.eIncome);
        eExpenses = findViewById(R.id.eExp);
        monthSpinner = findViewById(R.id.monthsSpinner);
        monthSpinner.setPrompt("Select month: ");

        sAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, monthNames);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(sAdapter);

        databaseReference.child("calendar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot ds: snapshot.getChildren()) {
                    monthNames.add(ds.getKey());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void createNewDBListener() {
        // remove previous databaseListener

        if (databaseReference != null && currentMonth != null && databaseListener != null)
            databaseReference.child("calendar").child(currentMonth).removeEventListener(databaseListener);

        databaseListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            MonthlyExpenses monthlyExpense = dataSnapshot.getValue(MonthlyExpenses.class);
            // explicit mapping of month name from entry key
            monthlyExpense.month = dataSnapshot.getKey();

            eIncome.setText(String.valueOf(monthlyExpense.getIncome()));
            eExpenses.setText(String.valueOf(monthlyExpense.getExpenses()));
            tStatus.setText("Found entry for " + currentMonth);
        }



        @Override
        public void onCancelled(DatabaseError error) {
            }
        };

        // set new databaseListener
        databaseReference.child("calendar").child(currentMonth).addValueEventListener(databaseListener);
    }

    public void clicked(View view){
        switch(view.getId()){

            case R.id.bUpdate:
                break;
        }
    }
}