package com.example.courseworkappproper;

public class DataModel {
    String fname, lname, mname, dob, gender, joindate;

    public DataModel(String Fname, String Lname, String Mname, String Dob, String Gender, String Joindate) {
        this.fname = Fname;
        this.lname = Lname;
        this.mname = Mname;
        this.dob = Dob;
        this.gender = Gender;
        this.joindate = Joindate;
    }

    public String getFName() {
        return (fname);
    }

    public void setFName(String Fname) {
        this.fname = Fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getDOB() {
        return dob;
    }

    public void setDob(String Dob) {
        this.dob = Dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJoin() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }


    @Override
    public String toString() {
        return "DataModel{" +
                "name='" + fname + " " + lname + '\'' +
                ", gender='" + gender + '\'' +
                ", DOB=" + dob +
                '}';
    }
}
