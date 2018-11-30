package com.example.leena.calkwalkcaketoyinventory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CakeToyList extends Activity {
    ListView cakeToyList;
    Button addToyBtn;
    Button menuBtn;
    static DBHelper db;
    EditText searchText;
    static ArrayList<ToyData> toyList;
    ToyListAdapter toyListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caketoylist);
        createComponents();
        searchText = (EditText)findViewById(R.id.SearchToyText);
        toyList = new ArrayList<ToyData>();
        toyList = db.getListContentToy();
        toyListAdapter = new ToyListAdapter(this, toyList);
        cakeToyList.setAdapter(toyListAdapter);
        cakeToyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogue = new AlertDialog.Builder(CakeToyList.this);
                View mView = getLayoutInflater().inflate(R.layout.alert_toy_change_row, null);

                final EditText toyNameInput = (EditText)mView.findViewById(R.id.toyName_input);
                final EditText toyQuantityInput = (EditText)mView.findViewById(R.id.toyQuantity_input);
                final EditText toyID = (EditText)mView.findViewById(R.id.toyID);

                toyNameInput.setText(toyList.get(position).getName());
                toyQuantityInput.setText(toyList.get(position).getQuantity());
                toyID.setText(toyList.get(position).getID());

                Button updateBtn = (Button)mView.findViewById(R.id.update_btn);
                Button cancelBtn = (Button)mView.findViewById(R.id.orderBackBtnDisp);
                Button deleteBtn = (Button)mView.findViewById(R.id.deleteBtn);

                alertDialogue.setView(mView);
                final AlertDialog dialog = alertDialogue.create();
                dialog.show();

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (toyNameInput.getText().length() > 0 && toyQuantityInput.getText().length() > 0 && toyID.getText().length() > 0) {
                            Cursor cursor = db.getListContentsCursor();
                            while(cursor.moveToNext()) {
                                int toyiD = Integer.parseInt(toyList.get(position).getID().toString());
                                int data = Integer.parseInt(cursor.getString(1));
                                if (toyiD == data) {
                                    boolean success = db.updateToyData(cursor.getString(0), Integer.parseInt(toyID.getText().toString()), toyNameInput.getText().toString(), Integer.parseInt(toyQuantityInput.getText().toString()));
                                    if (success) {
                                        toyList.get(position).setID(toyID.getText().toString());
                                        toyList.get(position).setName(toyNameInput.getText().toString());
                                        toyList.get(position).setQuantity(toyQuantityInput.getText().toString());
                                        Toast.makeText(CakeToyList.this,"Successfully updated the data", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                        break;
                                    }
                                }
                            }
                            reloadList();
                            dialog.cancel();
                        }
                        else {
                            Toast.makeText(CakeToyList.this,"Please fill in any of the empty fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor cursor = db.getListContentsCursor();
                        while(cursor.moveToNext()) {
                            int toyID = Integer.parseInt(toyList.get(position).getID().toString());
                            int data = Integer.parseInt(cursor.getString(1));
                            if (toyID == data) {
                                db.deleteToy(cursor.getString(0));
                                toyList.remove(position);
                                break;
                            }
                        }
                        reloadList();
                        dialog.cancel();
                        Toast.makeText(CakeToyList.this,"Successfully deleted the toy", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        addToyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogue = new AlertDialog.Builder(CakeToyList.this);
                View mView = getLayoutInflater().inflate(R.layout.alert_toy_add, null);
                final EditText toyNameInput = (EditText)mView.findViewById(R.id.toyName_input);
                final EditText toyQuantityInput = (EditText)mView.findViewById(R.id.toyQuantity_input);
                final EditText toyID = (EditText)mView.findViewById(R.id.toyID);
                Button addBtn = (Button)mView.findViewById(R.id.add_btn);
                Button cancelBtn = (Button)mView.findViewById(R.id.orderBackBtnDisp);
                alertDialogue.setView(mView);
                final AlertDialog dialog = alertDialogue.create();
                dialog.show();
                toyNameInput.getText().clear();
                toyQuantityInput.getText().clear();
                toyID.getText().clear();
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (toyNameInput.getText().length() > 0 && toyQuantityInput.getText().length() > 0 && toyID.getText().length() > 0) {
                           boolean success = db.insertDataToyTable(Integer.parseInt(toyID.getText().toString()), toyNameInput.getText().toString(), Integer.parseInt(toyQuantityInput.getText().toString()));
                           if (success) {
                               loadList();
                               Toast.makeText(CakeToyList.this,"Successfully added to list", Toast.LENGTH_SHORT).show();
                               dialog.cancel();
                           }
                           else {
                               dialog.cancel();
                               Toast.makeText(CakeToyList.this,"Process failed", Toast.LENGTH_SHORT).show();
                           }
                        }
                        else {
                            Toast.makeText(CakeToyList.this,"Please fill in any empty fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });


        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CakeToyList.this, MainActivity.class);
                startActivity(i);
            }
        });
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                toyListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void createComponents() {
        cakeToyList = (ListView)findViewById(R.id.CakeToyList);
        addToyBtn = (Button)findViewById(R.id.AddToyBtn);
        menuBtn = (Button)findViewById(R.id.MenuBtn);
        searchText = (EditText)findViewById(R.id.SearchToyText);
        db = new DBHelper(this);
    }

    private void loadList() {
        toyList = new ArrayList<ToyData>();
        toyList = db.getListContentToy();
        toyListAdapter = new ToyListAdapter(this, toyList);
        cakeToyList.setAdapter(toyListAdapter);
        toyListAdapter.notifyDataSetChanged();
    }
    private void reloadList() {
        toyListAdapter = new ToyListAdapter(this, toyList);
        cakeToyList.setAdapter(toyListAdapter);
        toyListAdapter.notifyDataSetChanged();
    }
}
