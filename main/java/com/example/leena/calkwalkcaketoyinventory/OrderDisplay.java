package com.example.leena.calkwalkcaketoyinventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OrderDisplay extends Activity {

    static String orderID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdisplayer);

        EditText cakeWeightTxt = (EditText) findViewById(R.id.cakeWeightTextDisp);
        EditText orderToyNameTxt = (EditText) findViewById(R.id.orderToyNameDisp);
        EditText orderToyIDTxt = (EditText) findViewById(R.id.orderToyIDDisp);
        EditText orderDateTxt = (EditText) findViewById(R.id.orderDateDisp);
        EditText deliveryTimeTxt = (EditText) findViewById(R.id.deliveryTimeTextDisp);
        EditText cakeFlavorTxt = (EditText) findViewById(R.id.cakeFlavorTextDisp);
        EditText msgTxt = (EditText) findViewById(R.id.msgTextDisp);
        EditText splRequestTxt = (EditText) findViewById(R.id.splRequestTextDisp);
        EditText commentTxt = (EditText) findViewById(R.id.commentTextDisp);
        EditText locationTxt = (EditText) findViewById(R.id.locationTextDisp);

        Button backBtn = (Button)findViewById(R.id.orderBackBtnDisp);

        int index = -1;
        for (int i = 0; i < OrderHistory.orderList.size(); i++) {
            if (orderID.compareTo(OrderHistory.orderList.get(i).getOrderID()) == 0) {
                index = i;
                break;
            }
        }

        cakeWeightTxt.setText(OrderHistory.orderList.get(index).getCakeWeight());
        orderToyNameTxt.setText(OrderHistory.orderList.get(index).getToyName());
        orderToyIDTxt.setText(OrderHistory.orderList.get(index).getToyID());
        orderDateTxt.setText(OrderHistory.orderList.get(index).getDate());
        deliveryTimeTxt.setText(OrderHistory.orderList.get(index).getDeliveryTime());
        cakeFlavorTxt.setText(OrderHistory.orderList.get(index).getCakeFlavor());
        msgTxt.setText(OrderHistory.orderList.get(index).getMsg());
        splRequestTxt.setText(OrderHistory.orderList.get(index).getSplRequest());
        commentTxt.setText(OrderHistory.orderList.get(index).getComment());
        locationTxt.setText(OrderHistory.orderList.get(index).getLocation());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderDisplay.this, OrderHistory.class);
                startActivity(i);
            }
        });
    }
}
