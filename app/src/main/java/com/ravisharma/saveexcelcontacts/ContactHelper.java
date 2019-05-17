package com.ravisharma.saveexcelcontacts;

/**
 * Created by Ravi Sharma on 06-Mar-18.
 */

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

import java.util.ArrayList;

public class ContactHelper {

    public static ArrayList<ContactInfo> fetchContacts(Context context) {
        ArrayList<ContactInfo> contactList = new ArrayList();

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;

        String _ID = "_id";
        String DISPLAY_NAME = "display_name";
        String HAS_PHONE_NUMBER = "has_phone_number";
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = "contact_id";
        String NUMBER = "data1";

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        int sno = 1;

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ContactInfo tempContact = new ContactInfo();
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                if (name != null) {
                    int sno2 = sno + 1;
                    tempContact.setId(String.valueOf(sno));
                    tempContact.setFullName(name);
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                    int count = 1;
                    while (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        if (count == 1) {
                            tempContact.setMobileHome(phoneNumber);
                            count++;
                        } else if (count == 2) {
                            tempContact.setMobileWork(phoneNumber);
                            count++;
                        } else if (count == 3) {
                            tempContact.setMobileOffice1(phoneNumber);
                            count++;
                        } else if (count == 4) {
                            tempContact.setMobileOffice2(phoneNumber);
                            count++;
                        }
                    }
                    phoneCursor.close();
                    contactList.add(tempContact);
                    sno = sno2;
                }
            }
        }
        return contactList;
    }

    public static boolean insertContact (ContentResolver contactAdder, ContactInfo contactInfo, String name) {
        String fullName = contactInfo.getFullName();
        String mobileHome = contactInfo.getMobileHome();
        String mobileWork = contactInfo.getMobileWork();
        String mobileoffice1 = contactInfo.getMobileOffice1();
        String mobileoffice2 = contactInfo.getMobileOffice2();

        ArrayList<ContentProviderOperation> ops = new ArrayList();

        ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
                .withValue("account_type", null)
                .withValue("account_name", null)
                .build());

        if (fullName != null) {
            ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
                    .withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/name")
                    .withValue("data2", fullName+"("+name+")")
                    .build());
        }
        if (mobileHome != null) {
            ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
                    .withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                    .withValue("data1", mobileHome)
                    .withValue("data2", Integer.valueOf(2))
                    .build());
        }

        if (mobileWork != null) {
            ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
                    .withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                    .withValue("data1", mobileWork)
                    .withValue("data2", Integer.valueOf(3))
                    .build());
        }
        if (mobileoffice1 != null) {
            ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
                    .withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                    .withValue("data1", mobileoffice1)
                    .withValue("data2", Integer.valueOf(3))
                    .build());
        }
        if (mobileoffice2 != null) {
            ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
                    .withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                    .withValue("data1", mobileoffice2)
                    .withValue("data2", Integer.valueOf(3))
                    .build());
        }

        try {
            contactAdder.applyBatch("com.android.contacts", ops);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
