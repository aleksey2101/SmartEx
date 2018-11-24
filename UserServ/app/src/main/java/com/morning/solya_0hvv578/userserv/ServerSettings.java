package com.morning.solya_0hvv578.userserv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by solya_0hvv578 on 22.11.2018.
 */

public class ServerSettings extends Activity {
    String URL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_settings);
        Intent intent = getIntent();
        URL = intent.getStringExtra("url");
    }

    public void goMain(View view) {
        //Intent intent = new Intent(ServerSettings.this, MainActivity.class);
        EditText editText = view.findViewById(R.id.editText);
        //URL = editText.getText().toString();
        //intent.putExtra("url", URL);
        //startActivity(intent);
        TextView textView = view.findViewById(R.id.textView3);
        textView.setText(URL);
    }
}
