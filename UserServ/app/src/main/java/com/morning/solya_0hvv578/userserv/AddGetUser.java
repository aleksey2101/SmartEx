package com.morning.solya_0hvv578.userserv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.morning.solya_0hvv578.userserv.Classes.URLSendGet;

public class AddGetUser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_get_user);
        //Intent intent = getIntent();
        //URL = intent.getStringExtra("url");
    }

   public void getUser(View view) {
        EditText editText = view.findViewById(R.id.editText);
        String url = editText.getText().toString();
   }
}
