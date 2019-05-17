package com.ravisharma.saveexcelcontacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ravi Sharma on 06-Mar-18.
 */

public class FileArrayAdapter extends ArrayAdapter<Option> {
    private Context c;
    private int id;
    private List<Option> items;

    public FileArrayAdapter(Context context, int textViewResourceId, List<Option> objects) {
        super(context, textViewResourceId, objects);
        this.c = context;
        this.id = textViewResourceId;
        this.items = objects;
    }

    public Option getItem(int i) {
        return (Option) this.items.get(i);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = ((LayoutInflater) this.c.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.id, null);
        }
        Option o = (Option) this.items.get(position);
        if (o != null) {
            ImageView im = (ImageView) v.findViewById(R.id.img1);
            TextView t1 = (TextView) v.findViewById(R.id.TextView01);
            TextView t2 = (TextView) v.findViewById(R.id.TextView02);
            if (o.getData().equalsIgnoreCase("folder")) {
                im.setImageResource(R.drawable.folder);
            } else if (o.getData().equalsIgnoreCase("parent directory")) {
                im.setImageResource(R.drawable.back32);
            } else {
                String name = o.getName().toLowerCase();
                if (name.endsWith(".xls") || name.endsWith(".xlsx")) {
                    im.setImageResource(R.drawable.xls);
                }
            }
            if (t1 != null) {
                t1.setText(o.getName());
            }
            if (t2 != null) {
                t2.setText(o.getData());
            }
        }
        return v;
    }
}

