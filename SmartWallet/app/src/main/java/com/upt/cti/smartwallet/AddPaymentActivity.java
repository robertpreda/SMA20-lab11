package com.upt.cti.smartwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPaymentActivity extends AppCompatActivity {

    private EditText ePayName, ePayType;
    private EditText ePayCost;

    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        ePayCost = findViewById(R.id.ePayCost);
        ePayName = findViewById(R.id.paymentName);
        ePayType = findViewById(R.id.paymentType);

        addButton = findViewById(R.id.addPayment);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = ePayName.getText().toString();
                String newType = ePayType.getText().toString();
                double newCost = Double.parseDouble(ePayCost.getText().toString());

                Payment newPayment = new Payment(newName, newType, newCost);
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("newPayment", newPayment);
                startActivity(intent);

            }
        });


    }
}