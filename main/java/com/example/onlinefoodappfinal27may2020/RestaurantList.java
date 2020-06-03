package com.example.onlinefoodappfinal27may2020;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RestaurantList extends ArrayAdapter<Restaurant>
{
    private Activity context;
    List<Restaurant> res;

    public RestaurantList(Activity context, List<Restaurant> res)
    {
        super(context, R.layout.layout_restaurants_list, res);
        this.context = context;
        this.res = res;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_restaurants_list, null, true);


        TextView rid= (TextView) listViewItem.findViewById(R.id.txtVResTID);
        TextView t = (TextView) listViewItem.findViewById(R.id.txtViewRESTitle);
        TextView area= (TextView) listViewItem.findViewById(R.id.txtVResTArea);
        TextView city = (TextView) listViewItem.findViewById(R.id. txtViewRESTCity);




        TextView contno= (TextView) listViewItem.findViewById(R.id.txtVResContNo);
        TextView add = (TextView) listViewItem.findViewById(R.id.txtViewRESTAddress);
        TextView opent= (TextView) listViewItem.findViewById(R.id. txtVResTOpenTime);
        TextView clost = (TextView) listViewItem.findViewById(R.id.txtViewRESTClosingTime);

        Restaurant r=res.get(position);

        rid.setText(r.getRestaurantiD());
        t.setText(r.getTitle());

        area.setText(r.getArea());

        city.setText(r.getCity());
        contno.setText(r.getContactNo());
        add.setText(r.getAddress());
        opent.setText(r.getOpeningTime());
        clost.setText(r.getClosingTime());




        return listViewItem;
    }




}
