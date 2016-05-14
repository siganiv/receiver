package com.example.siganiv.receiver;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

//TODO przelozenie na bok przerywa transfer
//TODO scroll?
public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_PORT = "8888";
    private static final String START_RECEIVING = "Start";
    private static final String STOP_RECEIVING = "Stop";
    public static final String MESSAGE_TO_ARCHIVE = "message";

    private EditText port;
    private Button start;
    private ToggleButton average;
    private Button archive;
    private DataValues result;
    private Receive receive;
    private GraphView graph;

    private ToggleButton toggle1;
    private ToggleButton toggle2;
    private ToggleButton toggle3;
    private ToggleButton toggle4;

    private boolean toggle1Flag = false;
    private boolean toggle2Flag = false;
    private boolean toggle3Flag = false;
    private boolean toggle4Flag = false;
    private boolean toggleAverageFlag = false;

    private LineGraphSeries<DataPoint> series1;
    private LineGraphSeries<DataPoint> series2;
    private LineGraphSeries<DataPoint> series3;
    private LineGraphSeries<DataPoint> series4;
    private LineGraphSeries<DataPoint> seriesAverage;

    Receive.ReceiveInterface receiveInterface = new Receive.ReceiveInterface() {
        @Override
        public void onUpdate(String value) {
            result.add(value);
            series1 = new LineGraphSeries<DataPoint>(result.getSeries1());
            series2 = new LineGraphSeries<DataPoint>(result.getSeries2());
            series3 = new LineGraphSeries<DataPoint>(result.getSeries3());
            series4 = new LineGraphSeries<DataPoint>(result.getSeries4());

            seriesAverage = new LineGraphSeries<DataPoint>(result.getAverage());

            series1.setTitle("Sensor 1");
            series2.setTitle("Sensor 2");
            series3.setTitle("Sensor 3");
            series4.setTitle("Sensor 4");
            seriesAverage.setTitle("Average");

            series2.setColor(Color.YELLOW);
            series3.setColor(Color.GREEN);
            series4.setColor(Color.BLACK);
            seriesAverage.setColor(Color.RED);

            graph.removeAllSeries();

            if(toggle1Flag){
                graph.addSeries(series1);
            }

            if(toggle2Flag){
                graph.addSeries(series2);
            }

            if(toggle3Flag){
                graph.addSeries(series3);
            }

            if(toggle4Flag){
                graph.addSeries(series4);
            }

            if(toggleAverageFlag){
                graph.addSeries(seriesAverage);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        result = new DataValues();
        setContentView(R.layout.activity_main);
        port = (EditText) findViewById(R.id.portButton);
        start = (Button) findViewById(R.id.startButton);
        average = (ToggleButton) findViewById(R.id.averageButton);
        archive = (Button) findViewById(R.id.archiveButton);
        graph = (GraphView) findViewById(R.id.graph);

        toggle1 = (ToggleButton) findViewById(R.id.toggleButton);
        toggle2 = (ToggleButton) findViewById(R.id.toggleButton2);
        toggle3 = (ToggleButton) findViewById(R.id.toggleButton3);
        toggle4 = (ToggleButton) findViewById(R.id.toggleButton4);

        series1 = new LineGraphSeries<DataPoint>(result.getSeries1());
        series2 = new LineGraphSeries<DataPoint>(result.getSeries2());
        series3 = new LineGraphSeries<DataPoint>(result.getSeries3());
        series4 = new LineGraphSeries<DataPoint>(result.getSeries4());
        seriesAverage = new LineGraphSeries<DataPoint>(result.getAverage());

        port.setText(String.valueOf(DEFAULT_PORT));
    }


    public void onClickArchive(View view) {
        Intent getNameScreenIntent = new Intent(this, ArchiveActivity.class);

        getNameScreenIntent.putExtra(MESSAGE_TO_ARCHIVE, result.getHistory());
        startActivity(getNameScreenIntent);
    }

    public void onClickStart(View view) {
        if (receive == null || receive.getStatus() != AsyncTask.Status.RUNNING) {
            receive = new Receive(receiveInterface);
            receive.execute(Integer.parseInt(port.getText().toString()));

            start.setText(STOP_RECEIVING);

        } else {
            receive.stop();

            start.setText(START_RECEIVING);
        }
    }

    public void onClickAverage(View view) {
        if (!toggleAverageFlag) {

            graph.addSeries(seriesAverage);
            toggleAverageFlag = true;

        } else {

            graph.removeSeries(seriesAverage);
            toggleAverageFlag = false;
        }
    }

    public void onClickToggle1(View view) {
        if (!toggle1Flag) {

            graph.addSeries(series1);
            toggle1Flag = true;

        } else {

            graph.removeSeries(series1);
            toggle1Flag = false;
        }
    }

    public void onClickToggle2(View view) {
        if (!toggle2Flag) {

            graph.addSeries(series2);
            toggle2Flag = true;

        } else {

            graph.removeSeries(series2);
            toggle2Flag = false;
        }
    }

    public void onClickToggle3(View view) {
        if (!toggle3Flag) {

            graph.addSeries(series3);
            toggle3Flag = true;

        } else {

            graph.removeSeries(series3);
            toggle3Flag = false;
        }
    }

    public void onClickToggle4(View view) {
        if (!toggle4Flag) {

            graph.addSeries(series4);
            toggle4Flag = true;

        } else {

            graph.removeSeries(series4);
            toggle1Flag = false;
        }
    }

}
