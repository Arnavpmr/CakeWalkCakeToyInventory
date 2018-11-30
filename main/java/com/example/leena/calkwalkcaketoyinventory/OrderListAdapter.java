package com.example.leena.calkwalkcaketoyinventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;

public class OrderListAdapter extends BaseAdapter implements Filterable{
    private Context c;
    OrderFilter orderFilter;
    ArrayList<OrderData> tempArray;
    @Override
    public int getCount() {
        return OrderHistory.orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return OrderHistory.orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflator.inflate(R.layout.orderrow, null);

        TextView dateText = (TextView)rowView.findViewById(R.id.DateText);
        TextView toyName = (TextView)rowView.findViewById(R.id.ToyNameOrderText);
        TextView toyID = (TextView)rowView.findViewById(R.id.ToyIDOrderText);

        dateText.setText(OrderHistory.orderList.get(position).getDate());
        toyName.setText(OrderHistory.orderList.get(position).getToyName());
        toyID.setText(OrderHistory.orderList.get(position).getToyID());

        return rowView;
    }
    public OrderListAdapter(Context c) {
        this.c = c;
        this.tempArray = OrderHistory.orderList;
    }

    @Override
    public Filter getFilter() {
        if (orderFilter == null) {
            orderFilter = new OrderFilter();
        }
        return orderFilter;
    }

class OrderFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString();
                ArrayList<OrderData> filterList = new ArrayList<>();
                for (int i = 0; i < tempArray.size(); i++) {
                    if (OrderHistory.searchState == "Location") {
                        if (tempArray.get(i).getLocation().contains(constraint)) {
                            OrderData orderData = new OrderData(tempArray.get(i).getDate(), tempArray.get(i).getToyName(), tempArray.get(i).getToyID(), tempArray.get(i).getDeliveryTime(), tempArray.get(i).getLocation(), tempArray.get(i).getCakeWeight(), tempArray.get(i).getCakeFlavor(), tempArray.get(i).getMsg(), tempArray.get(i).getSplRequest(), tempArray.get(i).getComment(), tempArray.get(i).getOrderID());
                            filterList.add(orderData);
                        }
                    }
                    else if (OrderHistory.searchState == "Date") {
                            if (tempArray.get(i).getDate().contains(constraint)) {
                                OrderData orderData = new OrderData(tempArray.get(i).getDate(), tempArray.get(i).getToyName(), tempArray.get(i).getToyID(), tempArray.get(i).getDeliveryTime(), tempArray.get(i).getLocation(), tempArray.get(i).getCakeWeight(), tempArray.get(i).getCakeFlavor(), tempArray.get(i).getMsg(), tempArray.get(i).getSplRequest(), tempArray.get(i).getComment(), tempArray.get(i).getOrderID());
                                filterList.add(orderData);
                            }
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            }
            else {
                results.count = tempArray.size();
                results.values = tempArray;
            }
            return results;

        }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        OrderHistory.orderList = (ArrayList<OrderData>)results.values;
        notifyDataSetChanged();
    }
}
}
