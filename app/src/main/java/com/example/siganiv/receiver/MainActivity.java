package com.example.siganiv.receiver;


import android.content.Intent;
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
    private Button reset;
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

    private LineGraphSeries<DataPoint> series1;
    private LineGraphSeries<DataPoint> series2;
    private LineGraphSeries<DataPoint> series3;
    private LineGraphSeries<DataPoint> series4;

    Receive.ReceiveInterface receiveInterface = new Receive.ReceiveInterface() {
        @Override
        public void onUpdate(String value) {
            result.add(value);
            series1 = new LineGraphSeries<DataPoint>(result.getSeries1());
            series2 = new LineGraphSeries<DataPoint>(result.getSeries2());
            series3 = new LineGraphSeries<DataPoint>(result.getSeries3());
            series4 = new LineGraphSeries<DataPoint>(result.getSeries4());

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
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        result = new DataValues();
        setContentView(R.layout.activity_main);
        port = (EditText) findViewById(R.id.portButton);
        start = (Button) findViewById(R.id.startButton);
        reset = (Button) findViewById(R.id.resetButton);
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

        port.setText(String.valueOf(DEFAULT_PORT));

        //TODO colors
//        series2.setTitle("Random Curve 1");
//        series2.setColor(Color.RED);
//        series3.setColor(Color.GREEN);
//        series4.setColor(Color.BLACK);

        //TODO scalable/scrollable
//        graph.getViewport().setScalable(true);
//        graph.getViewport().setScrollable(true);
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

    public void onClickReset(View view) {
        graph.removeAllSeries();
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
