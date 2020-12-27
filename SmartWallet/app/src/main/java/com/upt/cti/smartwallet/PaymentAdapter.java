package com.upt.cti.smartwallet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

public class PaymentAdapter extends ArrayAdapter<Payment> {
    private Context context;
    private List<Payment> payments;
    private int layoutResID;

    public PaymentAdapter(Context context, int layoutResID, List<Payment> payments){
        super(context, layoutResID, payments);
        this.context = context;
        this.layoutResID = layoutResID;
        this.payments = payments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ItemHolder itemHolder;
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            itemHolder = new ItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            itemHolder.tIndex = (TextView) view.findViewById(R.id.tIndex);
            itemHolder.tName = view.findViewById(R.id.tName);
            itemHolder.lHeader = view.findViewById(R.id.lHeader);
            itemHolder.tDate = view.findViewById(R.id.tDate);
            itemHolder.tTime = view.findViewById(R.id.tTime);
            itemHolder.tCost = view.findViewById(R.id.tCost);
            itemHolder.tType = view.findViewById(R.id.tType);

            view.setTag(itemHolder);
        }else{
            itemHolder = (ItemHolder)view.getTag();
        }

        final Payment pItem = payments.get(position);
        itemHolder.tIndex.setText(String.valueOf(position + 1));
        itemHolder.tName.setText(pItem.getName());
        itemHolder.lHeader.setBackgroundColor(PaymentType.getColorFromPaymentType(pItem.getType()));
        itemHolder.tCost.setText(pItem.getCost() + "RON");
        itemHolder.tType.setText(pItem.getType());
        itemHolder.tDate.setText("DATE: " + pItem.timestamp.substring(0, 10));
        itemHolder.tDate.setText("TIME: " + pItem.timestamp.substring(11));

        return view;

    }

    private static class ItemHolder{
        TextView tIndex;
        TextView tName;
        RelativeLayout lHeader;
        TextView tDate, tTime;
        TextView tCost, tType;
    }


}

