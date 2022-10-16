package de.androidcrypto.randomtimerow;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    TextView dataRandom;
    Button start;
    int value = 60;
    int counter = 0;

    private final int defaultXMin = 0;
    private final int defaultXMax = 60;
    private final int defaultYMin = 50;
    private final int defaultYMax = 90;
    private final int maximalDataPoints = 100;
    GraphView graph;
    LineGraphSeries<DataPoint> series;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataRandom = findViewById(R.id.tvMainData);
        start = findViewById(R.id.btnMainStart);

        graph = (GraphView) findViewById(R.id.graph);

        series = new LineGraphSeries<DataPoint>();

        /*
        series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 0)
        });*/
        graph.addSeries(series);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(defaultXMin);
        graph.getViewport().setMaxX(defaultXMax);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(defaultYMin);
        graph.getViewport().setMaxY(defaultYMax);

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

            graph.removeAllSeries();
            series.appendData(new DataPoint(counter, value), true, maximalDataPoints, false);

            System.out.println("counter: " + counter + " value: " + value);
            /*
            graph.getViewport().setXAxisBoundsManual(true);*/

            graph.getViewport().setMinX(defaultXMin);
            if (counter < defaultXMax) {
                //graph.getViewport().setMinX((counter - defaultXMax));
                graph.getViewport().setMaxX(defaultXMax);
            } else {
                graph.getViewport().setMaxX(counter);
            }
            if (series.getLowestValueY() < defaultYMin) {
                graph.getViewport().setMinY((series.getLowestValueY() - 5));
            } else {
                graph.getViewport().setMinY(defaultYMin);
            }
            if (series.getHighestValueY() < defaultYMax) {
                graph.getViewport().setMaxY(defaultYMax);
            } else {
                graph.getViewport().setMaxY((series.getHighestValueY() + 5));
            }
            graph.addSeries(series);
            //graph.addSeries(series);
            counter++;
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