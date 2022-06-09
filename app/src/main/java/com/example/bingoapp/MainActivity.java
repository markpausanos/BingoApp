package com.example.bingoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextInputEditText codeInput;
    TextView tester, tokenPut;
    ToggleButton b1,b2,b3,b4,b5;
    ToggleButton i1,i2,i3,i4,i5;
    ToggleButton n1,n2,n3,n4,n5;
    ToggleButton g1,g2,g3,g4,g5;
    ToggleButton o1,o2,o3,o4,o5;
    String prevCode = "";
    String inputCode, token;
    Boolean entered = false;
    ArrayList<ToggleButton> checker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codeInput = findViewById(R.id.gameCode);

        b1 = findViewById(R.id.buttonb1);
        b2 = findViewById(R.id.buttonb2);
        b3 = findViewById(R.id.buttonb3);
        b4 = findViewById(R.id.buttonb4);
        b5 = findViewById(R.id.buttonb5);

        i1 = findViewById(R.id.buttoni1);
        i2 = findViewById(R.id.buttoni2);
        i3 = findViewById(R.id.buttoni3);
        i4 = findViewById(R.id.buttoni4);
        i5 = findViewById(R.id.buttoni5);

        n1 = findViewById(R.id.buttonn1);
        n2 = findViewById(R.id.buttonn2);
        n3 = findViewById(R.id.buttonn3);
        n4 = findViewById(R.id.buttonn4);
        n5 = findViewById(R.id.buttonn5);

        g1 = findViewById(R.id.buttong1);
        g2 = findViewById(R.id.buttong2);
        g3 = findViewById(R.id.buttong3);
        g4 = findViewById(R.id.buttong4);
        g5 = findViewById(R.id.buttong5);

        o1 = findViewById(R.id.buttono1);
        o2 = findViewById(R.id.buttono2);
        o3 = findViewById(R.id.buttono3);
        o4 = findViewById(R.id.buttono4);
        o5 = findViewById(R.id.buttono5);

        tester = findViewById(R.id.state);
        tokenPut = findViewById(R.id.tokenPut);

        ToggleButton[] buttons = {b1,b2,b3,b4,b5,i1,i2,i3,i4,i5,n1,n2,n3,n4,n5,g1,g2,g3,g4,g5,o1,o2,o3,o4,o5};
        checker = new ArrayList<>();
        Collections.addAll(checker, buttons);

        for (ToggleButton x: checker) {
            x.setEnabled(false);
        }
        reset();
    }

    public void connectRoom(){
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://www.hyeumine.com/getcard.php?bcode=" + inputCode;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("0")){
                            tester.setText("GAME NOT FOUND!");
                            tokenPut.setText("");
                            return;
                        }
                        try {
                            parseString(response);
                            tokenPut.setText(token);
                            for (ToggleButton x: checker) {
                                x.setEnabled(true);
                            }
                            tester.setText("");
                            entered = true;
                            prevCode = inputCode;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tester.setText(error.toString());
            }
        });

        queue.add(stringRequest);
    }
    public void checkCard(){
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://www.hyeumine.com/checkwin.php?playcard_token=" + token;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        if(response.equals("0")){
                            tester.setText("GAME STILL NOT WON!");
                        }else{
                            Intent intent = new Intent(MainActivity.this, WinActivity.class);
                            intent.putExtra("token", token);
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tester.setText(error.toString());
            }
        });

        queue.add(stringRequest);
    }
    public void parseString(String stringjson) throws JSONException {
        JSONObject json = new JSONObject(stringjson);
        token = json.getString("playcard_token");
        JSONObject json2 = json.getJSONObject("card");
        setButtonsB(json2.getJSONArray("B"));
        setButtonsI(json2.getJSONArray("I"));
        setButtonsN(json2.getJSONArray("N"));
        setButtonsG(json2.getJSONArray("G"));
        setButtonsO(json2.getJSONArray("O"));
    }
    public void setButtonsB(JSONArray jsonArray) throws JSONException {
        b1.setText(jsonArray.getInt(0) + "");
        b1.setTextOn(jsonArray.getInt(0) + "");
        b1.setTextOff(jsonArray.getInt(0) + "");

        b2.setText(jsonArray.getInt(1) + "");
        b2.setTextOn(jsonArray.getInt(1) + "");
        b2.setTextOff(jsonArray.getInt(1) + "");

        b3.setText(jsonArray.getInt(2) + "");
        b3.setTextOn(jsonArray.getInt(2) + "");
        b3.setTextOff(jsonArray.getInt(2) + "");

        b4.setText(jsonArray.getInt(3) + "");
        b4.setTextOn(jsonArray.getInt(3) + "");
        b4.setTextOff(jsonArray.getInt(3) + "");

        b5.setText(jsonArray.getInt(4) + "");
        b5.setTextOn(jsonArray.getInt(4) + "");
        b5.setTextOff(jsonArray.getInt(4) + "");

    }
    public void setButtonsI(JSONArray jsonArray) throws JSONException {
        i1.setText(jsonArray.getInt(0) + "");
        i1.setTextOn(jsonArray.getInt(0) + "");
        i1.setTextOff(jsonArray.getInt(0) + "");

        i2.setText(jsonArray.getInt(1) + "");
        i2.setTextOn(jsonArray.getInt(1) + "");
        i2.setTextOff(jsonArray.getInt(1) + "");

        i3.setText(jsonArray.getInt(2) + "");
        i3.setTextOn(jsonArray.getInt(2) + "");
        i3.setTextOff(jsonArray.getInt(2) + "");

        i4.setText(jsonArray.getInt(3) + "");
        i4.setTextOn(jsonArray.getInt(3) + "");
        i4.setTextOff(jsonArray.getInt(3) + "");

        i5.setText(jsonArray.getInt(4) + "");
        i5.setTextOn(jsonArray.getInt(4) + "");
        i5.setTextOff(jsonArray.getInt(4) + "");
    }
    public void setButtonsN(JSONArray jsonArray) throws JSONException {
        n1.setText(jsonArray.getInt(0) + "");
        n1.setTextOn(jsonArray.getInt(0) + "");
        n1.setTextOff(jsonArray.getInt(0) + "");

        n2.setText(jsonArray.getInt(1) + "");
        n2.setTextOn(jsonArray.getInt(1) + "");
        n2.setTextOff(jsonArray.getInt(1) + "");

        n3.setText(jsonArray.getInt(2) + "");
        n3.setTextOn(jsonArray.getInt(2) + "");
        n3.setTextOff(jsonArray.getInt(2) + "");

        n4.setText(jsonArray.getInt(3) + "");
        n4.setTextOn(jsonArray.getInt(3) + "");
        n4.setTextOff(jsonArray.getInt(3) + "");

        n5.setText(jsonArray.getInt(4) + "");
        n5.setTextOn(jsonArray.getInt(4) + "");
        n5.setTextOff(jsonArray.getInt(4) + "");
    }
    public void setButtonsG(JSONArray jsonArray) throws JSONException {
        g1.setText(jsonArray.getInt(0) + "");
        g1.setTextOn(jsonArray.getInt(0) + "");
        g1.setTextOff(jsonArray.getInt(0) + "");

        g2.setText(jsonArray.getInt(1) + "");
        g2.setTextOn(jsonArray.getInt(1) + "");
        g2.setTextOff(jsonArray.getInt(1) + "");

        g3.setText(jsonArray.getInt(2) + "");
        g3.setTextOn(jsonArray.getInt(2) + "");
        g3.setTextOff(jsonArray.getInt(2) + "");

        g4.setText(jsonArray.getInt(3) + "");
        g4.setTextOn(jsonArray.getInt(3) + "");
        g4.setTextOff(jsonArray.getInt(3) + "");

        g5.setText(jsonArray.getInt(4) + "");
        g5.setTextOn(jsonArray.getInt(4) + "");
        g5.setTextOff(jsonArray.getInt(4) + "");
    }
    public void setButtonsO(JSONArray jsonArray) throws JSONException {
        o1.setText(jsonArray.getInt(0) + "");
        o1.setTextOn(jsonArray.getInt(0) + "");
        o1.setTextOff(jsonArray.getInt(0) + "");

        o2.setText(jsonArray.getInt(1) + "");
        o2.setTextOn(jsonArray.getInt(1) + "");
        o2.setTextOff(jsonArray.getInt(1) + "");

        o3.setText(jsonArray.getInt(2) + "");
        o3.setTextOn(jsonArray.getInt(2) + "");
        o3.setTextOff(jsonArray.getInt(2) + "");

        o4.setText(jsonArray.getInt(3) + "");
        o4.setTextOn(jsonArray.getInt(3) + "");
        o4.setTextOff(jsonArray.getInt(3) + "");

        o5.setText(jsonArray.getInt(4) + "");
        o5.setTextOn(jsonArray.getInt(4) + "");
        o5.setTextOff(jsonArray.getInt(4) + "");
    }

    public void connectRoom(View view) {
        if(Objects.requireNonNull(codeInput.getText()).toString().equals("")){
            tester.setText("ENTER GAME ROOM CODE FIRST!");
            return;
        }
        if(codeInput.getText().toString().equals(prevCode)){
            tester.setText("YOU ARE ALREADY IN THE ROOM!");
            return;
        }
        inputCode = codeInput.getText().toString();
        reset();
        connectRoom();
    }
    public void Checker(View view){
        if(!entered){
            return;
        }
        checkCard();
    }

    public void reset(){
        for (ToggleButton x: checker) {
            x.setChecked(false);
        }
    }

}