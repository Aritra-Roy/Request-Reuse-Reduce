package webtek.cse.uem.biswajit.com.requestresuereduce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Biswajit Paul on 04-05-2017.  

 */
 
 // adapter for the listview for My order and home feed. 
public class CustomAdapter extends BaseAdapter {

    ArrayList<DetailsFeeds> list = new ArrayList<>();
    Context context;
    String name;


    public CustomAdapter(ArrayList<DetailsFeeds> list, Context context, String name) {

        this.context = context;
        this.list = list;
        this.name = name;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final MyViewHolder holder;
        final int pos = i;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.layout_row_list, viewGroup, false);
            holder = new MyViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (MyViewHolder) view.getTag();
        }
        holder.category.setText("Category : " + list.get(i).getCategory());
        holder.description.setText("Description : " + list.get(i).getItemDonateName());
        holder.quantity.setText("Quantity : " + list.get(i).getQuantity());
        if(name.equals("Donate")) {
            holder.button.setText("Donate");
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, donation.class);
                    Bundle b = new Bundle();
                    b.putString("mob", list.get(i).getPhoneNo());
                    b.putString("post", String.valueOf(list.get(i).getPostNumber()));
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });
        }
        else if(name.equals("Myorder"))
        {
            holder.button.setText("Delete");
        }
        return view;
    }

    public class MyViewHolder {

        TextView category, description, quantity;
        Button button;

        MyViewHolder(View view) {
            category = (TextView) view.findViewById(R.id.post_row_category);
            description = (TextView) view.findViewById(R.id.post_row_description);
            quantity = (TextView) view.findViewById(R.id.post_row_quantity);
            button = (Button) view.findViewById(R.id.post_row_donate);
        }

    }
}