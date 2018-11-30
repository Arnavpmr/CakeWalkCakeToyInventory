package com.example.leena.calkwalkcaketoyinventory;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import java.util.ArrayList;

public class ToyListAdapter extends BaseAdapter implements Filterable{
    private Context c;
    ToyFilter toyFilter;
    ArrayList<ToyData> tempArray;
    @Override
    public int getCount() {
        return CakeToyList.toyList.size();
    }

    @Override
    public Object getItem(int position) {
        return CakeToyList.toyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflator.inflate(R.layout.caketoyrow, null);

        TextView toyQuantity = (TextView)rowView.findViewById(R.id.ToyQuantity);
        TextView toyName = (TextView)rowView.findViewById(R.id.ToyName);
        TextView toyID = (TextView)rowView.findViewById(R.id.ToyID);
        TextView inStock = (TextView)rowView.findViewById(R.id.inStock);

        toyQuantity.setText(CakeToyList.toyList.get(position).getQuantity());
        toyName.setText(CakeToyList.toyList.get(position).getName());
        toyID.setText(CakeToyList.toyList.get(position).getID());

        if (Integer.parseInt(toyQuantity.getText().toString()) <= 0) {
            inStock.setText("OUT OF STOCK");
            inStock.setTextColor(Color.parseColor("#ff0000"));
        }
        else if (Integer.parseInt(toyQuantity.getText().toString()) > 3) {
            inStock.setText("IN STOCK");
            inStock.setTextColor(Color.parseColor("#53a006"));
        }
        else if (Integer.parseInt(toyQuantity.getText().toString()) > 0 && Integer.parseInt(toyQuantity.getText().toString()) <= 3) {
            inStock.setText("LOW STOCK");
            inStock.setTextColor(Color.parseColor("#ff0000"));
        }
        toyID.setTextColor(Color.parseColor("#0213d6"));
        toyName.setTextColor(Color.parseColor("#0213d6"));
        toyQuantity.setTextColor(Color.parseColor("#0213d6"));

        return rowView;
    }
    public ToyListAdapter(Context c, ArrayList<ToyData> originalArray) {
        this.c = c;
        this.tempArray = originalArray;
    }

    @Override
    public Filter getFilter() {
        if (toyFilter == null) {
            toyFilter = new ToyFilter();
        }
        return toyFilter;
    }

    class ToyFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() >= 0) {
                constraint = constraint.toString();
                tempArray = CakeToyList.db.getListContentToy();
                CakeToyList.toyList.clear();
                for (int i = 0; i < tempArray.size(); i++) {
                    if (tempArray.get(i).getName().contains(constraint)) {
                        ToyData toyData = new ToyData(tempArray.get(i).getID(), tempArray.get(i).getName(), tempArray.get(i).getQuantity());
                        CakeToyList.toyList.add(toyData);
                    }
                }
                results.count = CakeToyList.toyList.size();
                results.values = CakeToyList.toyList;
            }
            else {
                results.count = tempArray.size();
                results.values = tempArray;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            CakeToyList.toyList = (ArrayList<ToyData>)results.values;
            notifyDataSetChanged();
        }
    }
}
