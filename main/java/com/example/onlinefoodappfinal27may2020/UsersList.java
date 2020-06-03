package com.example.onlinefoodappfinal27may2020;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class UsersList extends ArrayAdapter<Users>
{


    private Activity context;
    List<Users> users;

    public UsersList(Activity context, List<Users> users) {
        super(context, R.layout.layout_users_list, users);
        this.context = context;
        this.users = users;


    }








    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_users_list, null, true);

        TextView name= (TextView) listViewItem.findViewById(R.id.txtUName);
        TextView fname = (TextView) listViewItem.findViewById(R.id.txtUFName);
        TextView userid = (TextView) listViewItem.findViewById(R.id.txtUUserID);
        TextView area = (TextView) listViewItem.findViewById(R.id. txtArea);




        TextView city = (TextView) listViewItem.findViewById(R.id.txtCity);
        TextView add = (TextView) listViewItem.findViewById(R.id.txtAddress);
        TextView email= (TextView) listViewItem.findViewById(R.id. txtEmail);
        TextView mob = (TextView) listViewItem.findViewById(R.id.txtUMobile);
        //name,fname,userid,area,city,address,email,mobile
        Users a = users.get(position);
        name.setText(a.getName());
        fname.setText(a.getfName());
        userid.setText(a.getUserid());
        area.setText(a.getArea());

        city.setText(a.getCity());
        add.setText(a.getAddress());

        email.setText(a.getEmail());
        mob.setText(a.getMobile());




        return listViewItem;
    }








}
