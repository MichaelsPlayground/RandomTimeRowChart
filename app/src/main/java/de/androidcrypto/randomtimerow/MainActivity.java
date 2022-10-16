package de.androidcrypto.randomtimerow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView dataRandom;
    Button start;
    int value = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataRandom = findViewById(R.id.tvMainData);
        start = findViewById(R.id.btnMainStart);

        // this method uses a ScheduledExecutorService
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rand = getRandomNumberInRange(-5, 5);
                value = value + rand;
                dataRandom.setText(String.valueOf(value));
                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.SECONDS);
            }
        });
    }

    Runnable helloRunnable = new Runnable() {
        public void run() {
            System.out.println("ScheduledTask");
            int rand = getRandomNumberInRange(-5, 5);
            value = value + rand;
            dataRandom.setText(String.valueOf(value));
        }
    };

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}