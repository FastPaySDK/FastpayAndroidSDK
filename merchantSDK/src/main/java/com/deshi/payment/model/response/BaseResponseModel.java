package com.deshi.payment.model.response;

import java.io.Serializable;
import java.util.ArrayList;

public class BaseResponseModel implements Serializable {

    private int code;
    private String message;
    private ArrayList<String> errors = new ArrayList<>();
    private Object data;

    public BaseResponseModel() {
    }

    public BaseResponseModel(int code, String message, ArrayList<String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public BaseResponseModel(int code, String message, ArrayList<String> errors, Object data) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public Object getData() {
        /*if(data == null){
            data = new Object();
        }*/
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
