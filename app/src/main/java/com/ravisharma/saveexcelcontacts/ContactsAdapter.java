package com.ravisharma.saveexcelcontacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ravi Sharma on 06-Mar-18.
 */

public class ContactsAdapter extends BaseAdapter {
    ArrayList<ContactInfo> contactInfoArrayList = new ArrayList();
    Context context;
    private final LayoutInflater layoutInflater;

    public ContactsAdapter(Context context, ArrayList<ContactInfo> contactInfoArrayList) {
        this.context = context;
        this.contactInfoArrayList = contactInfoArrayList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.contactInfoArrayList.size();
    }

    public Object getItem(int position) {
        return this.contactInfoArrayList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.single_row_contact, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.textViewName);
        TextView textViewMobileHome = (TextView) convertView.findViewById(R.id.textViewMobileHome);
        TextView textViewMobileWork = (TextView) convertView.findViewById(R.id.textViewMobileWork);
        TextView textViewMobileOffice1 = (TextView) convertView.findViewById(R.id.textViewMobileOffice1);
        TextView textViewMobileOffice2 = (TextView) convertView.findViewById(R.id.textViewMobileOffice2);
        ContactInfo contactInfo = (ContactInfo) this.contactInfoArrayList.get(position);
        ((TextView) convertView.findViewById(R.id.textViewSNo)).setText(String.valueOf(position + 1));
        name.setText(contactInfo.getFullName());
        if (contactInfo.getMobileHome() != null) {
            textViewMobileHome.setText(contactInfo.getMobileHome());
        }
        if (contactInfo.getMobileWork() != null) {
            textViewMobileWork.setText(contactInfo.getMobileWork());
        }
        if (contactInfo.getMobileOffice1() != null) {
            textViewMobileOffice1.setText(contactInfo.getMobileOffice1());
        }
        if (contactInfo.getMobileOffice2() != null) {
            textViewMobileOffice2.setText(contactInfo.getMobileOffice2());
        }
        return convertView;
    }
}
