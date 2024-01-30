package com.example.mobilprogramlamafinal.DataStructures;

public class User_DataStructure {
    String FirstName;
    String LastName;
    String E_Mail;
    public User_DataStructure() {
    }

    public User_DataStructure(String firstName, String lastName, String e_Mail) {
        FirstName = firstName;
        LastName = lastName;
        E_Mail = e_Mail;
    }
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getE_Mail() {
        return E_Mail;
    }

    public void setE_Mail(String e_Mail) {
        E_Mail = e_Mail;
    }
}
