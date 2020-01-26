package com.univerhelper.smartex.univerhelper;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.univerhelper.smartex.univerhelper.Classes.URLSendGet;
import com.univerhelper.smartex.univerhelper.Classes.User;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import ru.yandex.speechkit.Language;
import ru.yandex.speechkit.OnlineModel;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.gui.RecognizerActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.univerhelper.smartex.univerhelper.Classes.URLSendGet;
import com.univerhelper.smartex.univerhelper.Classes.User;

import java.util.UUID;

import ru.yandex.speechkit.Language;
import ru.yandex.speechkit.OnlineModel;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.gui.RecognizerActivity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Language;
import ru.yandex.speechkit.OnlineVocalizer;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Vocalizer;
import ru.yandex.speechkit.VocalizerListener;
import ru.yandex.speechkit.Voice;

public class MainActivity extends AppCompatActivity implements VocalizerListener{

    private static final String API_KEY_FOR_TESTS_ONLY = "88bb7adf-932f-4722-8c43-44eae7e6eb81";

    private static final int REQUEST_CODE = 31;

    private EditText resultView;

    private Vocalizer vocalizer;

    private String name = "";
    private int k1 = 0, k2 = 0;
    ArrayList<String> days = new ArrayList<>();
    ArrayList<String> sub  = new ArrayList<>();

    private int TIME_OUT=8000;
    private String SERVER_URL="http://10.201.2.208:8080/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();
        name = intent.getStringExtra("name");
        days.add("понедельник");
        days.add("вторник");
        days.add("среду");
        days.add("четверг");
        days.add("пятницу");
        days.add("субботу");
        days.add("воскресение");

        sub.add("свободная");
        sub.add("литература");
        sub.add("философия");
        sub.add("право");
        sub.add("математический анализ");

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

        vocalizer = new OnlineVocalizer.Builder(Language.RUSSIAN, MainActivity.this)
                .setVoice(Voice.ALYSS)
                .setAutoPlay(true)
                .build();

        final TextView textView = findViewById(R.id.textView8);
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vocalizer.cancel();
                Log.i("kyky","OnTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing

                Log.i("kyky","afterTextChanged");
                String text = textView.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(MainActivity.this, "Write smth to be vocalized!", Toast.LENGTH_SHORT).show();
                } else {
                    vocalizer.synthesize(text, Vocalizer.TextSynthesizingMode.INTERRUPT);
                }
            }
        });



        Button EnterBtn = findViewById(R.id.enter_btn);
        EnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.recognition_result);
                String message = editText.getText().toString();
                Log.d("kyky", "message: "+message);
                URLSendGet urlSendGet = new URLSendGet(SERVER_URL,TIME_OUT);
                TextView textView = findViewById(R.id.textView8);
                String[] s = message.split(" ");
                String idUser = urlSendGet.get("getUserId/" + name);
                String sZ = "";
                Button button = findViewById(R.id.map_button);
                Button button2 = findViewById(R.id.button2);
                button.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                switch (s[0]) {
                    case "сложить":
                        textView.setText(urlSendGet.get("id/"+s[1]+"+"+s[3]));

                        break;
                    case "запиши":
                        String Uid = urlSendGet.get("getUserId/" + name);
                        if (Integer.parseInt(urlSendGet.get("addGroupUser/"+s[3]+"/"+Uid)) > 0) {
                            textView.setText("Вы записаны");
                        } else {
                            textView.setText("Этой группы нет");
                        }
                        break;
                    case "напомнить":
                        String id = urlSendGet.get("getUserId/" + name);
                        Gson gson = new Gson();
                        User user = gson.fromJson(urlSendGet.get("getUser/" + id), User.class);
                        textView.setText(user.getPassword());
                        break;
                    case "скажи":
                        String day = s[3].substring(0, s[3].length()-1);
                        String id1 = urlSendGet.get("getUserId/" + name);
                        Gson gson1 = new Gson();
                        User user1 = gson1.fromJson(urlSendGet.get("getUser/" + id1), User.class);
                        String s1 = Integer.toString(user1.getGroup());
                        String s2 = Integer.toString(days.indexOf(day) + 1);
                        s1 = urlSendGet.get("getGroupDay/"+s1+"/"+s2);
                        String[] r = s1.split("-");
                        s1 = "";
                        for (int i = 0; i < r.length; i++) {
                            s1 += "пара " + Integer.toString(i + 1) + " " + sub.get(Integer.parseInt(r[i])) + ", ";
                        }
                        textView.setText(s1);
                        break;
                    case "добавь":
                        if (s[1]!="заметка")
                            sZ=s[1]+sZ;
                        boolean flag=false;
                        for (int i = 2; i < s.length; i++) {
                            sZ += s[i] + " ";
                            flag=true;

                        }
                        if (flag)
                        sZ= sZ.substring(0,sZ.length()-1);
                        Log.i("kyky","sz= "+sZ);

                        try {
                            urlSendGet.get("addZ/"+idUser+"/"+ URLEncoder.encode(sZ, "UTF-8").replaceAll("\\+", "%20")
                                    .replaceAll("\\%21", "!")
                                    .replaceAll("\\%27", "'")
                                    .replaceAll("\\%28", "(")
                                    .replaceAll("\\%29", ")")
                                    .replaceAll("\\%7E", "~"));
                            Log.i("kyky","addZ sz= "+URLEncoder.encode(sZ, "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        textView.setText("Заметка добавлена в ваш список");
                        break;
                    case "покажи":
                        try {
                            try {
                                int n=Integer.parseInt(s[1]);

                            }catch (Exception e){
                                switch (s[1]) {
                                    case "первое":
                                        s[1]="1";
                                        break;
                                    case "второе":
                                        s[1]="2";
                                        break;
                                    case "третье":
                                        s[1]="3";
                                        break;
                                    case "четвертое":
                                        s[1]="4";
                                        break;
                                    case "пятое":
                                        s[1]="5";
                                        break;
                                    case "шестое":
                                        s[1]="6";
                                        break;
                                    case "седьмое":
                                        s[1]="7";
                                        break;
                                        //todo ошибка
                                    case "девятое":
                                        s[1]="8";
                                        break;
                                    case "десятое":
                                        s[1]="9";
                                        break;
                                }
                            }
                            //urlSendGet.get("getZ/"+idUser+"/"+s[1])
                            Log.i("kyky","getZ MAS= "+ Arrays.toString(s));
                            Log.i("kyky","getZ s= "+urlSendGet.get("getZ/"+idUser+"/"+s[1]));
                            textView.setText(URLDecoder.decode(urlSendGet.get("getZ/"+idUser+"/"+s[1]),"UTF-8"));
                            Log.i("kyky","getZ str= "+s[1]);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "удали":
                        Log.i("kyky","getZ str= "+sZ);
                        if (Integer.parseInt(urlSendGet.get("remZ/"+idUser+"/"+s[1])) > 0) {
                            textView.setText("Такой заметки у вас нет");
                        } else {
                            textView.setText("Заметка была удалена");
                        }
                        break;
                    case "помоги":
                        textView.setText("нет");
                        break;
                    case "как" :
                        textView.setText("Воспользуйтесь этой схемой чтоб разобраться");
                        //k1 = Integer.parseInt(s[3]);
                        //k2 = Integer.parseInt(s[5]);
                        button.setVisibility(View.VISIBLE);
                        break;
                    case "открой":
                        textView.setText("Открываю");
                        button2.setVisibility(View.VISIBLE);
                        break;
                    default:
                        textView.setText("Повторите пожалуйста");
                        //textView.setText(s[0]);
                        break;
                }
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
    private void updateStateText(@NonNull final String text) {
//        currentStateTv.setText(text);
    }

    @Override
    public void onSynthesisDone(@NonNull Vocalizer vocalizer) {
        updateStateText("Synthesis done");
    }

    @Override
    public void onPartialSynthesis(@NonNull Vocalizer vocalizer, @NonNull Synthesis synthesis) {
        updateStateText("on partial synthesis");
    }

    @Override
    public void onPlayingBegin(@NonNull Vocalizer vocalizer) {
        updateStateText("Playing begin");
    }

    @Override
    public void onPlayingDone(@NonNull Vocalizer vocalizer) {
        updateStateText("Playing done");
    }

    @Override
    public void onVocalizerError(@NonNull Vocalizer vocalizer, @NonNull Error error) {
        updateStateText("Error occurred " + error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        vocalizer.cancel();
        vocalizer.destroy();
        vocalizer = null;
    }

    public void settings(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    public void map (View view) {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra("k1", k1);
        intent.putExtra("k2", k2);
        startActivity(intent);
    }
}
