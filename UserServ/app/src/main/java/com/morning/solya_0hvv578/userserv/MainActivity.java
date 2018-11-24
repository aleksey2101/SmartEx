package com.morning.solya_0hvv578.userserv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.morning.solya_0hvv578.userserv.Classes.URLSendGet;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void get(View view) {
        EditText editText = (EditText) (findViewById(R.id.editText3));
        String url = editText.getText().toString();
        TextView textView = findViewById(R.id.textView5);
        URLSendGet urlSendGet = new URLSendGet(url, 80000);
        editText = findViewById(R.id.editText0);
        String line = urlSendGet.get("getUser/" + editText.getText().toString());
        textView.setText(line);
    }

    public void add(View view) {
        EditText editText = findViewById(R.id.editText4);
        String name = editText.getText().toString();
        editText = findViewById(R.id.editText5);
        String password = editText.getText().toString();
        editText = (EditText) (findViewById(R.id.editText3));
        String url = editText.getText().toString();
        URLSendGet urlSendGet = new URLSendGet(url, 80000);
        String line = urlSendGet.get("addUser/" + name + "/0/" + password);
        TextView textView = findViewById(R.id.textView02);
        textView.setText(line);
    }

    public void remove(View view) {
        EditText editText = findViewById(R.id.editText03);
        String id = editText.getText().toString();
        editText = (EditText) (findViewById(R.id.editText3));
        String url = editText.getText().toString();
        URLSendGet urlSendGet = new URLSendGet(url, 80000);
        String line = urlSendGet.get("removeUser/" + id);
        TextView textView = findViewById(R.id.textView05);
        textView.setText(line);
    }

    public void getList(View view) {
        EditText editText = (findViewById(R.id.editText3));
        String url = editText.getText().toString();
        URLSendGet urlSendGet = new URLSendGet(url, 80000);
        int n = Integer.parseInt(urlSendGet.get("getUserSize"));
        String line = "";
        for (int i = 0; i < n; i++) {
            line += urlSendGet.get("getUser/" + Integer.toString(i)) + "\n";
        }
        TextView textView = findViewById(R.id.textView9);
        textView.setText(line);
    }
}
