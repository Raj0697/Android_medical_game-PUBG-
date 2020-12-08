package com.example.uploading_from_firebase.helperclasses;

public class Userdata {
    String fullName,email,password,confirm_password,phone_no;

    public Userdata(){
    }

    public Userdata(String fullName, String email, String password, String confirm_password, String phone_no) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        this.phone_no = phone_no;
    }

    public String getfullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = this.fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
