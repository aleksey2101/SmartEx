package com.univerhelper.smartex.univerhelper.Classes;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  Класс для упрощения отправки GET запроса
 */

public class URLSendGet {
    private int TIME_OUT;
    private String SERVER_URL;

    /**
     * Конструктор класса
     * @param SERVER_URL адрес + порт
     * @param TIME_OUT  ожидание подключения и ответа
     */
    public URLSendGet(@NotNull String SERVER_URL,
                      @NotNull int TIME_OUT) {
        this.TIME_OUT   = TIME_OUT;
        this.SERVER_URL = SERVER_URL;
    }

    /**
     * Метод отправляющий запрос на сервер.
     *
     * @param SERVER_GET запрос для сервера
     * @return строка ответ сервера
     */
    public String get(final String SERVER_GET) {
        final String[] input = {null};
        Log.i("kyky","get is working");
        Thread send = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    String q = SERVER_URL + SERVER_GET;
                    URL url = new URL(q);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(TIME_OUT);
                    conn.setReadTimeout(TIME_OUT);
                    conn.connect();

                    if(HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        input[0] = bufferedReader.readLine();


                    }
                } catch (Exception e) {
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        });
        send.start();
        while (send.isAlive()) { }
        Log.i("kyky","ans "+input[0]);
        return input[0];
    }
}
