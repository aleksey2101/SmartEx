package com.univerhelper.smartex.univerhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.univerhelper.smartex.univerhelper.Classes.URLSendGet;
import com.univerhelper.smartex.univerhelper.Classes.User;

public class LoginActivity extends AppCompatActivity {

    public final String URL = "http://10.201.2.208:8080/";

    private boolean newUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void chekBox (View view) {
        newUser = !newUser;
    }

    public void start (View view) {
        URLSendGet urlSendGet = new URLSendGet(URL, 8000);
        EditText editText = findViewById(R.id.editText3);
        String name = editText.getText().toString();
        editText = findViewById(R.id.editText4);
        String pass = editText.getText().toString();
        TextView textView = findViewById(R.id.textView);
        if (name != null && pass != null) {
            if (newUser) {
                if (Integer.parseInt(urlSendGet.get("getUserId/" + name)) > -1) {
                    textView.setText("Имя занято");
                } else {
                    textView.setText("Пользователь добавлен");
                    urlSendGet.get("addUser/" + name + "/0/" + pass);
                }
            } else {
                String id = urlSendGet.get("getUserId/" + name);
                Gson gson = new Gson();
                User user = gson.fromJson(urlSendGet.get("getUser/" + id), User.class);
                if (pass.equals(user.getPassword())) {
                    textView.setText("Все верно");
                } else {
                    textView.setText("Неверный пароль");
                }
            }
        }
    }
}
