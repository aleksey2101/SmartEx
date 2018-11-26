package com.univerhelper.smartex.univerhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.univerhelper.smartex.univerhelper.Classes.URLSendGet;

import java.util.UUID;

import ru.yandex.speechkit.Language;
import ru.yandex.speechkit.OnlineModel;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.gui.RecognizerActivity;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY_FOR_TESTS_ONLY = "88bb7adf-932f-4722-8c43-44eae7e6eb81";

    private static final int REQUEST_CODE = 31;

    private EditText resultView;

    private int TIME_OUT=8000;
    private String SERVER_URL="http://10.201.2.208:8080/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            SpeechKit.getInstance().init(this, API_KEY_FOR_TESTS_ONLY);
            SpeechKit.getInstance().setUuid(UUID.randomUUID().toString());
        } catch (SpeechKit.LibraryInitializationException ignored) {
            //do not ignore in the prod version!
            //finish()
        }
        Button startRecognitionBtn = findViewById(R.id.start_recognition_btn);
        startRecognitionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the current state.
                updateTextResult("");
                // To start recognition create an Intent with required extras.
                Intent intent = new Intent(MainActivity.this, RecognizerActivity.class);
                // Specify the model for better results.
                intent.putExtra(RecognizerActivity.EXTRA_MODEL, OnlineModel.QUERIES.getName());
                // Specify the language.
                intent.putExtra(RecognizerActivity.EXTRA_LANGUAGE, Language.RUSSIAN.getValue());
                // To get recognition results use startActivityForResult(),
                // also don't forget to override onActivityResult().
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        resultView = findViewById(R.id.recognition_result);

        Button EnterBtn = findViewById(R.id.enter_btn);
        EnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.recognition_result);
                String message = editText.getText().toString();
                Log.d("kyky", "message: "+message);
                URLSendGet urlSendGet = new URLSendGet(SERVER_URL,TIME_OUT);
                //urlSendGet.get("sendMessage/"+message);
                TextView textView = findViewById(R.id.textView8);
                textView.setText(urlSendGet.get("getUser/0"));
                textView = findViewById(R.id.textView9);
                textView.setText(message);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RecognizerActivity.RESULT_OK && data != null) {
                final String result = data.getStringExtra(RecognizerActivity.EXTRA_RESULT);
                updateTextResult(result);
            } else if (resultCode == RecognizerActivity.RESULT_ERROR) {
                String error = data.getSerializableExtra(RecognizerActivity.EXTRA_ERROR).toString();
                updateTextResult(error);
            }
        }
    }

    private void updateTextResult(String error) {
        resultView.setText(error);
    }

}
