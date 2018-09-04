package com.example.sss.translatorapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.util.Scanner;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClick(View view) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String host = "translate.yandex.net";
                    String file = "api/v1.5/tr.json/translate?";
                    String api_key = "Здесь пиши свой api key";
                    String text = ((TextView) findViewById(R.id.sourceText)).getText().toString();
                    String lang = "ru";
                    String format = "plain";
                    file += "key=" + api_key + "&" + "text" + text + "&lang=" + lang + "&format=" + format;
                    HttpURLConnection connection = null;
                    try {
                        URL url = new URL("https", host, 80, file);
//                        URL url = new URL("http://www.android.com/");
                        connection = ((HttpURLConnection) url.openConnection());
                        InputStream in = new BufferedInputStream(connection.getInputStream());
                        ((EditText) findViewById(R.id.destinationText)).setText(readStream(in));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null)
                            connection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private String readStream(InputStream in) {
        StringWriter writer = new StringWriter();
        String text = null;
        try (Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
            text = scanner.useDelimiter("\\A").next();
        }
        return text;
    }
}
