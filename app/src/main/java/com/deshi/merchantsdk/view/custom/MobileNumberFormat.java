package com.deshi.merchantsdk.view.custom;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MobileNumberFormat implements TextWatcher {

    private EditText view;
    private String previousValue;

    public MobileNumberFormat(EditText view){
        this.view = view;
        previousValue = new String(" ");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString().replaceAll(" ","");
        if(previousValue.length() < text.length()) {
            String finalText = "";
            char textArray[] = text.toCharArray();
            for (int x = 0; x < textArray.length; x++){
                if(x == 3 || x == 6)
                    finalText = finalText+" "+textArray[x];
                else
                    finalText = finalText+""+textArray[x];
            }
            previousValue = finalText.replaceAll(" ","");
            view.setText(finalText);
        }
        view.setSelection(view.getText().toString().length());
        previousValue = view.getText().toString().replaceAll(" ","");
    }

    private String addSpace(String value, int position){
        String returnData = new String();
        char[] characters = value.toCharArray();
        for (int x=0;x<characters.length;x++) {
            if(position == x) {
                returnData = returnData + " ";
            }
            returnData = returnData + characters[x];
        }
        return returnData;
    }

    private String removeSpace(String value, int limitPosition){
        String returnData = new String();
        char[] characters = value.toCharArray();
        for (int x=0;x<limitPosition;x++) {
            returnData = returnData + characters[x];
        }
        return returnData;
    }

}
