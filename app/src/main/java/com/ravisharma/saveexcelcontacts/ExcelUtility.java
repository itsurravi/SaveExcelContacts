package com.ravisharma.saveexcelcontacts;

/**
 * Created by Ravi Sharma on 06-Mar-18.
 */

import android.os.Environment;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelUtility {

    static ArrayList<ContactInfo> readExcelFile(File file) {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e("etc", "Storage not available or read only");
            return null;
        }
        try {
            Iterator rowIter = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(file))).getSheetAt(0).rowIterator();
            ArrayList<ContactInfo> contactInfoArrayList = new ArrayList();
            while (rowIter.hasNext()) {
                ContactInfo contactInfoObject = new ContactInfo();
                Iterator cellIter = ((HSSFRow) rowIter.next()).cellIterator();
                int temp = 0;
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    myCell.setCellType(1);
                    Log.d("ak333", "Cell Value: " + myCell.toString());
                    String sTemp = myCell.toString();
                    switch (temp) {
                        case R.styleable.View_android_theme /*0*/:
                            contactInfoObject.setId(sTemp);
                            break;
                        case R.styleable.View_android_focusable /*1*/:
                            contactInfoObject.setFullName(sTemp);
                            break;
                        case R.styleable.View_paddingEnd /*2*/:
                            contactInfoObject.setMobileHome(sTemp);
                            break;
                        case R.styleable.View_paddingStart /*3*/:
                            contactInfoObject.setMobileWork(sTemp);
                            break;
                        case R.styleable.View_theme /*4*/:
                            contactInfoObject.setMobileOffice1(sTemp);
                            break;
                        case R.styleable.Toolbar_contentInsetEnd /*5*/:
                            contactInfoObject.setMobileOffice2(sTemp);
                            break;
                        default:
                            break;
                    }
                    temp++;
                }
                contactInfoArrayList.add(contactInfoObject);
            }
            Log.d("ak33", "DONE");
            return contactInfoArrayList;
        } catch (Exception e) {
            Log.d("ak33", "DONE\n"+e.getMessage());
            return null;
        }
    }

    public static boolean isExternalStorageReadOnly() {
        if ("mounted_ro".equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }
}
