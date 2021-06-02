package com.example.handlingformsubmission;

public class ResponseForm {
    private String  status;

    ResponseForm(){

    }
    ResponseForm(String st,Employee em){
        status=st;
        raw_data=em;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getRaw_data() {
        return raw_data;
    }

    public void setRaw_data(Employee raw_data) {
        this.raw_data = raw_data;
    }

    private Employee raw_data;
}
