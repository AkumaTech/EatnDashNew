package com.example.trident.eatndash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MealDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);
        getSupportActionBar().setTitle("Carls Jr");


        //new buttons for cart
        final TextView totalPrice = (TextView) findViewById(R.id.price);
        final TextView labelQuantity = (TextView) findViewById(R.id.label_quantity);
        Button buttonIncrease = (Button) findViewById(R.id.button_increase);
        Button buttonDecrease = (Button) findViewById(R.id.button_decrease);

       // calculation increase
        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(labelQuantity.getText().toString());

                qty = qty + 1;
                labelQuantity.setText(qty + "");
                totalPrice.setText("S" + (qty * 7.00));


            }
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(labelQuantity.getText().toString());
                if(qty > 1) {
                    qty = qty - 1;
                    labelQuantity.setText(qty + "");
                    totalPrice.setText("S" + (qty * 7.00));

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meal_details, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
