package com.example.leena.calkwalkcaketoyinventory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class OrderHistory extends Activity {

    Button menuBtn;
    Button addBtn;
    Spinner spnrSearchFilter;
    ListView cakeToyOrderList;
    EditText searchText;
    DBHelper db;
    static ArrayList<OrderData> orderList;
    OrderListAdapter orderListAdapter;

    static String searchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderhistory);
        menuBtn = (Button) findViewById(R.id.MenuOrderBtn);
        addBtn = (Button) findViewById(R.id.AddToyBtnOrder);
        cakeToyOrderList = (ListView) findViewById(R.id.CakeToyOrderList);
        spnrSearchFilter = (Spinner) findViewById(R.id.spnrSearchFilter);
        searchText = (EditText)findViewById(R.id.searchText);
        db = new DBHelper(this);
        if (CakeToyList.toyList == null) {
            CakeToyList.toyList = new ArrayList<ToyData>();
            CakeToyList.toyList = db.getListContentToy();
        }
        orderList = new ArrayList<OrderData>();
        orderList = db.getListContentOrder();
        orderListAdapter = new OrderListAdapter(this);
        cakeToyOrderList.setAdapter(orderListAdapter);

        final String[] items = {"Location", "Date"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(OrderHistory.this, android.R.layout.simple_spinner_dropdown_item, items);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrSearchFilter.setAdapter(arrayAdapter);
       // loadList();
        // cakeToyOrderList.setAdapter(new ArrayAdapter<String>(this, R.layout.ordertoyselectionlistrow, R.id.nameDisplay, nameData));
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderHistory.this, MainActivity.class);
                startActivity(i);
            }
        });
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                orderListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spnrSearchFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchState = items[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cakeToyOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderDisplay.orderID = orderList.get(position).getOrderID();
                Intent i = new Intent(OrderHistory.this, OrderDisplay.class);
                startActivity(i);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogue = new AlertDialog.Builder(OrderHistory.this);
                View mView = getLayoutInflater().inflate(R.layout.orderalertdialogue, null);

                final EditText dateText = (EditText) mView.findViewById(R.id.orderDate);
                final EditText toyName = (EditText) mView.findViewById(R.id.orderToyName);
                final EditText toyID = (EditText) mView.findViewById(R.id.orderToyID);
                final EditText deliveryTime = (EditText) mView.findViewById(R.id.deliveryTimeText);
                final EditText location = (EditText) mView.findViewById(R.id.locationText);
                final EditText cakeWeight = (EditText) mView.findViewById(R.id.cakeWeightText);
                final EditText cakeFlavor = (EditText) mView.findViewById(R.id.cakeFlavorText);
                final EditText msg = (EditText) mView.findViewById(R.id.msgText);
                final EditText splRequest = (EditText) mView.findViewById(R.id.splRequestText);
                final EditText comment = (EditText) mView.findViewById(R.id.commentText);

                final Button addBtn = (Button) mView.findViewById(R.id.orderAddBtn);
                final Button cancelBtn = (Button) mView.findViewById(R.id.orderBackBtnDisp);

                alertDialogue.setView(mView);
                final AlertDialog dialog = alertDialogue.create();
                dialog.show();

                dateText.getText().clear();
                toyName.getText().clear();
                toyID.getText().clear();

                dateText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        int mYear, mMonth, mDay;
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(OrderHistory.this, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                        dateText.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
                                    }
                                }, mYear, mMonth, mDay);

                        datePickerDialog.show();
                    }
                });

                deliveryTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        int mHour, mMinute;

                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);


                        TimePickerDialog timePickerDialog = new TimePickerDialog(OrderHistory.this, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                        if (hourOfDay > 12) {
                                            if (minute < 10) {
                                                deliveryTime.setText((hourOfDay - 12) + ":0" + minute + " " + "PM");
                                            }
                                            else {
                                                deliveryTime.setText((hourOfDay - 12) + ":" + minute + " " + "PM");
                                            }
                                        }
                                        else {
                                            if (minute < 10) {
                                                deliveryTime.setText((hourOfDay - 12) + ":0" + minute + " " + "AM");
                                            }
                                            else {
                                                deliveryTime.setText(hourOfDay + ":" + minute + " " + "AM");
                                            }
                                        }
                                    }
                                }, mHour, mMinute, false);

                        timePickerDialog.show();
                    }
                });

                location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderHistory.this);
                        builder.setTitle("Pick a Location");

                        final String[] items = {"Plainsboro", "East Windsor", "South Brunswick"};
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int index) {
                                location.setText(items[index]);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                cakeFlavor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderHistory.this);
                        final String[] items = {"Pineapple", "Butter Scotch", "BlackForest", "Mango", "Mixed Fruit", "Chocolate", "Strawberry"};
                        final ArrayList<String> selectedItems = new ArrayList<String>();

                        builder.setTitle("Pick all Flavors").setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int index, boolean isChecked) {
                                                if (isChecked) {
                                                    selectedItems.add(items[index]);
                                                } else if (!isChecked) {
                                                    for (int i = 0; i < selectedItems.size(); i++) {
                                                        if (selectedItems.get(i) == items[index]) {
                                                            selectedItems.remove(i);
                                                        }
                                                    }
                                                }
                                            }
                                        })
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        String cakeFlavorText = "";
                                        for (int i = 0; i < selectedItems.size(); i++) {
                                            if (i == 0) {
                                                cakeFlavorText = cakeFlavorText.concat(selectedItems.get(i));
                                            }
                                            else {
                                               cakeFlavorText = cakeFlavorText.concat(" and " + selectedItems.get(i));
                                            }
                                        }
                                        cakeFlavor.setText(cakeFlavorText);
                                    }
                                })
                                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                    toyName.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                String inputText = s.toString();
                                for (int i = 0; i < CakeToyList.toyList.size(); i++) {
                                    if (inputText.compareTo(CakeToyList.toyList.get(i).getName()) == 0) {
                                        toyID.setText(CakeToyList.toyList.get(i).getID());
                                        break;
                                    } else {
                                        toyID.setText("");
                                    }
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dateText.getText().length() > 0 && (toyName.getText().length() > 0 || toyID.getText().length() > 0) && deliveryTime.getText().length() > 0
                                && location.getText().length() > 0 && cakeWeight.getText().length() > 0 && cakeFlavor.getText().length() > 0) {
                            if (toyID.getText().length() > 0) {
                                String toyOrderName = null;
                                for (int i = 0; i < CakeToyList.toyList.size(); i++) {
                                    int ID = Integer.parseInt(toyID.getText().toString());
                                    int elementID = Integer.parseInt(CakeToyList.toyList.get(i).getID());
                                    if (ID == elementID) {
                                        toyOrderName = CakeToyList.toyList.get(i).getName();
                                        Cursor cursor = db.getListContentsCursor();
                                        cursor.moveToPosition(i);
                                        db.updateToyData(cursor.getString(0), Integer.parseInt(CakeToyList.toyList.get(i).getID()), CakeToyList.toyList.get(i).getName(), Integer.parseInt(CakeToyList.toyList.get(i).getQuantity()) - 1);
                                        break;
                                    }
                                }
                                if (toyOrderName != null) {
                                    boolean success = db.insertDataOrderTable(dateText.getText().toString(), toyOrderName, Integer.parseInt(toyID.getText().toString()), deliveryTime.getText().toString(), Integer.parseInt(cakeWeight.getText().toString()),
                                            cakeFlavor.getText().toString(), msg.getText().toString(), splRequest.getText().toString(), comment.getText().toString(), location.getText().toString());
                                    if (success) {
                                        loadList();
                                        Toast.makeText(OrderHistory.this, "Successfully added to list", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dialog.cancel();
                                        Toast.makeText(OrderHistory.this, "There is an error adding it to the order list", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.cancel();
                                }
                                else {
                                    Toast.makeText(OrderHistory.this, "Entered id did not match any toy", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if (toyName.getText().length() > 0) {
                                int toyid = 0;
                                for (int i = 0; i < CakeToyList.toyList.size(); i++) {
                                    String toyname = toyName.getText().toString();
                                    String toyElementName = CakeToyList.toyList.get(i).getName();
                                    int elementID = Integer.parseInt(CakeToyList.toyList.get(i).getID());
                                    if (toyname.compareTo(toyElementName) == 0) {
                                        toyid = Integer.parseInt(CakeToyList.toyList.get(i).getID());
                                        Cursor cursor = db.getListContentsCursor();
                                        cursor.moveToPosition(i);
                                        db.updateToyData(cursor.getString(0), Integer.parseInt(CakeToyList.toyList.get(i).getID()), CakeToyList.toyList.get(i).getName(), Integer.parseInt(CakeToyList.toyList.get(i).getQuantity()) - 1);
                                        break;
                                    }
                                }
                                if (toyid > 0) {
                                    boolean success = db.insertDataOrderTable(dateText.getText().toString(), toyName.getText().toString(), toyid, deliveryTime.getText().toString(), Integer.parseInt(cakeWeight.getText().toString()),
                                            cakeFlavor.getText().toString(), msg.getText().toString(), splRequest.getText().toString(), comment.getText().toString(), location.getText().toString());
                                    if (success) {
                                        loadList();
                                        Toast.makeText(OrderHistory.this, "Successfully added to list", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dialog.cancel();
                                        Toast.makeText(OrderHistory.this, "There is an error adding it to the order list", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.cancel();
                                }
                                else {
                                    Toast.makeText(OrderHistory.this, "No such toy under the entered name was found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(OrderHistory.this, "Please fill in any of required fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private void loadList() {
        orderList = new ArrayList<OrderData>();
        orderList = db.getListContentOrder();
        orderListAdapter = new OrderListAdapter(this);
        cakeToyOrderList.setAdapter(orderListAdapter);
        orderListAdapter.notifyDataSetChanged();
    }

}
