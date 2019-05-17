package com.ravisharma.saveexcelcontacts;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class ViewContactsActivity extends AppCompatActivity {

    private Button buttonImportAll;
    private ArrayList<ContactInfo> contactInfoArrayList;
    private ContactsAdapter contactsAdapter;
    private ContentResolver contentResolver;
    private Context context;
    private File file;
    private ListView listViewContacts;
    int numMessages = 1;

    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);

        context = ViewContactsActivity.this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fileStr = extras.getString("fileSelected");
            if (fileStr != null) {
                file = new File(fileStr);
            }
        }

        ed = (EditText)findViewById(R.id.name);
        listViewContacts = (ListView) findViewById(R.id.listViewContacts);
        contactInfoArrayList = ExcelUtility.readExcelFile(file);

        if (contactInfoArrayList == null) {
            Log.d("ak33", "check3");
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("No Contacts to save");
            alertDialog.setMessage("Please choose xls file with contacts");
            alertDialog.setIcon(R.mipmap.ic_launcher);
            alertDialog.show();
        } else if (contactInfoArrayList.size() > 0) {
            contactInfoArrayList.remove(0);
            contactsAdapter = new ContactsAdapter(getApplicationContext(), contactInfoArrayList);
            listViewContacts.setAdapter(contactsAdapter);
        }
        contentResolver = getContentResolver();
        buttonImportAll = (Button) findViewById(R.id.buttonImportAll);
        buttonImportAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = ed.getText().toString();
                if(name.isEmpty())
                {
                    new MyTask().execute(new String[]{""});
                }
                else
                {
                    new MyTask().execute(new String[]{name});
                }
            }
        });
    }

    public void showAlertBox(String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage(result + " Contacts Imported Successfully.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.show();
    }

    class MyTask extends AsyncTask<String, String, String> {
        ProgressDialog PD = new ProgressDialog(ViewContactsActivity.this);

        MyTask() {
        }

        protected String doInBackground(String... params) {
            int i = 1;
            if (contactInfoArrayList != null) {
                Iterator it = contactInfoArrayList.iterator();
                while (it.hasNext()) {
                    ContactInfo contactInfo = (ContactInfo) it.next();
                    if (contactInfo != null) {
                        ContactHelper.insertContact(contentResolver, contactInfo, params[0]);
                        i++;
                    }
                }
            }
            return String.valueOf(i - 1);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.PD.setTitle("Please Wait..");
            this.PD.setMessage("Importing Contacts...\nIt will take few minutes.\nAfter Completion you will get notification.");
            this.PD.setCancelable(false);
            this.PD.show();
        }

        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            ViewContactsActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    if (MyTask.this.PD != null && MyTask.this.PD.isShowing()) {
                        MyTask.this.PD.dismiss();
                    }
                    if (result != null) {
                        String[] events = new String[6];
                        events[0] = "Contacts Imported Successfully..";
                        events[1] = "Total Contacts Imported : " + result;
                        events[2] = "Click to open app";
                        events[3] = "Successfually Done.";
                        //ViewContactsActivity.this.displayNotification(events, result);
                        ViewContactsActivity.this.showAlertBox(result);
                        return;
                    }
                    Toast.makeText(ViewContactsActivity.this.context, "Contact not imported, Try Again", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
