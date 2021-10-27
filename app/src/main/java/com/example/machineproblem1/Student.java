package com.example.machineproblem1;

public class Student {

    private int studentID;
    private String LastName;
    private String FirstName;
    private String Address;
    private String Gender;
    private String Contact;
    private String Birthday;
    //Constructor
    public Student(int studentID, String lastName, String firstName, String address, String gender, String birthday, String contact) {
        this.studentID = studentID;
        LastName = lastName;
        FirstName = firstName;
        Address = address;
        Gender = gender;
        Birthday = birthday;
        Contact = contact;


    }

    //SETTERS and GETTERS
    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }


    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }


    @Override
    public String toString() {
        return studentID + " " + LastName + " " + FirstName + " " + Birthday + " " + Gender + " " + Address + " " + Contact ;
    }
}
