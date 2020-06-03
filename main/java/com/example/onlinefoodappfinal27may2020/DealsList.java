package com.example.onlinefoodappfinal27may2020;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DealsList extends ArrayAdapter<Deals>

{


    private Activity context;
    List<Deals> deals;

    public DealsList(Activity context, List<Deals> deals)
    {
        super(context, R.layout.layout_deals_list, deals);//layout_deals_list
        this.context = context;
        this.deals = deals;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_deals_list, null, true);


        TextView rid= (TextView) listViewItem.findViewById(R.id.txtDealsResTID);
        TextView cat = (TextView) listViewItem.findViewById(R.id.txtViewDealsCategory);
        TextView title = (TextView) listViewItem.findViewById(R.id.txtVDealsTitle);
        TextView price = (TextView) listViewItem.findViewById(R.id. txtViewDealsPrice);





        Deals a = deals.get(position);

        rid.setText(a.getRestaurantiD());
        cat.setText(a.getCategory());
        title.setText(a.getTitle());
        price.setText(a.getPrice());





        return listViewItem;
    }









}
