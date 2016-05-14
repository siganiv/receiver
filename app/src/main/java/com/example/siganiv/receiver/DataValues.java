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
    private final static int MAX_AVERAGE = 5;
    private final static int MAX_SERIES = 20;

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
            tempString = (dataPoint.toString() + NEXT_LINE + tempString);
        }

        return tempString;
    }

    public DataPoint[] getSeries1() {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        int count = 0;

        for (int i = values.size()-1; i >=0; i--) {
            if (values.get(i).getState() == DataPointState.VALID&&count<MAX_SERIES) {
                tempDataPoints.add(new DataPoint(values.get(i).getTime(), values.get(i).getValue1()));
                count++;
            }
        }

        List<DataPoint> tempDataPoints2 = new ArrayList<>();

        for (int i = tempDataPoints.size()-1; i >= 0; i--) {
            tempDataPoints2.add(tempDataPoints.get(i));
        }

        DataPoint[] array = tempDataPoints2.toArray(new DataPoint[tempDataPoints2.size()]);
        return array;
    }

    public DataPoint[] getSeries2() {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        int count = 0;

        for (int i = values.size()-1; i >=0; i--) {
            if (values.get(i).getState() == DataPointState.VALID&&count<MAX_SERIES) {
                tempDataPoints.add(new DataPoint(values.get(i).getTime(), values.get(i).getValue2()));
                count++;
            }
        }

        List<DataPoint> tempDataPoints2 = new ArrayList<>();

        for (int i = tempDataPoints.size()-1; i >= 0; i--) {
            tempDataPoints2.add(tempDataPoints.get(i));
        }

        DataPoint[] array = tempDataPoints2.toArray(new DataPoint[tempDataPoints2.size()]);
        return array;
    }

    public DataPoint[] getSeries3() {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        int count = 0;

        for (int i = values.size()-1; i >=0; i--) {
            if (values.get(i).getState() == DataPointState.VALID&&count<MAX_SERIES) {
                tempDataPoints.add(new DataPoint(values.get(i).getTime(), values.get(i).getValue3()));
                count++;
            }
        }

        List<DataPoint> tempDataPoints2 = new ArrayList<>();

        for (int i = tempDataPoints.size()-1; i >= 0; i--) {
            tempDataPoints2.add(tempDataPoints.get(i));
        }

        DataPoint[] array = tempDataPoints2.toArray(new DataPoint[tempDataPoints2.size()]);
        return array;
    }

    public DataPoint[] getSeries4() {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        int count = 0;

        for (int i = values.size()-1; i >=0; i--) {
            if (values.get(i).getState() == DataPointState.VALID&&count<MAX_SERIES) {
                tempDataPoints.add(new DataPoint(values.get(i).getTime(), values.get(i).getValue4()));
                count++;
            }
        }

        List<DataPoint> tempDataPoints2 = new ArrayList<>();

        for (int i = tempDataPoints.size()-1; i >= 0; i--) {
            tempDataPoints2.add(tempDataPoints.get(i));
        }

        DataPoint[] array = tempDataPoints2.toArray(new DataPoint[tempDataPoints2.size()]);
        return array;
    }

    public DataPoint[] getAverage() {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        int count = 0;

        for (int i = values.size() - 1; i >= 0; i--) {
            if ((values.get(i).getState() == DataPointState.VALID) && count < MAX_AVERAGE) {
                tempDataPoints.add(new DataPoint(values.get(i).getTime(), countAverage(values.get(i).getValue1(), values.get(i).getValue2(), values.get(i).getValue3(), values.get(i).getValue4())));
                count++;
            }
        }

        List<DataPoint> tempDataPoints2 = new ArrayList<>();

        for (int i = tempDataPoints.size()-1; i >= 0; i--) {
            tempDataPoints2.add(tempDataPoints.get(i));
        }

        DataPoint[] array = tempDataPoints2.toArray(new DataPoint[tempDataPoints2.size()]);
        return array;
    }

    private double countAverage(double first, double second, double third, double fourth) {
        double result = 0;

        int denominator = 4;

        if (first == 0) {
            denominator--;
        }

        if (second == 0) {
            denominator--;
        }

        if (third == 0) {
            denominator--;
        }

        if (fourth == 0) {
            denominator--;
        }

        if (denominator == 0) {
            denominator = 1;
        }

        double numerator = first + second + third + fourth;

        result = numerator / denominator;

        return result;
    }
}
