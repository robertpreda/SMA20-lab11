package com.upt.cti.smartwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddPaymentDialogActivity extends AppCompatActivity {

    private EditText description, cost;
    private TextView textView1, timeStampTextView;
    private Button saveButton, deleteButton;
    private Spinner sType;
    private Payment payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_dialog);

        this.description = findViewById(R.id.eDesc);
        this.cost = findViewById(R.id.eCost);
        this.textView1 = findViewById(R.id.textView1);
        this.timeStampTextView = findViewById(R.id.eTimestamp);
        this.saveButton = findViewById(R.id.saveButton);
        this.deleteButton = findViewById(R.id.deleteButton);
        this.sType = findViewById(R.id.paymentsSpinner);

        String[] types = {"Tech", "Food & Drinks", "Education", "Entertainment", "Other"};
        final ArrayAdapter<String> sAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sType.setAdapter(sAdapter);

        this.payment = AppState.get().getCurrentPayment();
        if(payment != null)
        {
            description.setText(payment.getName());
            cost.setText(String.valueOf(payment.getCost()));
            timeStampTextView.setText(payment.timestamp);


            try
            {
                sType.setSelection(Arrays.asList(types).indexOf(payment.getType()));
            }
            catch (Exception e)
            {

            }
        }
        else{
                timeStampTextView.setText("");
            }


        }

        public static String getCurrentTimeDate(){
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date now = new Date();
            return sdfDate.format(now);
        }

        public void clickListener(View view){
            switch (view.getId()){
                case R.id.saveButton:
                    if(payment != null){
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", payment.getName());
                        map.put("cost", Double.parseDouble(cost.getText().toString()));
                        map.put("type", payment.getType());
                        AppState.get().getDatabaseReference().child("wallet").updateChildren(map);
                    }
                    break;
                case R.id.deleteButton:
                    AppState.get().getDatabaseReference().child("wallet").child(timeStampTextView.getText().toString()).removeValue();
                    finish();
            }
        }






}



