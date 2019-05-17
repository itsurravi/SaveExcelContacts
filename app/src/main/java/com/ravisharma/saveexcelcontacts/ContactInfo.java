package com.ravisharma.saveexcelcontacts;

/**
 * Created by Ravi Sharma on 06-Mar-18.
 */

public class ContactInfo {

    String fullName;
    String id;
    String mobileHome;
    String mobileOffice1;
    String mobileOffice2;
    String mobileWork;

    public String getMobileOffice1() {
        return this.mobileOffice1;
    }

    public void setMobileOffice1(String mobileOffice1) {
        this.mobileOffice1 = mobileOffice1;
    }

    public String getMobileOffice2() {
        return this.mobileOffice2;
    }

    public void setMobileOffice2(String mobileOffice2) {
        this.mobileOffice2 = mobileOffice2;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileHome() {
        return this.mobileHome;
    }

    public void setMobileHome(String mobileHome) {
        this.mobileHome = mobileHome;
    }

    public String getMobileWork() {
        return this.mobileWork;
    }

    public void setMobileWork(String mobileWork) {
        this.mobileWork = mobileWork;
    }

    public String toString() {
        return this.fullName + " " + this.mobileHome;
    }
}
