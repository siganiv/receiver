package com.example.siganiv.receiver;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siganiv on 2016-03-11.
 */
public class DataValues {

    private final static String SPLITTER = "#";
    private final static int INVALID_VALUE = -1;
    private final static int INITIAL_VALUE = 0;
    private final static int MAX_VALUES = 5;
    private final static String INITIAL_TEXT = "";
    private final static String NEXT_LINE = "\n";

    public List<CustomDataPoint> values;

    public DataValues() {
        values = new ArrayList<>();
    }

    public void add(String value) {

        if (value.contains(SPLITTER)) {
            String tempValues[] = value.split(SPLITTER);
            int tempTime = INITIAL_VALUE;
            double tempCheckedValues[] = {INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE};

            for (int i = 0; i < tempValues.length; i++) {

                if (i == 0) {

                    tempTime = checkTime(tempValues[0]);

                } else if (i > 0 && i < MAX_VALUES) {

                    tempCheckedValues[i - 1] = checkValue(tempValues[i]);

                } else {
                    break;
                }
            }

            addPoint(tempTime, tempCheckedValues);

        } else {
            values.add(new CustomDataPoint());
        }

    }

    private void addPoint(int tempTime, double[] tempCheckedValues) {
        boolean validityFlag = true;

        if (tempTime != INVALID_VALUE) {
            for (int i = 0; i < tempCheckedValues.length; i++) {
                if (tempCheckedValues[i] == INVALID_VALUE) {
                    validityFlag = false;
                }
            }
        } else {
            validityFlag = false;
        }

        if (validityFlag) {
            values.add(new CustomDataPoint(tempTime, tempCheckedValues[0], tempCheckedValues[1], tempCheckedValues[2], tempCheckedValues[3]));
        } else {
            values.add(new CustomDataPoint());
        }
    }

    private int checkTime(String time) {
        try {
            return Integer.parseInt(time);
        } catch (NumberFormatException e) {
            return INVALID_VALUE;
        }
    }

    private double checkValue(String time) {
        try {
            return Double.parseDouble(time);
        } catch (NumberFormatException e) {
            return INVALID_VALUE;
        }
    }

    public String getHistory() {
        String tempString = INITIAL_TEXT;
        for (CustomDataPoint dataPoint : values) {
            tempString=(dataPoint.toString()+NEXT_LINE+tempString);
        }

        return tempString;
    }

    public DataPoint[] getSeries1() {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).getState() == DataPointState.VALID) {
                tempDataPoints.add(new DataPoint(values.get(i).getTime(), values.get(i).getValue1()));
            }
        }

        DataPoint[] array = tempDataPoints.toArray(new DataPoint[tempDataPoints.size()]);
        return array;
    }

    public DataPoint[] getSeries2() {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).getState() == DataPointState.VALID) {
                tempDataPoints.add(new DataPoint(values.get(i).getTime(), values.get(i).getValue2()));
            }
        }

        DataPoint[] array = tempDataPoints.toArray(new DataPoint[tempDataPoints.size()]);
        return array;
    }

    public DataPoint[] getSeries3() {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).getState() == DataPointState.VALID) {
                tempDataPoints.add(new DataPoint(values.get(i).getTime(), values.get(i).getValue3()));
            }
        }

        DataPoint[] array = tempDataPoints.toArray(new DataPoint[tempDataPoints.size()]);
        return array;
    }

    public DataPoint[] getSeries4() {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).getState() == DataPointState.VALID) {
                tempDataPoints.add(new DataPoint(values.get(i).getTime(), values.get(i).getValue4()));
            }
        }

        DataPoint[] array = tempDataPoints.toArray(new DataPoint[tempDataPoints.size()]);
        return array;
    }
}
