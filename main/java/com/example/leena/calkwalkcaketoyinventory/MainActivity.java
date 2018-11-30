package com.example.leena.calkwalkcaketoyinventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button cakeToyListbtn;
    Button ordersBtn;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createBtns();
        cakeToyListbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CakeToyList.class);
                startActivity(i);
            }
        });
        ordersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OrderHistory.class);
                startActivity(i);
            }
        });
    }
    private void createBtns() {
        cakeToyListbtn = (Button)findViewById(R.id.CakeToyButton);
        ordersBtn = (Button)findViewById(R.id.OrdersBtn);
    }
}
